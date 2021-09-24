package swst.application.controllers.products;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import swst.application.exceptionhandlers.ExceptionDetails;
import swst.application.exceptionhandlers.ExceptionFoundation;
import swst.application.imageManager.ImageService;
import swst.application.models.products.Products;
import swst.application.models.products.ProductsColor;
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
	public ResponseEntity<Products> findProductsByID(@PathVariable int id) throws Exception {
		Products search = productsRepository.findById(id)
				.orElseThrow(() -> new ExceptionFoundation(ExceptionDetails.EXCEPTION_CODES.SEARCH_NOT_FOUND,
						"[ OBJECT DOES NOT EXIST ] the product id " + id + " is not exist in our database."));
		return ResponseEntity.ok().body(search);
	}
	
	//List by page
	/*@GetMapping("")
	public ResponseEntity<Products> findByDateOfPlaced(int caseID, Pageable pageable){
		return null;
	}*/

	// Image retrieve only
	/*
	@GetMapping(value = "/image/{filename}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	@ResponseBody
	public Resource getImage(@PathVariable String filename) throws IOException {
		return imageService.load(filename);
	}*/

	/*
	 * @GetMapping("/findproductcolor/{productid}") public List<ProductsColor>
	 * findProductColorByID(@PathVariable int productid) throws ExceptionFoundation{
	 * List<ProductsColoro> search = ProductsColorRepository. return null; }
	 */

}
