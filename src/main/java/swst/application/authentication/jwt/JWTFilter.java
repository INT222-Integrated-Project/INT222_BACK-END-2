package swst.application.authentication.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JWTFilter extends GenericFilterBean {

	public static final String AUTHORIZATION_HEADER = "Authorization";
	private JWTTokenProvider tokenProvider;

	public JWTFilter(JWTTokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String jwt = resolveToken(httpRequest);
		
		if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
			Authentication authen = tokenProvider.getAuthenticationT(jwt);
			SecurityContextHolder.getContext().setAuthentication(authen);
		} else {
			System.out.println("No token found.");
		}
		
		chain.doFilter(request, response);
	}
	
	private String resolveToken(HttpServletRequest request) {
		String holderToken = request.getHeader(AUTHORIZATION_HEADER);
		if(StringUtils.hasText(holderToken) && holderToken.startsWith("Bearer ")) {
			return holderToken.substring(7);
		}
		return null;
	}

}
