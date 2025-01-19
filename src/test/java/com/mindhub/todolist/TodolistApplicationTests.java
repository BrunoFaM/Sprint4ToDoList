package com.mindhub.todolist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class TodolistApplicationTests {

	@Test
	void contextLoads() {

	}

	@Test
	public void testPropertiesFile(@Value("${jwt.secret}") String secret){
		System.out.println(secret);
	}

}
