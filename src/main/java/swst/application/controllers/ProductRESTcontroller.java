package swst.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swst.application.models.Products;
import swst.application.models.ProductsColor;
import swst.application.repositories.ProductsColorRepository;
import swst.application.repositories.ProductsRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/public/products")
public class ProductRESTcontroller {

	@Autowired
	private ProductsRepository productRepository;
	@Autowired
	private ProductsColorRepository productsColorRepository;

	ProductRESTcontroller(ProductsRepository productRepository) {
		this.productRepository = productRepository;
	}

	// LIST ALL PRODUCTS
	@GetMapping("")
	public List<Products> findAllproducts() {
		return productRepository.findAll();
	}
	
	@GetMapping("/test")
	public List<ProductsColor> findAllcolors() {
		return productsColorRepository.findAll();
	}

}