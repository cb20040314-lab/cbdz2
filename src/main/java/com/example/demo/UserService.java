package com.example.demo;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public User add(User user) {
        userMapper.insert(user);
        return user;
    }

    public boolean update(Integer id, User user) {
        user.setId(id);
        return userMapper.update(user) > 0;
    }

    public boolean delete(Integer id) {
        return userMapper.deleteById(id) > 0;
    }

    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    public List<User> findPage(int page, int size) {
        int offset = (page - 1) * size;
        return userMapper.findPage(offset, size);
    }

}