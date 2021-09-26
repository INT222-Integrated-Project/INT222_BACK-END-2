package swst.application.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@PropertySource("userdefined.properties")
@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

	@Value("#{'${application.origin.method}'.split(',')}")
	private String[] allowedMethods;
	@Value("#{'${application.origin.host}'.split(',')}")
	private String[] alloweDomain;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins(alloweDomain).allowedMethods(allowedMethods);
	}

}
