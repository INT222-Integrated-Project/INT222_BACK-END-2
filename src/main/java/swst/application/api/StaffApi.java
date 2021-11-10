package swst.application.api;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import swst.application.authenSecurity.TokenUtills;
import swst.application.controllers.ProductsController;
import swst.application.entities.Products;
import swst.application.models.ActionResponseModel;
import swst.application.repositories.ProductsRepository;
import swst.application.repositories.UsernameRepository;

@RequestMapping("/staff")
@RestController
@RequiredArgsConstructor
public class StaffApi {

	@Autowired
	private final ProductsRepository productsRepository;
	@Autowired
	private final UsernameRepository usernameRepository;
	@Autowired
	private final ProductsController productsController;

	// [ createNewproduct ]
	@PostMapping(value = "/products/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Products> createNewproduct(@RequestBody Products products, HttpServletRequest request) {
		productsController.createNewproductTextOnly(TokenUtills.getUserNameFromToken(request), products);
		URI uri = URI
				.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/staff/product/add").toString());
		return ResponseEntity.created(uri).body(products);
	}

	// [ editExistingProduct ]
	@PutMapping(value = "/product/edit", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> editExistingProduct(@RequestBody Products incomingproduct, HttpServletRequest request) {

		URI uri = URI
				.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/staff/product/add").toString());
		return ResponseEntity.created(uri).body(incomingproduct);
	}

	// [ toggleOnStore ]
	@PostMapping("/product/onstore/{id}")
	public ResponseEntity<ActionResponseModel> toggleOnStore(@PathVariable(name = "id") int productId,
			HttpServletRequest request) {
		ActionResponseModel action = productsController.toggleProduct(productId, request);
		return ResponseEntity.ok().body(action);
	}

	/*
	 * @PostMapping("/createProduct") public ResponseEntity<?>
	 * createNewproductTextOnly(HttpServletRequest request, Products
	 * incomingNewProduct) { Products newproduct = new Products();
	 * 
	 * int userId =
	 * usernameRepository.findByUserName(TokenUtills.getUserNameFromToken(request)).
	 * getUserNameID(); //newproduct.
	 * 
	 * productsRepository.save(newproduct); URI uri = URI
	 * .create(ServletUriComponentsBuilder.fromCurrentContextPath().path(
	 * "/staff/createProduct").toString()); return
	 * ResponseEntity.created(uri).body(null); }
	 */

}
