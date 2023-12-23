package com.eCommercoal.eCommercialWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories("com.eCommercoal.eCommercialWeb.esrepository")
public class ECommercialWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommercialWebApplication.class, args);
	}

}
