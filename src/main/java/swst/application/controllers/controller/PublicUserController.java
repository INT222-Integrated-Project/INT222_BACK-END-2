package swst.application.controllers.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class PublicUserController {
	@Autowired
	private UsernameRepository usernameRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RolesRepository rolesRepository;

	public UsernamesModels register(CreateNewUserModel newuser) {
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

	/*
	 * public LoginResponseModel userLogin(LoginModel userLogin) { String
	 * proviceToken = token return null; }
	 */

}
