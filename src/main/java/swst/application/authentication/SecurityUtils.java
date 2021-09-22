package swst.application.authentication;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

	private SecurityUtils() {

	}

	public static Optional<String> getCurrentUsername() {
		final Authentication authen = SecurityContextHolder.getContext().getAuthentication();

		if (authen == null) {
			return Optional.empty();
		}

		String username = null;
		if (authen.getPrincipal() instanceof UserDetails) {
			UserDetails securityUser = (UserDetails) authen.getPrincipal();
			username = securityUser.getUsername();
		} else if (authen.getPrincipal() instanceof String) {
			username = (String) authen.getPrincipal();
		}

		return Optional.ofNullable(username);
	}

}
