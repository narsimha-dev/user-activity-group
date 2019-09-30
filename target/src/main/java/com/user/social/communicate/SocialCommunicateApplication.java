package com.user.social.communicate;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SocialCommunicateApplication {

	public static void main(String[] args) {
		SpringApplication application= new SpringApplication(SocialCommunicateApplication.class);
		application.setBannerMode(Mode.CONSOLE);
		application.run(args);
		
	}
}
