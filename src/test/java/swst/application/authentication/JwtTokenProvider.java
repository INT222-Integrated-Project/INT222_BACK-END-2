/*package swst.application.authentication;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider implements InitializingBean {

	private static final String AUTHORITIES_KEY = "authen";

	private final String base64Secret;
	private final long tokenValidityInMilliseconds;
	private final long tokenValidityInMillisecondsForRememberMe;

	private Key key;

	public JwtTokenProvider(@Value("${jwt.base64-secret}") String base64Secret,
			@Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds,
			@Value("${jwt.token-validity-in-seconds-for-remember-me}") long tokenValidityInSecondsForRememberMe) {
		this.base64Secret = base64Secret;
		this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
		this.tokenValidityInMillisecondsForRememberMe = tokenValidityInSecondsForRememberMe * 1000;
	}

	@Override
	public void afterPropertiesSet() {
		// TODO Auto-generated method stub
		byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public String createJWToken(Authentication authen, boolean rememberThis) {
		// String authorities =
		// authen.getAuthorities().stream().map(GrantedAuthority::getAuthority)
		String authority = authen.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		long current = (new Date()).getTime();
		Date validity;

		if (rememberThis) {
			validity = new Date(current + this.tokenValidityInMillisecondsForRememberMe);
		} else {
			validity = new Date(current + this.tokenValidityInMilliseconds);
		}

		return Jwts.builder().setSubject(authen.getName()).claim(AUTHORITIES_KEY, authority)
				.signWith(key, SignatureAlgorithm.HS512).setExpiration(validity).compact();
	}

	public Authentication getAuthenticationT(String token) {
		Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(claims.get(AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		User principal = new User(claims.getSubject(), "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

	public boolean validateToken(String authenToken) {
		try {
			Jwts.parser().setSigningKey(key).parseClaimsJws(authenToken);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException exc) {
			System.out.println("Invalid JWT signature.");
		} catch (ExpiredJwtException exc) {
			System.out.println("This token is expired.");
		} catch (UnsupportedJwtException exc) {
			System.out.println("Unsupported Token.");
		} catch (IllegalArgumentException exc) {
			System.out.println("Token is invalid.");
		}
		return false;
	}

}*/
