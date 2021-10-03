package swst.application.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

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
import swst.application.models.TestJSONModel;
import swst.application.repositories.BrandsRepository;
import swst.application.repositories.ColorsRepository;

@RestController
@RequestMapping("/public")
public class PublicVisitorApi {
	@Autowired
	private PublicUserController publicUserController;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ProductsController productsRESTcontroller;
	@Autowired
	private ColorsRepository colorsRepository;
	@Autowired
	private ModelController modelController;
	@Autowired
	private BrandsRepository brandRepository;

	// [ createNewUser ] Will create new user.
	@PostMapping("/auth/register")
	public ResponseEntity<UsernamesModels> createNewUser(@RequestBody CreateNewUserModel newUser) {
		UsernamesModels createdUser = publicUserController.register(newUser);
		return ResponseEntity.ok(createdUser);
	}
	
	// [ userLogin ] Give the user login and token.
	@PostMapping("/auth/login")
	public LoginResponseModel userLogin(@RequestBody LoginModel userLogin) {
		return null;
	}

	@PostMapping("auth/psd")
	public String generatePass(@RequestBody CreateNewUserModel newUser) {
		return passwordEncoder.encode(newUser.getUserPassword());
	}

	@PostMapping("/auth/test")
	public ResponseEntity<CreateNewUserModel> testPost(@RequestBody CreateNewUserModel model) {
		return ResponseEntity.ok(model);
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

	// [ listAllBrands ]List all brands available in the repository.
	@GetMapping("/brands")
	public List<Brands> listAllBrands() {
		return brandRepository.findAll();
	}
}
