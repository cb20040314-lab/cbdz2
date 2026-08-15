package com.example.demo;

import org.apache.ibatis.annotations.Param;
import java.util.List;

import org.apache.ibatis.annotations.*;



/**
 * 用户数据访问接口，负责操作数据库中的 {@code users} 表。
 *
 * <p>{@link Mapper @Mapper} 会让 MyBatis 为该接口创建实现对象，
 * 供 Spring 注入并调用。</p>
 */
@Mapper
public interface UserMapper {

    /**
     * 查询用户表中的全部用户。
     *
     * @return 数据库中的用户列表
     */
    @Select("SELECT id, name FROM users")
    List<User> findAll();

    /**
     * 新增一名用户。
     *
     * <p>{@code #{name}} 会读取 {@code user} 的 {@code name} 属性；
     * 插入成功后，数据库生成的主键会回填到 {@code user.id}。</p>
     *
     * @param user 要保存的用户
     * @return 受影响的数据库行数
     */
    @Insert("INSERT INTO users(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);


    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteById(Integer id);

    @Update("UPDATE users SET name = #{name} WHERE id = #{id}")
    int update(User user);

    @Select("SELECT id, name FROM users WHERE id = #{id}")
    User findById(Integer id);

    @Select("SELECT id, name FROM users LIMIT #{offset}, #{size}")
    List<User> findPage(@Param("offset") int offset,
                        @Param("size") int size);


}
