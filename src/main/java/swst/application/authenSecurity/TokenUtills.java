package swst.application.authenSecurity;

import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import swst.application.errorsHandlers.ExceptionFoundation;
import swst.application.errorsHandlers.ExceptionresponsesModel.EXCEPTION_CODES;

@Service
@PropertySource("userdefined.properties")
public class TokenUtills {

	@Autowired
	private static String secret = "S2lyYWggSGFpdGFrYQ==";
	private static int expireInMili = 43200000;
	private static String issuedBy = "naturegecko";

	public static String createToken(User userLogin) {
		String accessToken = JWT.create().withSubject(userLogin.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + expireInMili)).withIssuer(issuedBy)
				.withClaim("roles", userLogin.getAuthorities().stream().map(GrantedAuthority::getAuthority)
						.collect(Collectors.toList()))
				.sign(getAlgorithm());
		return accessToken;
	}

	public static String createToken(String username, String[] roles) {
		String accessToken = JWT.create().withSubject(username)
				.withExpiresAt(new Date(System.currentTimeMillis() + expireInMili)).withIssuer(issuedBy)
				.withArrayClaim("roles", roles).sign(getAlgorithm());
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

	public static String getUserNameFromToken(HttpServletRequest request) {
		String userName;
		String authenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authenHeader != null && authenHeader.startsWith("Bearer ")) {
			String token = authenHeader.substring("Bearer ".length());
			JWTVerifier verifier = JWT.require(TokenUtills.getAlgorithm()).build();
			DecodedJWT decodedJWT = verifier.verify(token);
			userName = decodedJWT.getSubject();
			return userName;
		} else {
			throw new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_HORRIBLE_TOKEN,
					"[ FAILED ] Horrible token , this token is mulfunction.");
		}

	}
}
