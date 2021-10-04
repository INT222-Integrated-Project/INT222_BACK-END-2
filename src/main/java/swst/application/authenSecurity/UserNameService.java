package swst.application.authenSecurity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import swst.application.entities.Roles;
import swst.application.entities.UsernamesModels;
import swst.application.errorsHandlers.ExceptionFoundation;
import swst.application.errorsHandlers.ExceptionresponsesModel.EXCEPTION_CODES;
import swst.application.repositories.RolesRepository;
import swst.application.repositories.UsernameRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class UserNameService implements UserNameModelService, UserDetailsService {

	private final UsernameRepository userNameRepository;
	private final RolesRepository rolesRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsernamesModels user = userNameRepository.findByUserName(username);
		if (user == null) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND, username + "not esist");
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		Roles userRole = user.getRole();
		authorities.add(new SimpleGrantedAuthority(userRole.getRoleName()));
		return new User(user.getUserName(), user.getUserPassword(), authorities);
	}

	@Override
	public UsernamesModels saveNewUser(UsernamesModels newUser) {
		newUser.setUserPassword(passwordEncoder.encode(newUser.getUserPassword()));
		return userNameRepository.save(newUser);
	}

	@Override
	public void assignRole(String username, String roleName) {
		UsernamesModels user = userNameRepository.findByUserName(username);
		Roles role = rolesRepository.findByroleName(roleName);
		user.setRole(role);
	}

	@Override
	public UsernamesModels getUserByName(String username) {
		return userNameRepository.findByUserName(username);
	}

	public List<UsernamesModels> getAllUsers() {
		return userNameRepository.findAll();
	}

}
