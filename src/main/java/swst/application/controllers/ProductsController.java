package swst.application.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import swst.application.authenSecurity.TokenUtills;
import swst.application.entities.Products;
import swst.application.entities.ProductsColor;
import swst.application.entities.Roles;
import swst.application.entities.UsernamesModels;
import swst.application.entities.seperated.ProductColorToProducts;
import swst.application.errorsHandlers.ExceptionFoundation;
import swst.application.errorsHandlers.ExceptionresponsesModel.EXCEPTION_CODES;
import swst.application.models.ActionResponseModel;
import swst.application.repositories.OrderDetailRepository;
import swst.application.repositories.ProductColorToProductsRepository;
import swst.application.repositories.ProductsColorRepository;
import swst.application.repositories.ProductsRepository;
import swst.application.repositories.RolesRepository;
import swst.application.repositories.UsernameRepository;
import swst.application.services.FileStorageService;

@Service
@PropertySource("userdefined.properties")
public class ProductsController {

	@Autowired
	private ProductsRepository productsRepository;
	@Autowired
	private UsernameRepository usernameRepository;
	@Autowired
	private RolesRepository roleRepository;
	@Autowired
	private ProductsColorRepository productsColorRepository;
	@Autowired
	private FileStorageService fileStorageService;
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private ProductColorToProductsRepository productColorToProductsRepository;

	@Value("${application.pagerequest.maxsize.products}")
	private int maxsizeProducts;

	@Value("${application.pagerequest.defaultsize.products}")
	private int defaultSizeProduct;
	
