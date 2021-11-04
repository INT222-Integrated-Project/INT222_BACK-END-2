package swst.application.api;

import java.net.URI;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

import lombok.RequiredArgsConstructor;
import swst.application.controllers.ModelController;
import swst.application.controllers.ProductsController;
import swst.application.controllers.PublicUserController;
import swst.application.entities.Brands;
import swst.application.entities.Colors;
import swst.application.entities.Models;
import swst.application.entities.Products;
import swst.application.entities.ProductsColor;
import swst.application.models.ActionResponseModel;
import swst.application.models.CreateNewUserModel;
import swst.application.models.LoginModel;
import swst.application.repositories.BrandsRepository;
import swst.application.repositories.ColorsRepository;
import swst.application.repositories.ProductsColorRepository;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicVisitorApi {
	@Autowired
	private final PublicUserController publicUserController;
	@Autowired
	private final ProductsController productsRESTcontroller;
	@Autowired
	private final ColorsRepository colorsRepository;
	@Autowired
	private final ModelController modelController;
	@Autowired
	private final BrandsRepository brandRepository;
	@Autowired
	private final ProductsColorRepository productsColorRepository;

	// [ createNewUser ] Will create new user.
	@PostMapping("/auth/register")
	public ResponseEntity<HttpStatus> createNewUser(@RequestPart CreateNewUserModel newUser) {
		publicUserController.register(newUser);
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/public/auth/register").toString());
		return ResponseEntity.created(uri).body(HttpStatus.CREATED);
	}

	// [ userLogin ] Give the user login and token.
	@PostMapping("/auth/login")
	public ResponseEntity<?> userLogin(@RequestBody LoginModel userLogin, HttpServletResponse response) {
		return ResponseEntity.ok().body(publicUserController.authenUser(userLogin, response));
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

	// [ listProductColors ] List all product color that belongs to specific product
	// ID.
	@GetMapping("/productcolor/{id}")
	public ResponseEntity<List<ProductsColor>> listProductColors(@PathVariable(name = "id") int caseID) {
		return ResponseEntity.ok().body(productsColorRepository.findAllBycaseID(caseID));
	}
}