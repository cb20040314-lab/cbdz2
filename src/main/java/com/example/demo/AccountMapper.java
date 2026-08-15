package com.example.demo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapper {

    @Insert("INSERT INTO accounts(username, password) VALUES(#{username}, #{password})")
    int insert(Account account);

    @Select("SELECT id, username, password FROM accounts WHERE username = #{username}")
    Account findByUsername(String username);
}