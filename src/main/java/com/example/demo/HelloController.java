//package com.example.demo;
//
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 示例 REST 控制器，提供问候语和用户相关的 HTTP 接口。
// *
// * <p>{@code /users} 接口操作内存中的用户列表，
// * {@code /db/users} 接口通过 MyBatis 操作数据库。</p>
// */
//@RestController
//public class HelloController {
//
//    /** 用于演示的内存用户列表，应用重启后其中的数据会丢失。 */
//    private final List<User> users = new ArrayList<>();
//
//    /** 用户数据访问对象，用于读写数据库。 */
//    private final UserMapper userMapper;
//
//    /**
//     * 通过构造器注入用户数据访问对象。
//     *
//     * @param userMapper 用户数据访问对象
//     */
//    public HelloController(UserMapper userMapper) {
//        this.userMapper = userMapper;
//    }
//
//    /**
//     * 根据请求参数返回问候语，例如：{@code GET /hello?name=小明}。
//     *
//     * @param name 要问候的姓名
//     * @return 包含姓名的问候语
//     */
//    @GetMapping("/hello")
//    public String hello(@RequestParam String name) {
//        return "你好" + name;
//    }
//
//    /**
//     * 返回一个固定的示例用户。
//     *
//     * @return 示例用户
//     */
//    @GetMapping("/user")
//    public User getUser() {
//        return new User(1, "张三");
//    }
//
//    /**
//     * 根据路径中的编号返回一个示例用户，例如：{@code GET /user/1}。
//     *
//     * @param id 用户编号
//     * @return 使用指定编号创建的示例用户
//     */
//    @GetMapping("/user/{id}")
//    public User getUserById(@PathVariable Integer id) {
//        return new User(id, "张三");
//    }
//
//    /**
//     * 接收并原样返回请求体中的用户，仅用于演示请求体绑定。
//     *
//     * @param user 请求体中的用户
//     * @return 接收到的用户
//     */
//    @PostMapping("/user")
//    public User addUser(@RequestBody User user) {
//        System.out.println("收到用户：" + user.getName());
//        return user;
//    }
//
//    /**
//     * 将请求体中的用户添加到内存列表。
//     *
//     * @param user 要添加的用户
//     * @return 添加后的用户
//     */
//    @PostMapping("/users")
//    public User addUsers(@RequestBody User user) {
//        System.out.println("收到用户：" + user.getName());
//        users.add(user);
//        return user;
//    }
//
//    /**
//     * 查询内存列表中的全部用户。
//     *
//     * @return 内存中的用户列表
//     */
//    @GetMapping("/users")
//    public List<User> getUsers() {
//        return users;
//    }
//
//    /**
//     * 从数据库查询全部用户。
//     *
//     * @return 数据库中的用户列表
//     */
//    @GetMapping("/db/users")
//    public List<User> getDatabaseUsers() {
//        return userMapper.findAll();
//    }
//
//    /**
//     * 将请求体中的用户保存到数据库。
//     *
//     * @param user 要保存的用户
//     * @return 保存后的用户（包含数据库生成的编号）
//     */
//    @PostMapping("/db/users")
//    public User addDatabaseUser(@RequestBody User user) {
//        userMapper.insert(user);
//        return user;
//    }
//
//    @DeleteMapping("/db/users/{id}")
//    public String deleteUser(@PathVariable Integer id) {
//        int rows = userMapper.deleteById(id);
//
//        if (rows > 0) {
//            return "删除成功";
//        } else {
//            return "用户不存在";
//        }
//    }
//
//    @PutMapping("/db/users/{id}")
//    public String updateUser(
//            @PathVariable Integer id,
//            @RequestBody User user) {
//
//        user.setId(id);
//        int rows = userMapper.update(user);
//
//        if (rows > 0) {
//            return "修改成功";
//        } else {
//            return "用户不存在";
//        }
//    }
//}
