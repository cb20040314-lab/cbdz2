package com.example.demo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT（JSON Web Token）工具类。
 *
 * <p>用于在用户登录成功后创建 token，以及在后续请求中验证 token
 * 并取出其中保存的用户名。</p>
 */
public class JwtUtil {

    // 后端自己的秘密钥匙：至少 32 个英文字符
    private static final String SECRET =
            "my-super-secret-key-for-demo-2026";

    // token 有效期：2 小时
    private static final long EXPIRE_TIME = 2 * 60 * 60 * 1000L;

    /** 由密钥文本转换而来的 HMAC 签名密钥，用于签发和验证 token。 */
    private static final SecretKey KEY =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    /**
     * 根据登录用户名创建带签名的 JWT。
     *
     * <p>生成的 token 中包含用户名、签发时间和过期时间；
     * 客户端可在之后的请求中携带它来证明已登录。</p>
     *
     * @param username 登录成功的用户名
     * @return 已签名、有效期为两小时的 JWT 字符串
     */
    public static String createToken(String username) {
        return Jwts.builder()
                // 设置 JWT 的 subject 字段，用来保存用户名。
                .subject(username)
                // 设置 token 的签发时间为当前时间。
                .issuedAt(new Date())
                // 设置两小时后的过期时间，超过该时间 token 将失效。
                .expiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                // 使用服务器密钥为 token 签名，防止内容被伪造或篡改。
                .signWith(KEY)
                // 将 JWT 压缩为可通过 HTTP 传输的三段式字符串。
                .compact();
    }

    /**
     * 验证 token 的签名和有效期，并取出其中保存的用户名。
     *
     * @param token 客户端请求中携带的 JWT 字符串
     * @return token 中 subject 字段保存的用户名
     * @throws io.jsonwebtoken.JwtException token 被篡改、格式错误或已经过期时抛出
     */
    public static String getUsername(String token) {
        return Jwts.parser()
                // 使用同一把密钥验证 token 的签名。
                .verifyWith(KEY)
                .build()
                // 解析已签名的 claims；签名错误或 token 过期时会在此处抛异常。
                .parseSignedClaims(token)
                .getPayload()
                // 取出 createToken() 写入 subject 字段的用户名。
                .getSubject();
    }
}

