package swst.application.api;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import swst.application.authenSecurity.UserNameService;
import swst.application.controllers.controller.ModelController;
import swst.application.controllers.controller.ProductsController;
import swst.application.controllers.controller.PublicUserController;
import swst.application.entities.Brands;
import swst.application.entities.Colors;
import swst.application.entities.Models;
import swst.application.entities.Products;
import swst.application.entities.UsernamesModels;
import swst.application.models.CreateNewUserModel;
import swst.application.models.LoginModel;
import swst.application.models.LoginResponseModel;
import swst.application.repositories.BrandsRepository;
import swst.application.repositories.ColorsRepository;
import swst.application.repositories.UsernameRepository;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicVisitorApi {
	@Autowired
	private final PublicUserController publicUserController;
	@Autowired
	private final PasswordEncoder passwordEncoder;
	@Autowired
	private final ProductsController productsRESTcontroller;
	@Autowired
	private final ColorsRepository colorsRepository;
	@Autowired
	private final ModelController modelController;
	@Autowired
	private final BrandsRepository brandRepository;
	@Autowired
	private final UserNameService userNameService;
	@Autowired
	private final UsernameRepository usernameRepository;

	@GetMapping("/user")
	public ResponseEntity<List<UsernamesModels>> getAllUser() {
		return ResponseEntity.ok().body(userNameService.getAllUsers());
	}

	// [ createNewUser ] Will create new user.
	@PostMapping("/auth/register")
	public ResponseEntity<HttpStatus> createNewUser(@RequestPart CreateNewUserModel newUser) {
		publicUserController.register(newUser);
		URI uri = URI
				.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/public/auth/register").toString());
		return ResponseEntity.created(uri).body(null);
	}

	// [ userLogin ] Give the user login and token.
	@PostMapping("/auth/login")
	public ResponseEntity<LoginResponseModel> userLogin(@RequestBody LoginModel userLogin) {
		return ResponseEntity.ok().body(publicUserController.authenUser(userLogin));
	}

	// [ listProductWithPage ] Will list product with page, optional with name.
	@GetMapping("/products")
	public Page<Products> listProductWithPage(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "9") int size, @RequestParam(defaultValue = "") String searchname) {
		return productsRESTcontroller.listProductOnStore(page, size, searchname);
	}

	// [ listModelWithpage ] List all models in the database with page.
	@GetMapping("/models")
	public Page<Models> listModelWithpage(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "30") int size, @RequestParam(defaultValue = "") String searchname) {
		return modelController.listModelsByPage(page, size, searchname);
	}

	// [ listAllColors ] List all colors available in the repository.
	@GetMapping("/colors")
	public List<Colors> listAllColors() {
		return colorsRepository.findAll();
	}

	// [ listAllBrands ] List all brands available in the repository.
	@GetMapping("/brands")
	public List<Brands> listAllBrands() {
		return brandRepository.findAll();
	}
}
