package com.example.smartShopping;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SmartShoppingApplication {

	// public static void main(String[] args) {
	// 	SpringApplication.run(SmartShoppingApplication.class, args);
	// }
	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SmartShoppingApplication.class, args);
        String user = context.getEnvironment().getProperty("DB_USERNAME");
        System.out.println(">>> DB_USERNAME from .env = " + user);
    }

}
