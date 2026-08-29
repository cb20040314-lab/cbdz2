package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

    private final AccountService accountService;

    public AdminController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/admin/accounts")
    public Result<List<AccountView>> findAllAccounts(
            @RequestHeader(value = "Authorization", required = false) String authorization) {

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return Result.error("请先登录管理员账号");
        }

        try {
            String token = authorization.substring("Bearer ".length()).trim();
            String username = JwtUtil.getUsername(token);

            if (!accountService.isAdmin(username)) {
                return Result.error("没有管理员权限");
            }

            return Result.success(accountService.findAllAccounts());
        } catch (Exception exception) {
            return Result.error("登录状态无效或已过期");
        }
    }
}
