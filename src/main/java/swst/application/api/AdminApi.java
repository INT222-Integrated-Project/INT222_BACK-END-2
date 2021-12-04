package swst.application.api;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.AllArgsConstructor;
import swst.application.controllers.ProductOrderController;
import swst.application.controllers.UserController;
import swst.application.entities.Orders;
import swst.application.entities.UsernamesModels;
import swst.application.models.ActionResponseModel;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminApi {

	@Autowired
	private final UserController userController;
	@Autowired
	private final ProductOrderController productOrderController;

	// [ listUser]
	@GetMapping("/listUser")
	public ResponseEntity<Page<UsernamesModels>> listAllUsers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "200") int size,
			@RequestParam(defaultValue = "", required = false) String searchContent) {
		return ResponseEntity.ok().body(userController.listUserByPage(page, size, searchContent));
	}

	// [ searchByTelephoneNumber ]
	@GetMapping("/listUser/searchByPhone")
	public ResponseEntity<Page<UsernamesModels>> searchByTelephoneNumber(
			@RequestParam(defaultValue = "") String phoneNumber) {
		return ResponseEntity.ok().body(userController.listUserByPhone(phoneNumber));
	}

	// [ assignRoleToUser ]
	@PutMapping("/assignRole")
	public ResponseEntity<ActionResponseModel> assignRoleToUser(@RequestParam int userNameID,
			@RequestParam int roleID) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/admin/assignRole").toString());
		return ResponseEntity.created(uri).body(userController.assignRole(userNameID, roleID));
	}

	// [ listAllOrders ]
	@GetMapping("/orders")
	public ResponseEntity<Page<Orders>> listAllOrders(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "200") int size,
			@RequestParam(defaultValue = "", required = false) String searchContent,
			@RequestParam(defaultValue = "", required = false) String searchStatus) {
		return ResponseEntity.ok().body(productOrderController.ListAllOrders(page, size, searchContent,searchStatus));
	}

}
