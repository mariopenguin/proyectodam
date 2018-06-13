package com.dam.servicios.servicios;

import com.dam.servicios.servicios.almacenamiento.StorageProperties;
import com.dam.servicios.servicios.almacenamiento.StorageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@ImportAutoConfiguration(FeignAutoConfiguration.class)
@EnableConfigurationProperties(StorageProperties.class)
public class Application extends SpringApplication{

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
}
