package swst.application.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import swst.application.authenSecurity.TokenUtills;
import swst.application.controllers.ProductOrderController;
import swst.application.controllers.ProductsController;
import swst.application.entities.Orders;
import swst.application.entities.Products;
import swst.application.entities.ProductsColor;
import swst.application.models.ActionResponseModel;

@RequestMapping("/staff")
@RestController
@RequiredArgsConstructor
@Slf4j
public class StaffApi {

	@Autowired
	private final ProductsController productsController;
	@Autowired
	private final ProductOrderController productOrderController;

	// [ createNewproduct ]
	@PostMapping(value = "/products", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE})
	public ResponseEntity<Products> createNewproduct(@RequestPart Products newProducts,
			@RequestPart(value = "imageFile") MultipartFile imageFile, HttpServletRequest request) {
		productsController.createNewproduct(newProducts, imageFile, request);
		URI uri = URI
				.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/staff/product/add").toString());
		return ResponseEntity.created(uri).body(newProducts);
	}
	
	// [ deleteProduct ]
	@DeleteMapping("/products")
	public ResponseEntity<ActionResponseModel> deleteProduct(@RequestParam(name = "productId") int id, HttpServletRequest request){
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/staff/product/"+id).toString());
		return ResponseEntity.created(uri).body(productsController.deleteProductById(id, request));
	}

	/*
	 * #############################################################################
	 * ##########################################################################
	 */

	// [ toggleOnStore ]
	@PutMapping("/product/onstore")
	public ResponseEntity<ActionResponseModel> toggleOnStore(@RequestParam int productId, HttpServletRequest request) {
		ActionResponseModel action = productsController.toggleProduct(productId, request);
		URI uri = URI
				.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/staff/product/onstore").toString());
		return ResponseEntity.created(uri).body(action);
	}

	// [ changeStock ]
	@PutMapping("/addStock")
	public ResponseEntity<ProductsColor> changeStock(@RequestParam long productColorId, @RequestParam int quantity,
			HttpServletRequest request) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/staff/addStock/" + productColorId).toString());
		return ResponseEntity.created(uri).body(productsController.addOrRemoveStock(quantity, productColorId, request));
	}

	// [ listProductByMyUsername ]
	@GetMapping("/myshop")
	public ResponseEntity<Page<Products>> listProductByMyUsername(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "15") int size, HttpServletRequest request) {
		return ResponseEntity.ok().body(productsController.listProductByUserId(page, size, request));
	}

	// [ editExistingProduct ]
	@PutMapping(value = "/product", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.IMAGE_JPEG_VALUE,
			MediaType.IMAGE_JPEG_VALUE })
	public ResponseEntity<ActionResponseModel> editExistingProduct(@RequestPart Products incomingproduct,
			@RequestParam(required = false) MultipartFile imageFile, HttpServletRequest request) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/staff/product").toString());
		return ResponseEntity.created(uri)
				.body(productsController.editExistingProduct(incomingproduct, imageFile, request));
	}

	// [ changeOrderStatus ]
	@PutMapping("/changestatus/{orderId}/{statusID}")
	public ResponseEntity<ActionResponseModel> changeOrderStatus(@PathVariable long orderId,
			@PathVariable int statusID) {
		URI uri = URI.create(
				ServletUriComponentsBuilder.fromCurrentContextPath().path("/staff/changestatus/" + orderId).toString());
		return ResponseEntity.created(uri).body(productOrderController.changeOrderStatusByStaff(orderId, statusID));
	}

	// !!! [ setPrice ]
	@PutMapping("/setprice/{productID}")
	public ResponseEntity<ActionResponseModel> setPrice(@PathVariable int productID) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/staff/setprice").toString());
		return ResponseEntity.created(uri).body(null);
	}

}
