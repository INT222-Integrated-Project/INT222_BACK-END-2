package swst.application.authenSecurity.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import swst.application.authenSecurity.TokenUtills;

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
		String accessToken = TokenUtills.createToken(userLogin);
		String refreshToken = TokenUtills.createRefreshToken(userLogin);

		response.setHeader("accessToken", accessToken);
		response.setHeader("refreshToken", refreshToken);
		
	}

}