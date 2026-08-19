package com.example.demo;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final PasswordEncoder passwordEncoder;
    private  final AccountMapper accountMapper;

    public AccountService(
            AccountMapper accountMapper,
            PasswordEncoder passwordEncoder) {

        this.accountMapper = accountMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // 注册
    public boolean register(Account account) {
        if (accountMapper.findByUsername(account.getUsername()) != null) {
            return false;
        }

        // 把用户输入的明文密码转换成 BCrypt 密文
        String encodedPassword =
                passwordEncoder.encode(account.getPassword());

        account.setPassword(encodedPassword);

        return accountMapper.insert(account) > 0;
    }

    // 登录
    public boolean login(Account account) {
        Account savedAccount =
                accountMapper.findByUsername(account.getUsername());

        // 用户名不存在
        if (savedAccount == null) {
            return false;
        }

        // 用户名存在，再比较密码
        return passwordEncoder.matches(
                account.getPassword(),
                savedAccount.getPassword()
        );
    }
}