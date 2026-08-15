package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Result<List<User>> findAll() {
//        return userService.findAll();
        List<User> users = userService.findAll();
        return Result.success(users);
    }

    @PostMapping
    public Result<User> add(@Valid @RequestBody User user) {
        User savedUser = userService.add(user);
        return Result.success(savedUser);
    }

    @PutMapping("/{id}")
    public Result<String> update(@PathVariable Integer id,
                         @RequestBody User user) {
        Boolean result =userService.update(id,user);
        if(result)return Result.success("修改成功");
        else return Result.error("修改失败");
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        Boolean result =userService.delete(id);
            if(result)return Result.success("删除成功");
                else return Result.error("删除失败");
    }

    @GetMapping("/{id}")
    public Result<User> findById(@PathVariable Integer id) {
        User user = userService.findById(id);
        if (user == null)
            return Result.error("没有该用户");
        else return Result.success(user);

    }

    @GetMapping("/page")
    public Result<List<User>> findPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {

        List<User> users = userService.findPage(page, size);
        return Result.success(users);
    }
}