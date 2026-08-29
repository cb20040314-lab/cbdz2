package com.example.demo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AccountMapper {

    @Insert("INSERT INTO accounts(username, password, role) VALUES(#{username}, #{password}, 'USER')")
    int insert(Account account);

    @Select("SELECT id, username, password, role FROM accounts WHERE username = #{username}")
    Account findByUsername(String username);

    @Select("SELECT id, username, role FROM accounts ORDER BY id ASC")
    List<AccountView> findAllAccounts();
}
