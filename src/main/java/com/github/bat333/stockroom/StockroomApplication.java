package com.github.bat333.stockroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StockroomApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockroomApplication.class, args);
	}

}
