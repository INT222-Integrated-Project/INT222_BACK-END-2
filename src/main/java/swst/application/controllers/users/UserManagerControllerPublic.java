package swst.application.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swst.application.models.users.UsernamesModels;
import swst.application.repositories.UsernameRepository;

@CrossOrigin("*")
@RequestMapping("/public/users")
@RestController
public class UserManagerControllerPublic {
	
	@Autowired
	private UsernameRepository usernameRepository;
	
	@PostMapping("/register")
	public String register(@RequestBody UsernamesModels newuser) {
		return null;
	}
}
