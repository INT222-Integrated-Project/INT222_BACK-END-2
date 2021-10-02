package swst.application.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import swst.application.controllers.RESTcontroller.ProductsController;
import swst.application.models.products.Colors;
import swst.application.models.products.Models;
import swst.application.models.products.Products;
import swst.application.repositories.ColorsRepository;
import swst.application.repositories.ModelsRepository;
import swst.application.repositories.ProductModelRepository;

@RestController
@RequestMapping("/public")
public class PublicVisitorProductAPI {

	@Autowired
	private ProductsController productsRESTcontroller;
	@Autowired
	private ColorsRepository colorsRepository;
	@Autowired
	private ModelsRepository modelRepository;

	// [ listProductWithPage ] Will list product with page, optional with name.
	@GetMapping("/products")
	public Page<Products> listProductWithPage(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "9") int size,
			@RequestParam(defaultValue = "") String searchname) {
		return productsRESTcontroller.listProductOnStore(page, size, searchname);
	}

	// [ listAllColors ] List all colors available in the repository.
	@GetMapping("/colors")
	public List<Colors> listAllColors() {
		return colorsRepository.findAll();
	}

	// [ listModelWithpage ] List all models in the database with page.
	@GetMapping("/models")
	public List<Models> listModelWithpage(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "30") int size) {
		return modelRepository.findAll();
	}
}
