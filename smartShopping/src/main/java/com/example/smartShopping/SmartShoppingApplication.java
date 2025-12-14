package com.example.smartShopping;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SmartShoppingApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();

		dotenv.entries().forEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue())
		);

		System.out.println("kiem tra mat khau");

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String newHash = encoder.encode("Admin@!123");
		System.out.println(newHash);



		SpringApplication.run(SmartShoppingApplication.class, args);
	}

}
