package com.in;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "serde","com.in"})
public class KafkaStreamAvroApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaStreamAvroApplication.class, args);
	}

}
