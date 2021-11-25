package swst.application;

import java.io.InputStream;
import java.net.URI;
import java.sql.Blob;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import swst.application.controllers.UserController;
import swst.application.entities.Brands;
import swst.application.entities.Colors;
import swst.application.entities.Models;
import swst.application.entities.OrderDetail;
import swst.application.entities.OrderStatus;
import swst.application.entities.Orders;
import swst.application.entities.ProductModel;
import swst.application.entities.Products;
import swst.application.entities.ProductsColor;
import swst.application.entities.Roles;
import swst.application.entities.UsernamesModels;
import swst.application.errorsHandlers.ExceptionFoundation;
import swst.application.errorsHandlers.ExceptionresponsesModel.EXCEPTION_CODES;
import swst.application.models.ActionResponseModel;
import swst.application.repositories.BrandsRepository;
import swst.application.repositories.ColorsRepository;
import swst.application.repositories.ModelsRepository;
import swst.application.repositories.OrderDetailRepository;
import swst.application.repositories.OrdersRepository;
import swst.application.repositories.ProductModelRepository;
import swst.application.repositories.UsernameRepository;
import swst.application.services.FileStorageService;
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
	@Autowired
	private UserController ussrcontroller;

	@Autowired
	private FileStorageService fileStorageService;
	
	@PostMapping("/put/test")
	public UsernamesModels putUsernameull(@RequestPart UsernamesModels models) {
		return models;
	}

	@PostMapping("/pc")
	public ResponseEntity<ProductsColor> productPost() {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/test/pc").toString());
		ProductsColor newColor = new ProductsColor();
		newColor.setProduct(productsRepository.findById(1).get());
		newColor.setColor(colorsRepository.findById(1).get());
		
		newColor.setQuantity(998);
		newColor.setImageCase(null);

		newColor = productsColorRepository.save(newColor);
		return ResponseEntity.created(uri).body(newColor);
	}

	// @GetMapping("/image/{imagename}")

	@PostMapping("/upload")
	public ActionResponseModel addimage(@RequestPart("image") MultipartFile file) {
		if (file == null) {
			throw new ExceptionFoundation(EXCEPTION_CODES.DEAD, "LOL!");
		}

		fileStorageService.saveProductImage(file, "products");
		return new ActionResponseModel("Uploaded", true);
	}

	@PostMapping("/post/orderdetail/{orderId}")
	public ActionResponseModel addOrderDetail(@RequestPart OrderDetail orderDetail, @PathVariable long orderId) {
		Optional<Orders> serchOrder = ordersRepository.findById(orderId);
		orderDetail.setOrders(serchOrder.get());

		orderDetailRepository.save(orderDetail);
		return new ActionResponseModel("Post orderdetail", true);
	}

	@PostMapping("/postpro")
	public ActionResponseModel postPro(@RequestBody Products product) {
		productsRepository.save(product);
		return new ActionResponseModel("Post product", true);
	}

	@GetMapping("/test")
	public Page<Products> finePro() {
		Pageable sendPageRequest = PageRequest.of(0, 9);
		return productsRepository.findByIsOnStore(true, sendPageRequest);
	}

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
	public List<Roles> fineAllRoles() {
		return rolesRepository.findAll();
	}

	@RequestMapping("/username")
	public Page<UsernamesModels> finrAllUserName(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "45") int size, @RequestParam(defaultValue = "") String searchname) {
		return ussrcontroller.listUserByPage(page, size, searchname);
	}

	// RC_OrderModels
	@RequestMapping("/orders")
	public List<Orders> findAllOrders() {
		return ordersRepository.findAll();
	}

	@RequestMapping("/orderdetail")
	public List<OrderDetail> findAllOrderDeatil() {
		return orderDetailRepository.findAll();
	}

	@RequestMapping("/status")
	public List<OrderStatus> fineAllStatus() {
		return statusRepository.findAll();
	}

}
