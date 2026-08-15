package com.example.demo;
import jakarta.validation.constraints.NotBlank;
public class User {

    private Integer id;

    @NotBlank(message = "姓名为空")
    private String name;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public  void setId(Integer id){
        this.id=id;
    }
}
