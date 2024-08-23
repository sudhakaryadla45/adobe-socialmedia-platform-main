package com.ip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition(info = @Info(title = "Adobe - Social media platform", version = "1.1",
description = "This is a comprehensive social media platform API built using SpringBoot that provides all the fundamental CRUD operations. Users can perform a range of tasks, including creating, updating, and deleting user profiles, creating posts, and liking posts. The API also includes analytical pages that display user engagement and content popularity. To ensure data integrity, we have implemented basic validation rules for input data and created customized exceptions for various parts of the application. The API is RESTful, and we have created endpoints such as POST, GET, PUT, and DELETE for user and post data manipulation.",
		contact = @Contact(name = "Indrajit Paul", email = "indraindrani@gmail.com", url = "https://lu.ma/ip")
),
servers = {
			@Server(url = "/", description = "Default Server URL")
		  }

)
@SpringBootApplication
public class BackendSocialMediaPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendSocialMediaPlatformApplication.class, args);
	}

}
