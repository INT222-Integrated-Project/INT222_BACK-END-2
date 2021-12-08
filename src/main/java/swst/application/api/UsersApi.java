package swst.application.api;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import swst.application.authenSecurity.TokenUtills;
import swst.application.controllers.ProductOrderController;
import swst.application.controllers.UserController;
import swst.application.entities.Orders;
import swst.application.entities.UsernamesModels;
import swst.application.models.ActionResponseModel;
import swst.application.models.LoginResponseModel;
import swst.application.repositories.UsernameRepository;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersApi {

	@Autowired
	private final UsernameRepository usernameRepository;
	@Autowired
	private final UserController userController;
	@Autowired
	private final ProductOrderController productOrderController;

	// [ editMyprofile ]
	@PutMapping(value = "/editMyprofile", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.IMAGE_JPEG_VALUE })
	public ResponseEntity<UsernamesModels> editMyprofile(@RequestPart(required = true) UsernamesModels newProfileInfo,
			@RequestPart(required = false) MultipartFile userProfileImage, HttpServletRequest request) {
		URI uri = URI
				.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/editMyprodile").toString());
		return ResponseEntity.created(uri).body(userController.editUser(newProfileInfo, userProfileImage, request));
	}

	// [ uploadProfileImage ]
	
	// [ ListOrderedOrderByUserID ]
	public ResponseEntity<Page<Orders>> ListOrderedOrderByUserID(HttpServletRequest httpServletRequest){
		return ResponseEntity.ok().body(productOrderController.ListOrderedOrderByUserID(httpServletRequest));
	}

	// [ cancleUserOrder ]
	@PutMapping("/cancleorder")
	public ResponseEntity<Orders> cancleOrder(@RequestParam long orderId, HttpServletRequest request) {
		URI uri = URI.create(
				ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/cancleorder/" + orderId).toString());
		return ResponseEntity.created(uri).body(productOrderController.cancelOrder(orderId, request));
	}

	// [ getMyprofile ] Will return a profile of that user.
	@GetMapping("/myprofile")
	public ResponseEntity<UsernamesModels> getMyprofile(HttpServletRequest request) {
		return ResponseEntity.ok().body(usernameRepository.findByUserName(TokenUtills.getUserNameFromToken(request)));
	}

	// [ userLogOut ] Will remove a token from user.
	@GetMapping("/auth/logout")
	public ResponseEntity<LoginResponseModel> userLogOut(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader(HttpHeaders.AUTHORIZATION, "");
		return ResponseEntity.ok().body(new LoginResponseModel("User was here", "", null));
	}

	// [ getMyOrder ]
	@GetMapping("/myOrders")
	public Page<Orders> listMyOrder(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "15") int size, HttpServletRequest request) {
		return productOrderController.listOrderByUserID(page, size, request);
	}

	// [ addOrders ]
	@PostMapping(value = "/addOrder")
	public ResponseEntity<ActionResponseModel> addOrder(@RequestPart Orders newOrders, HttpServletRequest request) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/addOrder").toString());
		return ResponseEntity.created(uri).body(productOrderController.addOrder(request, newOrders));
	}

	// [ addOneOrder ]
	@PostMapping(value = "/purchase")
	public ResponseEntity<Orders> addOneOrder(@RequestParam int productColorId, @RequestParam int quantity,
			HttpServletRequest request) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/purchase").toString());
		return ResponseEntity.created(uri)
				.body(productOrderController.purchaseOneProduct(productColorId, quantity, request));
	}

	// [ changePassword ]
	@PutMapping("/changepassword")
	public ResponseEntity<ActionResponseModel> changePassword(@RequestParam String oldPassword,
			@RequestParam String newPassword, HttpServletRequest request) {
		URI uri = URI
				.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/changepassword/").toString());
		return ResponseEntity.created(uri).body(userController.changePassword(oldPassword, newPassword, request));
	}

	// [ deleteMyAccount ]
	@PutMapping("/deleteMyAccount")
	public ResponseEntity<ActionResponseModel> deleteMyAccount(HttpServletRequest request,
			HttpServletResponse response) {
		URI uri = URI
				.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/deleteMyAccount").toString());
		userLogOut(request, response);
		return ResponseEntity.created(uri).body(userController.deleteUserAccount(null, request));
	}

}
