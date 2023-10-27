package com.example.doctoranywhere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DoctoranywhereApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoctoranywhereApplication.class, args);
	}

}
