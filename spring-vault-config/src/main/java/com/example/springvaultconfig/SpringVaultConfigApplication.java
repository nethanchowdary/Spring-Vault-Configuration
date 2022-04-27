package com.example.springvaultconfig;

import com.example.springvaultconfig.config.VaultConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@Slf4j
public class SpringVaultConfigApplication implements CommandLineRunner {



	private VaultConfiguration credentials;

	public SpringVaultConfigApplication(VaultConfiguration credentials) {
		this.credentials = credentials;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringVaultConfigApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Username : "+credentials.getUsername());
		log.info("Password : "+credentials.getPassword());
	}
}
