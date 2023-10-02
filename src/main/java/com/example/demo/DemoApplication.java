package com.example.demo;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public OpenAPI openApiInformation() {
		Server localServer = new Server()
				.url("http://localhost:8080")
				.description("Localhost Server URL");

		Contact contact = new Contact()
				.email("vincent.linas@zenika.com")
				.name("Vincent LINAS");

		Info info = new Info()
				.contact(contact)
				.description("Spring Boot 3")
				.summary("Demo of Spring Boot 3")
				.title("Spring Boot 3")
				.version("V1.0.0");

		return new OpenAPI().info(info).addServersItem(localServer);
	}

}
