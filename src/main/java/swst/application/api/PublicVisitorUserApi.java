package swst.application.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swst.application.controllers.users.PublicUserController;
import swst.application.models.users.CreateNewUserModel;
import swst.application.models.users.UsernamesModels;
import swst.application.responsemodels.LoginModel;
import swst.application.responsemodels.LoginResponseModel;

@RestController
@RequestMapping("/public")
public class PublicVisitorUserApi {
	@Autowired
	private PublicUserController publicUserController;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public void createNewUser(@RequestBody CreateNewUserModel newUser) {
		//return ResponseEntity.ok(publicUserController.register(newUser));
	}
	
	@PostMapping("/login")
	public LoginResponseModel userLogin(@RequestBody LoginModel userLogin) {
		return null;
	}
}
