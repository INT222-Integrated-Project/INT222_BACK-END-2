package sit221.marketapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@PropertySource("classpath:application.properties")

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {
	@Value("#{'${BACKENDV2.origin.method}'.split(',')}")
	private String[] methodList;
	@Value("#{'${BACKENDV2.origin.host}'.split(',')}")
	String[] hostList;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins(hostList).allowedMethods(methodList);
	}

}
