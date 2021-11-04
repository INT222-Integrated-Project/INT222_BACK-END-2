package swst.application.controllers;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.extern.slf4j.Slf4j;
import swst.application.authenSecurity.TokenUtills;
import swst.application.entities.Roles;
import swst.application.entities.UsernamesModels;
import swst.application.errorsHandlers.ExceptionresponsesModel;
import swst.application.errorsHandlers.ExceptionFoundation;
import swst.application.errorsHandlers.ExceptionresponsesModel.EXCEPTION_CODES;
import swst.application.models.CreateNewUserModel;
import swst.application.models.LoginModel;
import swst.application.models.LoginResponseModel;
import swst.application.repositories.RolesRepository;
import swst.application.repositories.UsernameRepository;

@Service
@Slf4j
public class PublicUserController {
	@Autowired
	private UsernameRepository usernameRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RolesRepository rolesRepository;

	public UsernamesModels register(CreateNewUserModel newuser) {

		if (!Pattern.matches("[a-zA-Z0-9_]+", newuser.getUserName())) {
			throw new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_ILLEGAL_CHAR,
					"[ ILLEGAL USER NAME ] Username can only contain A-Z or digits.");
		}
		if (usernameRepository.existsByUserNameIgnoreCase(newuser.getUserName())) {
			throw new ExceptionFoundation(ExceptionresponsesModel.EXCEPTION_CODES.AUTHEN_USERNAME_ALREADY_EXISTED,
					"[ EXISTED ] This username is used.");
		}
		if (usernameRepository.existsByPhoneNumber(newuser.getPhone())) {
			throw new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_PHONE_NUMBER_ALREADY_EXISTED,
					"[ EXISTED ] This phone number is occupied.");
		}

		UsernamesModels newModel = new UsernamesModels();
		newModel.setUserName(newuser.getUserName());
		newModel.setUserPassword(passwordEncoder.encode(newuser.getUserPassword()));
		newModel.setFirstName(newuser.getFirstName());
		newModel.setLastName(newuser.getLastName());
		newModel.setPhoneNumber(newuser.getPhone());
		newModel.setRole(rolesRepository.findByroleName("customer"));

		usernameRepository.save(newModel);
		return newModel;

	}

	public LoginResponseModel authenUser(LoginModel loginUser, HttpServletResponse response) {
		UsernamesModels requestUser = usernameRepository.findByUserName(loginUser.getUserName());
		if (requestUser == null
				|| !passwordEncoder.matches(loginUser.getUserPassword(), requestUser.getUserPassword())) {
			throw new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_BAD_CREDENTIALS,
					"[ AUTHEN FAILED ] Username or password doesn't match.");
		}
		if (requestUser.getRole() == rolesRepository.findByroleName("suspended")) {
			throw new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_NOT_ALLOWED, "[ SUS ] This accound is suspended.");
		}

		String[] roles = { "" };
		Roles getUserRoles = requestUser.getRole();
		roles[0] = getUserRoles.getRoleName();

		String token = TokenUtills.createToken(requestUser.getUserName(),roles);
		response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		LoginResponseModel loginResponse = new LoginResponseModel(loginUser.getUserName().toLowerCase(), token);
		return loginResponse;
	}

}
