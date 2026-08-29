package com.example.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

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
    public Result<Map<String, Object>> login(@RequestBody Account account) {
        Account savedAccount = accountService.login(account);

        if (savedAccount != null) {
            String token = JwtUtil.createToken(account.getUsername());

            Map<String, Object> loginData = new LinkedHashMap<>();
            loginData.put("message", "登录成功");
            loginData.put("token", token);
            loginData.put("accountId", savedAccount.getId());
            loginData.put("role", savedAccount.getRole());
            return Result.success(loginData);
        }

        return Result.error("用户名或密码错误");
    }
}