	// [ adminProductLister ]
	public Page<Products> adminProductLister(int page, int size, String searchContent){
		if (page < 0) {
			page = 0;
		}
		if (size < 1 || size > maxsizeProducts) {
			size = defaultSizeProduct;
		}
		Pageable sendPageRequest = PageRequest.of(page, size);
		Page<Products> result;

		result = productsRepository.findBycaseNameContainingIgnoreCase(searchContent, sendPageRequest);
		if (result.getTotalPages() < page + 1) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND, "[ NOT FOUND ] Nothing here. :(");
		}

		return result;
	}

	// [ createNewproduct ]
	public Products createNewproduct(Products incoming, MultipartFile imageFile, HttpServletRequest request) {

		Products newProduct = new Products();

		if (incoming.getCaseName().length() > 24) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SAVE_ILLEGAL_NAME,
					"[ ILLEGAL NAME ] Name cannot be longer than 24 characters.");
		}

		if (incoming.getCaseID() != 0) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SAVE_OBJECT_EXISTED,
					"[ EXISTED ] Better to edit it instead of create a new one.");
		}
		if (productsRepository.existsByCaseNameIgnoreCase(incoming.getCaseName())) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SAVE_NAME_EXISTS, "[ EXISTED ] This name is alrealdy taken");
		}
		newProduct.setProductImage(null);
		newProduct.setCaseName(incoming.getCaseName());
		newProduct.setCasePrice(incoming.getCasePrice());
		newProduct.setModels(incoming.getModels());
		newProduct.setCaseDate(LocalDate.now().toString());
		newProduct.setIsOnStore(true);
		newProduct.setUsernameID(
				usernameRepository.findByUserName(TokenUtills.getUserNameFromToken(request)).getUserNameID());
		if (incoming.getCaseDescription() == "") {
			newProduct.setCaseDescription("A case added by " + newProduct.getUsernameID());
		} else {
			newProduct.setCaseDescription(incoming.getCaseDescription());
		}

		if (imageFile != null) {

			newProduct.setProductImage(fileStorageService.saveImage(imageFile, "products"));
		}

		newProduct = productsRepository.save(newProduct);
		loopSaveProductColor(incoming.getProductColor(), newProduct);

		return newProduct;

	}

	// [ deleteProduct ]
	public ActionResponseModel deleteProductById(int id, HttpServletRequest request) {
		Products userproduct = productsRepository.findById(id).orElseThrow(
				() -> new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND, "[ NOT FOUND ] Nothing here. :("));

		UsernamesModels currentUser = usernameRepository.findByUserName(TokenUtills.getUserNameFromToken(request));

		for (int i = 0; i < userproduct.getProductColor().size(); i++) {
			if (orderDetailRepository
					.existsByproductcolorID(userproduct.getProductColor().get(i).getProductcolorID())) {
				throw new ExceptionFoundation(EXCEPTION_CODES.SAVE_SENSITIVE_INFO_EXISTS,
						"[ NOT ALLOWED ] You cannot delete a product that is already did a transaction.");
			}
		}

		if (!currentUser.getRole().getRoleName().equals("admin")) {
			if (currentUser.getUserNameID() == userproduct.getUsernameID()) {
				return deleteProductMethod(userproduct, currentUser);
			} else {
				throw new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_NOT_ALLOWED,
						"[ Not the owner ] You are not the owner of this products.");
			}
		} else {
			return deleteProductMethod(userproduct, currentUser);
		}

	}

	private ActionResponseModel deleteProductMethod(Products product, UsernamesModels user) {
		int productId = product.getCaseID();
		fileStorageService.deleteImage(product.getProductImage(), "products");
		productsRepository.deleteById(product.getCaseID());
		return new ActionResponseModel("Deleted the product with id : " + productId, true);
	}

	// ###################################################################
	// ###################################################################
	// ###################################################################
	// ###################################################################

	// [ getProductImage ]
	public Resource getProductImage(int productId) {
		return fileStorageService.loadImage(productsRepository.findById(productId).orElseThrow(
				() -> new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND, "[ NOT FOUND ] Nothing here. :("))
				.getProductImage(), "products");
	}

	// [ findProductById ]
	public Products findProductById(int id) {
		return productsRepository.findById(id).orElseThrow(
				() -> new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND, "[ NOT FOUND ] Nothing here. :("));
	}

	// [ listProductOnStore ]
	public Page<Products> listProductOnStore(int page, int size, String searchname) {
		if (page < 0) {
			page = 0;
		}
		if (size < 1 || size > maxsizeProducts) {
			size = defaultSizeProduct;
		}
		Pageable sendPageRequest = PageRequest.of(page, size);
		Page<Products> result;

		result = productsRepository.findBycaseNameContainingAndIsOnStoreTrue(searchname, sendPageRequest);
		if (result.getTotalPages() < page + 1) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND, "[ NOT FOUND ] Nothing here. :(");
		}

		return result;
	}

	// [ listProductColorsByProduct ]
	public List<ProductsColor> listProductColorsByProduct(int caseId) {
		Products searchBy = productsRepository.findById(caseId)
				.orElseThrow(() -> new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND,
						"[ NOT FOUNG ] This product with this ID is not exist."));
		return productsColorRepository.findAllByProduct(searchBy);
	}

	// [ editExistingProduct ]
	public Products editExistingProduct(Products incoming, MultipartFile file, HttpServletRequest request) {
		Products editProduct = productsRepository.findById(incoming.getCaseID())
				.orElseThrow(() -> new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND,
						"[ NOT FOUND ] This product ID never exist."));

		if (!incoming.getCaseName().equals(editProduct.getCaseName())) {
			if (productsRepository.existsByCaseNameIgnoreCase(incoming.getCaseName())) {
				throw new ExceptionFoundation(EXCEPTION_CODES.SAVE_NAME_EXISTS, "[ TAKEN NAME ] This name is taken");
			}
		}

		UsernamesModels owner = usernameRepository.findByUserName(TokenUtills.getUserNameFromToken(request));

		if (editProduct.getUsernameID() != owner.getUserNameID()) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SAVE_NOT_THE_OWNER,
					"[ NOT PERMITTED ] You are not the owner of this product.");
		}

		editProduct.setCaseName((incoming.getCaseName() == "" ? editProduct.getCaseName() : incoming.getCaseName()));
		editProduct.setCaseDescription((incoming.getCaseDescription() == "" ? editProduct.getCaseDescription()
				: incoming.getCaseDescription()));
		editProduct.setModels((incoming.getModels()));
		editProduct.setCasePrice((incoming.getCasePrice() == 0 ? editProduct.getCasePrice() : incoming.getCasePrice()));
		editProduct = productsRepository.save(editProduct);

		if (file != null) {
			fileStorageService.deleteImage(editProduct.getProductImage(), "products");
			editProduct.setProductImage(fileStorageService.saveImage(file, "products"));
			productsRepository.save(editProduct);
		}

		return productsRepository.findById(editProduct.getCaseID())
				.orElseThrow(() -> new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND, "[ NOT EXISTS ] Why?"));
	}

	// [ addOrRemoveStock ]
	public ProductsColor addOrRemoveStock(int quantity, long productId, HttpServletRequest request) {

		ProductsColor currentProduct = productsColorRepository.findById(productId)
				.orElseThrow(() -> new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND,
						"[ NOT FOUND ] THis product ID never exist."));

		String ownerName = usernameRepository
				.findById(productsRepository.findById(currentProduct.getProduct().getCaseID())
						.orElseThrow(() -> new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND,
								"[ NOT FOUND ] THis product ID never exist."))
						.getUsernameID())
				.orElseThrow(() -> new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND,
						"[ NOT FOUND ] THis product ID never exist."))
				.getUserName();
		if (!TokenUtills.getUserNameFromToken(request).equalsIgnoreCase(ownerName)) {
			throw new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_NOT_ALLOWED,
					"[ NOT ALLOWED ] You are not the owner of this product.");
		}

		currentProduct.setQuantity(currentProduct.getQuantity() + quantity);
		productsColorRepository.save(currentProduct);
		return currentProduct;
	}

	// [ toggleProduct ]
	public ActionResponseModel toggleProduct(int productId, HttpServletRequest request) {
		UsernamesModels owner = usernameRepository.findByUserName(TokenUtills.getUserNameFromToken(request));
		Roles userRole = owner.getRole();
		if (userRole == roleRepository.findByroleName("admin") || owner.getUserNameID() == productId) {
			Products setOnStore = productsRepository.findById(productId)
					.orElseThrow(() -> new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND,
							"[ NOT FOUND ] The product with id " + productId + " is not exist."));
			if (!setOnStore.getIsOnStore()) {
				setOnStore.setIsOnStore(true);
				productsRepository.save(setOnStore);
				return new ActionResponseModel("[SET] This product is now on store page!", true);

			} else {
				setOnStore.setIsOnStore(false);
				productsRepository.save(setOnStore);
				return new ActionResponseModel("[SET] This product is now removed!", true);
			}
		} else {
			throw new ExceptionFoundation(EXCEPTION_CODES.SAVE_NOT_THE_OWNER,
					"[ NOT THE OWNER ] You are nor the owner of this product.");
		}

	}

	// [ listProductByUserId ]
	public Page<Products> listProductByUserId(int page, int size, HttpServletRequest request) {
		UsernamesModels owner = usernameRepository.findByUserName(TokenUtills.getUserNameFromToken(request));

		if (page < 0) {
			page = 0;
		}
		if (size < 1 || size > maxsizeProducts) {
			size = defaultSizeProduct;
		}
		Pageable sendPageRequest = PageRequest.of(page, size);
		Page<Products> result;

		result = productsRepository.findByUsernameID(owner.getUserNameID(), sendPageRequest);

		if (result.getTotalPages() < page + 1) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND,
					"[ NOT FOUND ] Seems like you don't have any items here, try to add some?");
		}
		return result;
	}

	// [ loopSaveProductColor ]
	private List<ProductsColor> loopSaveProductColor(List<ProductsColor> incomingNewProduct, Products newProduct) {

		for (int i = 0; i < incomingNewProduct.size(); i++) {
			// log.info("" + newProduct.getCaseID());
			ProductsColor currentColor = new ProductsColor();
			currentColor.setColor(incomingNewProduct.get(i).getColor());
			currentColor.setImageCase(null);
			currentColor.setProduct(newProduct);
			currentColor.setQuantity(incomingNewProduct.get(i).getQuantity());
			// log.info("Save : " + currentColor.getProductcolorID());
			productsColorRepository.save(currentColor);
			// log.info("FINE : " + currentColor.getProductcolorID());
		}
		return null;
	}

	// [ getProductByProductColorid ]
	public List<ProductColorToProducts> getProductByProductColorid() {
		return productColorToProductsRepository.findAll();
	}
}
