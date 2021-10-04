package swst.application.authenSecurity;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
@PropertySource("userdefined.properties")
public class SecurityUtills {

	@Autowired
	private static String secret = "S2lyYWggSGFpdGFrYQ==";
	private static int expireInMili = 43200000;
	private static String issuedBy = "naturegecko";

	public static String createToken(User userLogin) {
		String accessToken = JWT.create().withSubject(userLogin.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() * expireInMili)).withIssuer(issuedBy)
				.withClaim("roles", userLogin.getAuthorities().stream().map(GrantedAuthority::getAuthority)
						.collect(Collectors.toList()))
				.sign(getAlgorithm());
		return accessToken;
	}

	public static String createRefreshToken(User userLogin) {
		String refreshToken = JWT.create().withSubject(userLogin.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + expireInMili + 60000)).withIssuer(issuedBy)
				.sign(getAlgorithm());
		return refreshToken;
	}

	public static Algorithm getAlgorithm() {
		return Algorithm.HMAC256(secret.getBytes());
	}
}
