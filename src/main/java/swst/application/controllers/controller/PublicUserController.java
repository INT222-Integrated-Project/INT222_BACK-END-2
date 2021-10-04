package swst.application.controllers.controller;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

	public LoginResponseModel authenUser(LoginModel loginUser) {
		UsernamesModels thisUser = usernameRepository.findByUserName(loginUser.getUserName());
		if (thisUser == null || !passwordEncoder.matches(loginUser.getUserPassword(), thisUser.getUserPassword())) {
			throw new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_BAD_CREDENTIALS,
					"[ AUTHEN FAILED ] Username or password doesn't match.");
		}
		if (thisUser.getRole() == rolesRepository.findByroleName("suspended")) {
			throw new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_NOT_ALLOWED, "[ SUS ] This accound is suspended.");
		}
		String acceessToken = "";
		String refreshToken = "";
		LoginResponseModel loginResponse = new LoginResponseModel(loginUser.getUserName(), acceessToken, refreshToken);
		return null;
	}
	/*
	 * public ResponseEntity<JwtTokenModel> userLogin(LoginModel userLogin){
	 * UsernamesModels thisUser =
	 * usernameRepository.findByUserName(userLogin.getUserName()); if(thisUser ==
	 * null || !passwordEncoder.matches(userLogin.getUserPassword(),
	 * thisUser.getUserPassword() )) { throw new
	 * ExceptionFoundation(EXCEPTION_CODES.AUTHEN_BAD_CREDENTIALS,
	 * "[ AUTHEN FAILED ] Username or password doesn't match."); }
	 * if(thisUser.getRole()==rolesRepository.findByroleName("suspended")) { throw
	 * new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_NOT_ALLOWED,
	 * "[ SUS ] THis accound is suspended."); }
	 * 
	 * String token = jwtTokenProvider.createJWToken(null, false) return new
	 * ResponseEntity<>(new JwtTokenModel(token), newHeader, HttpStatus.OK); }
	 */
	/*
	 * public ResponseEntity<JwtTokenModel> userLogin(LoginModel userLogin) {
	 * UsernamesModels thisUser =
	 * usernameRepository.findByUserName(userLogin.getUserName()); if (thisUser ==
	 * null || !passwordEncoder.matches(userLogin.getUserPassword(),
	 * thisUser.getUserPassword())) { throw new
	 * ExceptionFoundation(EXCEPTION_CODES.AUTHEN_BAD_CREDENTIALS,
	 * "[ AUTHEN FAILED ] Username or password doesn't match."); } if
	 * (thisUser.getRole() == rolesRepository.findByroleName("suspended")) { throw
	 * new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_NOT_ALLOWED,
	 * "[ SUS ] THis accound is suspended."); } UsernamePasswordAuthenticationToken
	 * authenWithToken = new UsernamePasswordAuthenticationToken(
	 * userLogin.getUserName(), userLogin.getUserPassword()); Authentication
	 * authentication =
	 * authenticationManagerBuilder.getObject().authenticate(authenWithToken);
	 * SecurityContextHolder.getContext().setAuthentication(authentication);
	 * 
	 * String token = jwtTokenProvider.createJWToken(authentication, false);
	 * 
	 * HttpHeaders newHeader = new HttpHeaders();
	 * newHeader.add(JwtTokenFilter.AUTHORIZATION_HEADER, "Bearer " + token);
	 * 
	 * return new ResponseEntity<>(new JwtTokenModel(token), newHeader,
	 * HttpStatus.OK); }
	 */

}
