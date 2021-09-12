package swst.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swst.application.models.Products;
import swst.application.repositories.ProductsRepository;

@RestController
@RequestMapping("/public/products")
public class ProductRESTcontroller {
	
	@Autowired
	private ProductsRepository productRepository;
	
	ProductRESTcontroller(ProductsRepository productRepository){
		this.productRepository = productRepository;
	}
	
	//LIST ALL PRODUCTS
	@GetMapping("")
	public List<Products> findAllproducts(){
		return productRepository.findAll();
	}
	
	
	
}