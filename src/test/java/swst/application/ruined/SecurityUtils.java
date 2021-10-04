package swst.application.ruined;
/*package swst.application.authentication;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

	// This class will get the login of the current user and return login to that
	// user.

	public static Optional<String> getCurrentUserName() {
		final Authentication authen = SecurityContextHolder.getContext().getAuthentication();

		if (authen == null) {
			return Optional.empty();
		}

		String username = null;
		if (authen.getPrincipal() instanceof UserDetails) {
			UserDetails secureUser = (UserDetails) authen.getPrincipal();
			username = secureUser.getUsername();
		} else if (authen.getPrincipal() instanceof String) {
			username = (String) authen.getPrincipal();
		}

		return Optional.ofNullable(username);
	}

}
*/