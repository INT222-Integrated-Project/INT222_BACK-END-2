package swst.application.controllers.users;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swst.application.exceptionhandlers.ExceptionDetails;
import swst.application.exceptionhandlers.ExceptionDetails.EXCEPTION_CODES;
import swst.application.exceptionhandlers.ExceptionFoundation;
import swst.application.models.users.CreateNewUserModel;
import swst.application.models.users.UsernamesModels;
import swst.application.repositories.UsernameRepository;
import swst.application.responsemodels.LoginModel;
import swst.application.responsemodels.LoginResponseModel;

@Service
public class PublicUserController {
	@Autowired
	private UsernameRepository usernameRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public void register(CreateNewUserModel newuser) {
		if (usernameRepository.existsByUserNameIgnoreCase(newuser.getUserName())) {
			throw new ExceptionFoundation(ExceptionDetails.EXCEPTION_CODES.AUTHEN_USERNAME_ALREADY_EXISTED,
					"[ EXISTED ] This username is used.");
		}
		if (usernameRepository.existsByPhoneNumber(newuser.getPhone())) {
			throw new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_PHONE_NUMBER_ALREADY_EXISTED,
					"[ EXISTED ] This phone number is used.");
		}

		UsernamesModels newUserModel = new UsernamesModels();

	}
	
	public LoginResponseModel userLogin(LoginModel userLogin) {
		return null;
	}

}
