package swst.application.ruined;
/*package swst.application.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

	private JwtTokenProvider jwtTokenProvider;
	public static final String AUTHORIZATION_HEADER = "Authorization";

	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String jwt = resolveToken(httpServletRequest);

		if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
			Authentication authen = jwtTokenProvider.getAuthenticationT(jwt);
			SecurityContextHolder.getContext().setAuthentication(authen);
		}

		filterChain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

}*/

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
