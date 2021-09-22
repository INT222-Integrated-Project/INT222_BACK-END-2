package swst.application.configuration;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import swst.application.authentication.jwt.JWTFilter;
import swst.application.authentication.jwt.JWTTokenProvider;

public class JavaWebTokenConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private JWTTokenProvider tokenProvider;

	public JavaWebTokenConfiguration(JWTTokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void configure(HttpSecurity http) {
		JWTFilter customFilter = new JWTFilter(tokenProvider);
		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
