package com.user.social.communicate;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.user.social.communicate.file.FileStorageProperties;

@SpringBootApplication

@EnableJpaRepositories
@EnableConfigurationProperties({FileStorageProperties.class})
public class OneTomanyApplication {

	public static void main(String[] args) {
		SpringApplication application= new SpringApplication(OneTomanyApplication.class);
		application.setBannerMode(Mode.CONSOLE);
		application.run(args);
		
	}
}
