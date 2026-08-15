package com.example.demo;

import org.apache.ibatis.annotations.*;

import java.util.List;

/** MyBatis 数据访问接口，负责执行 {@code todos} 表的 SQL。 */
@Mapper
public interface TodoMapper {

    /**
     * 向 todos 表插入一条待办；{@code #{title}} 和 {@code #{accountId}}
     * 分别读取 Todo 对象中的 title 与 accountId。
     *
     * @param todo 要新增的待办
     * @return 受影响的行数，成功时通常为 1
     */
    @Insert("""
            INSERT INTO todos(title, account_id)
            VALUES(#{title}, #{accountId})
            """)
    /** 将数据库生成的主键回填到 todo.id。 */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Todo todo);

    /**
     * 查询某个账号的待办，并按编号倒序返回。
     * SQL 中的 AS 会把数据库字段名转换为 Java 属性名。
     *
     * @param accountId 账号编号
     * @return 查询到的待办列表
     */
    @Select("""
            SELECT id, title, completed,
                   account_id AS accountId,
                   created_time AS createdTime
            FROM todos
            WHERE account_id = #{accountId}
            ORDER BY id ASC
            """)
    List<Todo> findByAccountId(Integer accountId);


    //任务完成
    @Update("""
        UPDATE todos
        SET completed = #{completed}
        WHERE id = #{id}
          AND account_id = #{accountId}
        """)
    int updateStatus(
            @Param("id") Integer id,
            @Param("completed") Integer completed,
            @Param("accountId") Integer accountId);

    @Delete("DELETE FROM todos WHERE id = #{id}")
    int deleteById(Integer id);


    @Select("""
        SELECT id, title, completed,
               account_id AS accountId,
               created_time AS createdTime
        FROM todos
        WHERE account_id = #{accountId}
        ORDER BY id ASC
        LIMIT #{offset}, #{size}
        """)
    List<Todo> findPage(
            @Param("accountId") Integer accountId,
            @Param("offset") int offset,
            @Param("size") int size);

    @Select("""
        SELECT id, title, completed,
               account_id AS accountId,
               created_time AS createdTime
        FROM todos
        WHERE account_id = #{accountId}
          AND completed = #{completed}
        ORDER BY id DESC
        """)
    List<Todo> findByStatus(
            @Param("accountId") Integer accountId,
            @Param("completed") Integer completed);

    @Update("""
        UPDATE todos
        SET title = #{title}
        WHERE id = #{id}
        """)
    int updateTitle(
            @Param("id") Integer id,
            @Param("title") String title);
}




