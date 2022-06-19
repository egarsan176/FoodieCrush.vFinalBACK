package com.example.demo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.Category;
import com.example.demo.model.User;
import com.example.demo.repository.CategoryRepo;
import com.example.demo.repository.UserRepo;

@SpringBootApplication
public class Foodie5Application extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(Foodie5Application.class, args);
	}
	//encriptación de la contraseña
@Autowired private PasswordEncoder passEncoder; 
	



}
