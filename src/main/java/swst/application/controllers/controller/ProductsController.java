package swst.application.controllers.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import swst.application.entities.Products;
import swst.application.errorsHandlers.ExceptionresponsesModel;
import swst.application.errorsHandlers.ExceptionFoundation;
import swst.application.errorsHandlers.ExceptionresponsesModel.EXCEPTION_CODES;
import swst.application.repositories.ProductsColorRepository;
import swst.application.repositories.ProductsRepository;

@Service
@PropertySource("userdefined.properties")
public class ProductsController {

	@Autowired
	private ProductsRepository productsRepository;

	@Value("${application.pagerequest.maxsize.products}")
	private int maxsizeProducts;

	@Value("${application.pagerequest.defaultsize.products}")
	private int defaultSizeProduct;

	// [ listProductOnStore ]
	public Page<Products> listProductOnStore(int page, int size, String searchname) {
		if (page < 0) {
			page = 0;
		}
		if (size < 0 || size > maxsizeProducts) {
			size = defaultSizeProduct;
		}
		Pageable sendPageRequest = PageRequest.of(page, size);
		Page<Products> result;
		if (searchname == "") {
			result = productsRepository.findByIsOnStore(0, sendPageRequest);
		} else {
			result = productsRepository.findBycaseNameContaining(searchname, sendPageRequest);
			if (result.getTotalPages() < page + 1) {
				throw new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND, "[ NOT FOUND ] Nothing here. :(");
			}
		}

		if (result.getTotalPages() < page + 1) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND, "[ NOT FOUND ] Nothing here. :(");
		}
		return result;
	}

	/*
	 * public ResponseEntity<Map<ject>> findByOnStore(@RequestParam(defaultValue =
	 * "0") iString, Obnt page,
	 * 
	 * @RequestParam(defaultValue = "9") int size) { List<Products> productList =
	 * new ArrayList<Products>(); Pageable paging = PageRequest.of(page, size);
	 * 
	 * Page<Products> pageOfProducts = productsRepository.findByIsOnStore(0,
	 * paging); productList = pageOfProducts.getContent();
	 * 
	 * if (pageOfProducts.getTotalPages() <= page) { int exceeded = page + 1; throw
	 * new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NO_PAGE_HERE,
	 * "[NO PAGE HERE] There are " + pageOfProducts.getTotalPages() +
	 * " pages available but you went to " + exceeded + "!"); }
	 * 
	 * Map<String, Object> response = new HashMap<>(); response.put("products",
	 * productList); response.put("currentPage", pageOfProducts.getNumber());
	 * response.put("totalItems", pageOfProducts.getTotalElements());
	 * response.put("totalPages", pageOfProducts.getTotalPages());
	 * 
	 * return new ResponseEntity<>(response, HttpStatus.OK); }
	 */
	/*
	 * public ResponseEntity<Products> findProductsByID(@PathVariable int id) throws
	 * Exception { Products search = productsRepository.findById(id) .orElseThrow(()
	 * -> new ExceptionFoundation(ExceptionDetails.EXCEPTION_CODES.SEARCH_NOT_FOUND,
	 * "[ OBJECT DOES NOT EXIST ] the product id " + id +
	 * " is not exist in our database.")); return ResponseEntity.ok().body(search);
	 * }
	 */
	/*
	 * public ResponseEntity<Map<String, Object>>
	 * findByName(@RequestParam(defaultValue = "0") int page,
	 * 
	 * @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "")
	 * String searchCaseName) { List<Products> productList = new
	 * ArrayList<Products>(); Pageable paging = PageRequest.of(page, size);
	 * 
	 * Page<Products> pageOfProducts =
	 * productsRepository.findBycaseNameContaining(searchCaseName, paging);
	 * productList = pageOfProducts.getContent();
	 * 
	 * if (pageOfProducts.getTotalPages() <= page) { int exceeded = page + 1; throw
	 * new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NO_PAGE_HERE,
	 * "[NO PAGE HERE] There are " + pageOfProducts.getTotalPages() +
	 * " pages available but you went to " + exceeded + "!"); }
	 * 
	 * Map<String, Object> response = new HashMap<>(); response.put("products",
	 * productList); response.put("currentPage", pageOfProducts.getNumber());
	 * response.put("totalItems", pageOfProducts.getTotalElements());
	 * response.put("totalPages", pageOfProducts.getTotalPages());
	 * 
	 * return new ResponseEntity<>(response, HttpStatus.OK); }
	 */
	/*
	 * @GetMapping("/test") public Page<Products> testThis(Pageable pageable) {
	 * return productsRepository.findByIsOnStore(1, pageable); }
	 */

	// List by page
	/*
	 * @GetMapping("") public ResponseEntity<Products> findByDateOfPlaced(int
	 * caseID, Pageable pageable){ return null; }
	 */

	// Image retrieve only
	/*
	 * @GetMapping(value = "/image/{filename}", produces = {
	 * MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	 * 
	 * @ResponseBody public Resource getImage(@PathVariable String filename) throws
	 * IOException { return imageService.load(filename); }
	 */

	/*
	 * @GetMapping("/findproductcolor/{productid}") public List<ProductsColor>
	 * findProductColorByID(@PathVariable int productid) throws ExceptionFoundation{
	 * List<ProductsColoro> search = ProductsColorRepository. return null; }
	 */

}
