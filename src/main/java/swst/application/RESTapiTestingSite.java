package swst.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swst.application.models.orders.OrderDetail;
import swst.application.models.orders.OrderStatus;
import swst.application.models.orders.Orders;
import swst.application.models.products.Brands;
import swst.application.models.products.Colors;
import swst.application.models.products.Models;
import swst.application.models.products.ProductModel;
import swst.application.models.products.Products;
import swst.application.models.products.ProductsColor;
import swst.application.models.users.Roles;
import swst.application.models.users.UsernamesModels;
import swst.application.repositories.BrandsRepository;
import swst.application.repositories.ColorsRepository;
import swst.application.repositories.ModelsRepository;
import swst.application.repositories.OrderDetailRepository;
import swst.application.repositories.OrdersRepository;
import swst.application.repositories.ProductModelRepository;
import swst.application.repositories.UsernameRepository;
import swst.application.repositories.ProductsColorRepository;
import swst.application.repositories.ProductsRepository;
import swst.application.repositories.RolesRepository;
import swst.application.repositories.OrderStatusRepository;

@RestController
@RequestMapping("/test")
public class RESTapiTestingSite {
	/*
	 * Repositories
	 */
	// RA_ProductModels
	@Autowired
	private ColorsRepository colorsRepository;
	@Autowired
	private ProductsColorRepository productsColorRepository;
	@Autowired
	private ProductsRepository productsRepository;
	@Autowired
	private ProductModelRepository productModelRepository;
	@Autowired
	private ModelsRepository modelsRepository;
	@Autowired
	private BrandsRepository brandsRepository;
	// RA_UserModels
	@Autowired
	private RolesRepository rolesRepository;
	@Autowired
	private UsernameRepository usernameRepository;
	// RC_OrderModels
	@Autowired
	private OrdersRepository ordersRepository;
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private OrderStatusRepository statusRepository;

	/*
	 * FindAll
	 */

	// RA_Products
	@RequestMapping("/colors")
	public List<Colors> fineAllColor() {
		return colorsRepository.findAll();
	}

	@RequestMapping("/productsColor")
	public List<ProductsColor> fineAllProdColor() {
		return productsColorRepository.findAll();
	}

	@RequestMapping("/products")
	public List<Products> fineAllProduct() {
		return productsRepository.findAll();
	}

	@RequestMapping("/productmodel")
	public List<ProductModel> fineAllProductModel() {
		return productModelRepository.findAll();
	}

	@RequestMapping("/models")
	public List<Models> fineAllmodels() {
		return modelsRepository.findAll();
	}

	@RequestMapping("/brands")
	public List<Brands> findAllBrands() {
		return brandsRepository.findAll();
	}
	
	// RA_UserModels
	@RequestMapping("/roles")
	public List<Roles> fineAllRoles(){
		return rolesRepository.findAll();
	}
	
	@RequestMapping("/username")
	public List<UsernamesModels> finrAllUserName(){
		return usernameRepository.findAll();
	}
	
	// RC_OrderModels
	@RequestMapping("/orders")
	public List<Orders> findAllOrders(){
		return ordersRepository.findAll();
	}
	
	@RequestMapping("/orderdetail")
	public List<OrderDetail> findAllOrderDeatil(){
		return orderDetailRepository.findAll();
	}
	
	@RequestMapping("/status")
	public List<OrderStatus> fineAllStatus(){
		return statusRepository.findAll();
	}
	

}
