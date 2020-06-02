package com.leverX.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}
//	Данный класс имеет main метод, который является точкой входа в приложение.
//		Аннотация SpringBootApplication указывает что приложение будет запускаться
//		как Spring Boot приложение. Она также указывает спрингу, что для всего что
//		у нас есть в файле зависимостей pom.xml нужно подключить авто конфигурацию.