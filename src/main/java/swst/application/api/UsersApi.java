package swst.application.api;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.RequiredArgsConstructor;
import swst.application.authenSecurity.TokenUtills;
import swst.application.controllers.controller.ProductsController;
import swst.application.controllers.controller.UserController;
import swst.application.entities.UsernamesModels;
import swst.application.models.LoginResponseModel;
import swst.application.repositories.UsernameRepository;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersApi {

	@Autowired
	private final ProductsController productsRESTcontroller;
	@Autowired
	private final UsernameRepository usernameRepository;
	@Autowired
	private final UserController userController;

	// [ getMyprofile ] Will return a profile of that user.
	@GetMapping("/myprofile")
	public ResponseEntity<UsernamesModels> getMyprofile(HttpServletRequest request) {
		return ResponseEntity.ok().body(usernameRepository.findByUserName(TokenUtills.getUserNameFromToken(request)));
	}

	// [ userLogOut ] Will remove a token from user.
	@GetMapping("/auth/logout")
	public ResponseEntity<LoginResponseModel> userLogOut(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader(HttpHeaders.AUTHORIZATION, "");
		return ResponseEntity.ok().body(new LoginResponseModel("User was here", ""));
	}

}
