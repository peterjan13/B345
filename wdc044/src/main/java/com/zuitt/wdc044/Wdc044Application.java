package com.zuitt.wdc044;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


// "@SpringBootApplication" is an example of "Annotations" mark.
// Annotations are used to provide supplemental information about the program.
// These are used to manage and configure the behavior of the framework.
// Annotations are used extensively in Spring Boot to configure components, define dependencies, or enable specific functionalities
// Spring Boot scans for classes in its class path upon running and assigns behaviors on them based on their annotations.

// This specifies the main class of the Spring boot Application
@SpringBootApplication
// This indicates that the class is a controller that will handle RESTful web requests and returns a HTTP response.
@RestController
public class Wdc044Application {

	public static void main(String[] args) {
//		This method starts the whole Spring Framework
//		This serves as the entry point to start the application.
		SpringApplication.run(Wdc044Application.class, args);
	}

	//This is used for mapping HTTP GET Requests
	//Note: @GetMapping annotation is always followed by a "method body"
	//"method body" contains the logic to generate the response.
	@GetMapping("/hello")
	//@RequestParam is used to extract query parameters, form parameter followed by "key=value" pair
	//"?" means the start of parameters followed by the "key=value" pair.
	//localhost:8080/hello?name=Tolits
	//localhost:8080/hello
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name){
		//"%s" is a placeholder value for the name
		return String.format("Hello %s", name);
	}

//S01 Activity
	@GetMapping("/hi")
	public String hi(@RequestParam(value = "user", defaultValue = "user") String user) {
		return String.format("hi %s!", user);
	}
//S01 Stretch Goals
	@GetMapping("/nameAge")
	public String nameAge(@RequestParam(value = "name", defaultValue = "Juan") String name,
						  @RequestParam(value = "age", defaultValue = "18") int age) {
		return String.format("Hello %s! Your age is %d.", name, age);
	}
}

//NOTES:

// a. Creating a spring boot project
// Go to https://start.spring.io/ to initialize a spring boot project
// Set up the following:
// Project: Maven
// Language: Java
// Spring Boot: 2.7.15 (or the most previous update)
// Artifact & Name: projectName
// Group: com.nameOfThePackage
// Packaging: War (for creating web apps)
// Java: 11 (jdk version) / or the current installed jdk in your device
// Under Dependencies: Click Add Dependencies > Spring Web
// Click Generate
//b. Extract the Zip file in your batch folder and open the file in intellij
//c. Look for the pom.xml and do the following:
// Right Click pom.xml > Look for Maven > Download Sources > Wait for the  download to be done.
// Right Click again pom.xml > Maven >  Reload Project > This should fix any errors on first time of opening the project.
// Note: the step "c" should also be followed if new dependencies is added on the pom.xml
// d. Command for running the application:
// ./mvnw spring-boot:run
// Note: This will also allow us to serve the Springboot project on tomcat server.