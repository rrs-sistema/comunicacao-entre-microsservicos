package br.com.rrssistema.microserviceproductapi;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class MicroserviceProductApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceProductApiApplication.class, args);
	}

}
