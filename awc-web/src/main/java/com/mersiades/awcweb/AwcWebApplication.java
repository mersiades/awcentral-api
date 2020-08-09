package com.mersiades.awcweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.mersiades.awcdata", "com.mersiades.awcweb"})
@SpringBootApplication
public class AwcWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwcWebApplication.class, args);
	}

}
