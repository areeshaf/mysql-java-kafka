package com.example.mysqlKafkaJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.example.mysqlKafkaJava.repository")
@SpringBootApplication
public class MysqlKafkaJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MysqlKafkaJavaApplication.class, args);
	}

}
