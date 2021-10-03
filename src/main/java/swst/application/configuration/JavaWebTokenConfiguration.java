package swst.application.configuration;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import swst.application.authentication.JwtTokenFilter;
import swst.application.authentication.TokenProvider;

public class JavaWebTokenConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private TokenProvider tokenProvider;

	public JavaWebTokenConfiguration(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	/*@Override
	public void configure(HttpSecurity http) {
		JwtTokenFilter customFilter = new JwtTokenFilter(tokenProvider);
		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}*/
}
