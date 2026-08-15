package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 待办事项接口层，接收浏览器或 {@code testTodo.http} 发来的 HTTP 请求。
 */
@RestController
public class TodoController {

    /** 待办业务层，负责调用数据库操作。 */
    private final TodoService todoService;

    /** 由 Spring 自动注入 TodoService。 */
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    /**
     * 新增一条待办。
     *
     * @param todo 前端 JSON 转换得到的待办对象
     * @return 保存后的待办（包含数据库生成的 id）
     */
    @PostMapping("/todos")
    public Result<Todo> add(@RequestBody Todo todo) {
        Todo savedTodo = todoService.add(todo);
        return Result.success(savedTodo);
    }

    /**
     * 根据账号编号查询该账号的全部待办。
     *
     * @param accountId 网址参数，例如 {@code /todos?accountId=1} 中的 1
     * @return 该账号的待办列表
     */
    @GetMapping("/todos")
    public Result<List<Todo>> findByAccountId(
            @RequestParam Integer accountId) {//网址拿参数

        List<Todo> todos = todoService.findByAccountId(accountId);
        return Result.success(todos);
    }

    @PutMapping("/todos/{id}/status")
    public Result<String> updateStatus(
            @PathVariable Integer id,
            @RequestParam Integer completed,
            @RequestParam Integer accountId) {

        boolean success =
                todoService.updateStatus(id, completed, accountId);

        if (success) {
            return Result.success("状态修改成功");
        }
        return Result.error("待办不存在，或不属于这个账号");
    }
//删除用户
    @DeleteMapping("/todos/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        boolean success = todoService.delete(id);

        if (success) {
            return Result.success("删除成功");
        }
        return Result.error("待办不存在");
    }

    @GetMapping("/todos/page")
    public Result<List<Todo>> findPage(
            @RequestParam Integer accountId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {

        List<Todo> todos = todoService.findPage(accountId, page, size);
        return Result.success(todos);
    }

    @GetMapping("/todos/status")
    public Result<List<Todo>> findByStatus(
            @RequestParam Integer accountId,
            @RequestParam Integer completed) {//网址 ? 后面取参数

        List<Todo> todos =
                todoService.findByStatus(accountId, completed);

        return Result.success(todos);
    }

    @PutMapping("/todos/{id}")
    public Result<String> updateTitle(
            @PathVariable Integer id,
            @RequestParam String title) {

        boolean success = todoService.updateTitle(id, title);

        if (success) {
            return Result.success("待办内容修改成功");
        }
        return Result.error("待办不存在");
    }
}
