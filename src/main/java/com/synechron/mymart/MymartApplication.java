package com.synechron.mymart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.synechron.mymart")
@SpringBootApplication
public class MymartApplication {

	public static void main(String[] args) {
		SpringApplication.run(MymartApplication.class, args);
	}

}
