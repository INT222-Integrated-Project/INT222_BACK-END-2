package swst.application.controllers.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swst.application.exceptionhandlers.ExceptionDetails;
import swst.application.exceptionhandlers.ExceptionFoundation;
import swst.application.model_products.Products;
import swst.application.model_products.ProductsColor;
import swst.application.repositories.ProductsColorRepository;
import swst.application.repositories.ProductsRepository;

@RestController
@RequestMapping("/public/products")
public class ProductsRESTcontroller {

	@Autowired
	private ProductsRepository productsRepository;
	@Autowired
	private ProductsColorRepository productsColorRepository;

	// Search by ID.
	@GetMapping("/{id}")
	public ResponseEntity<Products> findProductsByID(@PathVariable int id) throws ExceptionFoundation {
		Products search = productsRepository.findById(id)
				.orElseThrow(() -> new ExceptionFoundation(ExceptionDetails.EXCEPTION_CODES.SEARCH_NOT_FOUND,
						"[ OBJECT DOES NOT EXIST ] the product id " + id + " is not exist in our database."));
		return ResponseEntity.ok().body(search);
	}
	/*
	@GetMapping("/findproductcolor/{productid}")
	public List<ProductsColor> findProductColorByID(@PathVariable int productid) throws ExceptionFoundation{
		List<ProductsColoro> search = ProductsColorRepository.
		return null;
	}*/
	
}
