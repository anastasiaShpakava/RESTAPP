package com.leverX.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class BlogApplication  {
//@Bean
//	JedisConnectionFactory jedisConnectionFactory() {
//	return new JedisConnectionFactory();
//	}
//	@Bean
//	RedisTemplate<String, User> redisTemplate (){
//	RedisTemplate<String, User> redisTemplate=new RedisTemplate<>();
//	redisTemplate().setConnectionFactory(jedisConnectionFactory());
//	return redisTemplate;
//	}
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
}