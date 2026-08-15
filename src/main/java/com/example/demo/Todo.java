package com.example.demo;

import java.time.LocalDateTime;

/** 待办事项的数据对象，对应数据库中的 {@code todos} 表。 */
public class Todo {
    /** 待办编号，由数据库自动生成。 */
    private Integer id;
    /** 待办内容，例如“完成作业”。 */
    private String title;
    /** 完成状态：通常 0 表示未完成，1 表示已完成。 */
    private Integer completed;
    /** 创建这条待办的账号编号。 */
    private Integer accountId;
    /** 待办创建时间。 */
    private LocalDateTime createdTime;

    /** 供 Spring 将 JSON 请求体转换为 Todo 对象时使用。 */
    public Todo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
