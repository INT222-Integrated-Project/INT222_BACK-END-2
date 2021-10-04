/*package swst.application.authentication;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import swst.application.entities.UsernamesModels;

import static java.lang.String.format;

import java.security.Key;

@Component
@RequiredArgsConstructor
@PropertySource("userdefined.properties")
public class JwtTokenUtil {

	@Value("${application.jwt.secret}")
	private String jwtSecret;
	@Value("${application.jwt.secret}")
	private String jwtIssuer;

	private Key key;

	@Value("${jwt.expiration}")
	private int expiration;

	// Generate key from our secret.
	public void afterPropertiesSet() {
		byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	// Generate token for the authenticated user.
	public String generateAccessToken(UsernamesModels usernamesModels) {
		return Jwts.builder()
				.setSubject(format("%s,%s", usernamesModels.getUserNameID(), usernamesModels.getUserName()))
				.setIssuer(jwtIssuer).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	// Parse token to validate the user with that token.
	public String gerUserID(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claims.getSubject().split(",")[1];
	}

	// Get expiration by sending a token and check in this method.
	public Date hetExpirationDate(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(token).getBody();
		return claims.getExpiration();
	}

	// This will validate a contend in this token, preventing user for editing their
	// token. Throw exception when it is malfunction.
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (Exception exc) {
			return false;
		}
	}
}
*/