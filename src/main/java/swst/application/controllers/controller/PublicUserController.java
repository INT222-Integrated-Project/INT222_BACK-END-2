package swst.application.controllers.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import swst.application.authenSecurity.filter.CustomAuthenticationFilter;
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
		UsernamesModels requestUser = usernameRepository.findByUserName(loginUser.getUserName());
		if (requestUser == null
				|| !passwordEncoder.matches(loginUser.getUserPassword(), requestUser.getUserPassword())) {
			throw new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_BAD_CREDENTIALS,
					"[ AUTHEN FAILED ] Username or password doesn't match.");
		}
		if (requestUser.getRole() == rolesRepository.findByroleName("suspended")) {
			throw new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_NOT_ALLOWED, "[ SUS ] This accound is suspended.");
		}

		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
		String[] roles = { "admin", "customer" };
		String token = JWT.create().withSubject(loginUser.getUserName())
				.withExpiresAt(new Date(System.currentTimeMillis() * 600000)).withIssuer("naturegecko")
				.withArrayClaim("roles", roles).sign(algorithm);

		LoginResponseModel loginResponse = new LoginResponseModel(loginUser.getUserName(), token);
		return loginResponse;
	}

}
