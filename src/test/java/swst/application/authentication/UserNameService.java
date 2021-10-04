/*package swst.application.authentication;

import java.util.Optional;

import org.apache.catalina.security.SecurityUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import swst.application.entities.UsernamesModels;
import swst.application.repositories.RolesRepository;
import swst.application.repositories.UsernameRepository;

@Service
@Transactional
public class UserNameService {

	private UsernameRepository usernameRepository;
	private RolesRepository rolesRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserNameService(UsernameRepository usernameRepository) {
		this.usernameRepository = usernameRepository;
	}
	
	@Transactional(readOnly = true)
	public Optional<UsernamesModels> getUserWithAuthorities(){
		//return SecurityUtil.getCu
		return null;
	}

	/*@Autowired
	public UserNameService(UsernameRepository usernameRepository, 
			RolesRepository rolesRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.usernameRepository = usernameRepository;
		this.rolesRepository = rolesRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}*/
	
	/*public UsernamesModels findUserByName(String username) {
		return usernameRepository.findByUserName(username);
	}
	
	public UsernamesModels saveUser(UsernamesModels user) {
		user.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
		user.setRoleID(1);
		return usernameRepository.save(user);
	}
}
*/