package com.example.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody Account account) {
        boolean success = accountService.register(account);

        if (success) {
            return Result.success("注册成功");
        }
        return Result.error("用户名已存在");
    }

//    @PostMapping("/login")
//    public Result<String> login(@RequestBody Account account) {
//        boolean success = accountService.login(account);
//
//        if (success) {
//            return Result.success("登录成功");
//        }
//        return Result.error("用户名或密码错误");
//    }

    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody Account account) {
        boolean success = accountService.login(account);

        if (success) {
            String token = JwtUtil.createToken(account.getUsername());

            return Result.success(Map.of(
                    "message", "登录成功",
                    "token", token
            ));
        }

        return Result.error("用户名或密码错误");
    }

    @GetMapping("/me")
    public Result<String> me(Authentication authentication) {
        return Result.success(authentication.getName());
    }
}
