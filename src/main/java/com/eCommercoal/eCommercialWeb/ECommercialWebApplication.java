package com.eCommercoal.eCommercialWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.eCommercoal.eCommercialWeb"})
public class ECommercialWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommercialWebApplication.class, args);
	}

}
