package swst.application.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import swst.application.repositories.UsernameRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;

import static org.springframework.util.StringUtils.isEmpty;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter /*extends OncePerRequestFilter*/ {

	private final JwtTokenUtil jwtTokenUtil;
	private final UsernameRepository usernameRepository;

	/*@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Get authorization header and validate HERE!
		final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (!header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		// Get token and validate.
		final String token = header.split(" ")[1].trim();
		if (!jwtTokenUtil.validateToken(token)) {
			filterChain.doFilter(request, response);
			return;
		}

		// Get user identity and place it on the spring security.
		//UserDetails userDetails = usernameRepository.findByUserName(jwtTokenUtil.getUser)).orElse(null);
	}*/

}

/*
 * public static final String AUTHORIZATION_HEADER = "Authorization"; private
 * TokenProvider tokenProvider;
 * 
 * public JwtFilter(TokenProvider tokenProvider) { this.tokenProvider =
 * tokenProvider; }
 * 
 * @Override public void doFilter(ServletRequest request, ServletResponse
 * response, FilterChain chain) throws IOException, ServletException {
 * HttpServletRequest httpRequest = (HttpServletRequest) request; String jwt =
 * resolveToken(httpRequest);
 * 
 * if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
 * Authentication authen = tokenProvider.getAuthenticationT(jwt);
 * SecurityContextHolder.getContext().setAuthentication(authen); } else {
 * System.out.println("No token found."); } chain.doFilter(request, response); }
 * 
 * private String resolveToken(HttpServletRequest request) { String holderToken
 * = request.getHeader(AUTHORIZATION_HEADER); if
 * (StringUtils.hasText(holderToken) && holderToken.startsWith("Bearer ")) {
 * return holderToken.substring(7); } return null; }
 */
