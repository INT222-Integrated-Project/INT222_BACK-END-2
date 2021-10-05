package swst.application.api;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.log.LogAccessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import swst.application.controllers.UserController;
import swst.application.entities.UsernamesModels;
import swst.application.models.GetUserModel;
import swst.application.repositories.UsernameRepository;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@Slf4j
public class AdminApi {

	@Autowired
	private final UsernameRepository usernameRepository;
	@Autowired
	private final UserController userController;

	@GetMapping("/us")
	public ResponseEntity<List<UsernamesModels>> listAllUsers() {
		return ResponseEntity.ok().body(usernameRepository.findAll());
	}

	// [ assignRoleToUser ] Will get rolename and user from FRONT and assign that
	// role to user.
	@PostMapping("/assignRole")
	public ResponseEntity<GetUserModel> assignRoleToUser(@RequestBody GetUserModel getUser) {
		userController.assignRole(getUser);
		return ResponseEntity.ok().body(getUser);
	}

}
