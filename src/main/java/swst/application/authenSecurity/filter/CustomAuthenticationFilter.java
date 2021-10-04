package swst.application.authenSecurity.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.RequiredArgsConstructor;
import swst.application.authenSecurity.SecurityUtills;
import swst.application.authenSecurity.UserNameService;
import swst.application.entities.UsernamesModels;
import swst.application.models.LoginModel;
import swst.application.models.LoginResponseModel;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username = request.getParameter("userName");
		String password = request.getParameter("password");
		UsernamePasswordAuthenticationToken authenToken = new UsernamePasswordAuthenticationToken(username, password);
		return authenticationManager.authenticate(authenToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authen) throws IOException, ServletException {
		User userLogin = (User) authen.getPrincipal();
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
		String accessToken = JWT.create().withSubject(userLogin.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 43200000))
				.withIssuer("naturegecko").withClaim("roles", userLogin.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);

		String refreshToken = JWT.create().withSubject(userLogin.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 60000))
				.withIssuer(request.getRequestURL().toString()).sign(algorithm);

		response.setHeader("accessToken", accessToken);
		response.setHeader("refreshToken", refreshToken);
	}

}