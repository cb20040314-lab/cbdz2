package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountMapper accountMapper;

    public AccountService(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    // 注册
    public boolean register(Account account) {
        // 先按用户名查：查到了说明名字已经被别人注册
        if (accountMapper.findByUsername(account.getUsername()) != null) {
            return false;
        }

        // 没查到，才插入数据库
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
        return savedAccount.getPassword().equals(account.getPassword());
    }
}