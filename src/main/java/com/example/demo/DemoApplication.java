package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 标记这是 Spring Boot 应用的启动类，并启用自动配置和组件扫描。
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		// 启动 Spring Boot 应用，并将命令行参数传递给 Spring 容器。
		SpringApplication.run(DemoApplication.class, args);
	}

}
