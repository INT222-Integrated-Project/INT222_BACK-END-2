package swst.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("userdefined.properties")
@PropertySource("application.properties")
@SpringBootApplication
public class SweetsweetiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SweetsweetiesApplication.class, args);
	}

}
