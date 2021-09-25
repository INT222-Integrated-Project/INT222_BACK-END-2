package swst.application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
@Configuration
@EnableWebMvc
public class CORSconfiguration {

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration configure = new CorsConfiguration();
		configure.setAllowCredentials(true);
		configure.addAllowedHeader("*");
		configure.addAllowedOrigin("*");
		configure.addAllowedMethod("*");
		
		source.registerCorsConfiguration("/**", configure);
		return new CorsFilter(source);
	}
}
