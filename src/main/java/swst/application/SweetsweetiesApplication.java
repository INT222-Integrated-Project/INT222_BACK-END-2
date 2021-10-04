package swst.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@PropertySource("userdefined.properties")
@PropertySource("application.properties")
@SpringBootApplication
public class SweetsweetiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SweetsweetiesApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passWordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
