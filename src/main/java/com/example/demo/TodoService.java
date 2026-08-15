package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.List;

/** 待办业务层，负责连接 Controller 和 TodoMapper。 */
@Service
public class TodoService {

    /** MyBatis 自动生成的待办数据库操作对象。 */
    private final TodoMapper todoMapper;

    /** 由 Spring 自动注入 TodoMapper。 */
    public TodoService(TodoMapper todoMapper) {
        this.todoMapper = todoMapper;
    }

    /**
     * 将待办保存到数据库，并返回保存后的对象。
     *
     * @param todo 要保存的待办
     * @return 保存后的待办，id 会由 MyBatis 回填
     */
    public Todo add(Todo todo) {
        todoMapper.insert(todo);
        return todo;
    }

    /**
     * 查询指定账号的待办列表。
     *
     * @param accountId 账号编号
     * @return 该账号的待办列表
     */
    public List<Todo> findByAccountId(Integer accountId) {
        return todoMapper.findByAccountId(accountId);
    }

    // 修改待办完成状态
    public boolean updateStatus(
            Integer id, Integer completed, Integer accountId) {

        return todoMapper.updateStatus(id, completed, accountId) > 0;
    }

    // 删除待办
    public boolean delete(Integer id) {
        return todoMapper.deleteById(id) > 0;
    }

    public List<Todo> findPage(Integer accountId, int page, int size) {
        int offset = (page - 1) * size;
        return todoMapper.findPage(accountId, offset, size);
    }

    public List<Todo> findByStatus(Integer accountId, Integer completed) {
        return todoMapper.findByStatus(accountId, completed);
    }

    public boolean updateTitle(Integer id, String title) {
        return todoMapper.updateTitle(id, title) > 0;
    }
}
