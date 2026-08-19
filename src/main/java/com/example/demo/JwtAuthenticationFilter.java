package com.example.demo;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * JWT 身份认证过滤器。
 *
 * <p>每个 HTTP 请求进入 Controller 之前都会经过该过滤器。过滤器从
 * {@code Authorization} 请求头中读取 JWT；验证成功后，将当前用户名写入
 * Spring Security 的上下文，使后续安全规则能够把该请求识别为“已登录”。
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * 对每个请求执行一次 JWT 认证。
     *
     * @param request 当前 HTTP 请求
     * @param response 当前 HTTP 响应
     * @param filterChain Spring Security 过滤器链
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // 读取请求头，例如：Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
        String authorization =
                request.getHeader("Authorization");

        // 只有请求头存在并且使用 Bearer 认证格式时，才尝试解析 JWT。
        if (authorization != null
                && authorization.startsWith("Bearer ")) {

            // "Bearer " 一共占 7 个字符，截取后面的部分得到真正的 Token。
            String token = authorization.substring(7);

            try {
                // 校验 Token 的签名和有效期，并读取其中保存的用户名。
                String username = JwtUtil.getUsername(token);

                // 创建一个已认证对象。当前项目暂未设置用户角色，因此权限列表为空。
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                List.of()
                        );

                // 把认证信息保存到当前请求的安全上下文中。
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);

            } catch (JwtException | IllegalArgumentException e) {
                // Token 过期、签名错误或格式不正确时，清除认证信息。
                SecurityContextHolder.clearContext();
            }
        }

        // 无论有没有 Token，都继续执行后续过滤器；最终是否允许访问由安全配置决定。
        filterChain.doFilter(request, response);
    }
}
