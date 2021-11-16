package swst.application.api;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.log.LogAccessor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import swst.application.controllers.UserController;
import swst.application.entities.UsernamesModels;
import swst.application.entities.seperated.UserNameModelEdit;
import swst.application.errorsHandlers.ExceptionFoundation;
import swst.application.errorsHandlers.ExceptionresponsesModel.EXCEPTION_CODES;
import swst.application.models.ActionResponseModel;
import swst.application.models.GetUserModel;
import swst.application.repositories.UsernameRepository;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminApi {

	@Autowired
	private final UsernameRepository usernameRepository;
	@Autowired
	private final UserController userController;

	// [ listUser]
	@GetMapping("/listUser")
	public ResponseEntity<Page<UsernamesModels>> listAllUsers() {
		return ResponseEntity.ok().body(userController.listUserByPage(0, 0, ""));
	}

	// [ searchUserByNameOrEmail ]
	@GetMapping("/listUser/{searchcontent}")
	public ResponseEntity<Page<UsernamesModels>> searchUserByNameOrEmail(@PathVariable String searchcontent) {
		return ResponseEntity.ok().body(userController.listUserByPage(0, 0, ""));
	}

	// [ searchByTelephoneNumber ]
	@GetMapping("/listUser/searchByPhone/{phoneNumber}")
	public ResponseEntity<List<UsernamesModels>> searchByTelephoneNumber(@RequestParam String phoneNumber) {
		return ResponseEntity.ok().body(usernameRepository.findByPhoneNumber(phoneNumber));
	}

	// [ assignRoleToUser ]
	@PostMapping("/assignRole")
	public ResponseEntity<GetUserModel> assignRoleToUser(@RequestBody GetUserModel getUser) {
		userController.assignRole(getUser);
		return ResponseEntity.ok().body(getUser);
	}

	// [ editUser ]
	/*@PostMapping("/editUser")
	public ResponseEntity<ActionResponseModel> editUser(@RequestPart UserNameModelEdit newInformation,
			HttpServletRequest request) {
		return ResponseEntity.ok().body(userController.editUser(newInformation, request));
	}*/

	// [ changePassword ]

}
