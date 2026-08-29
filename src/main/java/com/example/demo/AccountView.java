package com.example.demo;

/** 管理员查看的安全账号信息，不包含密码。 */
public class AccountView {
    private Integer id;
    private String username;
    private String role;

    public AccountView() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
