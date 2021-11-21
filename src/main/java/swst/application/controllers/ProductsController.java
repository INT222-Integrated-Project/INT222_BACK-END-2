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

import lombok.extern.slf4j.Slf4j;
import swst.application.authenSecurity.TokenUtills;
import swst.application.entities.ProductModel;
import swst.application.entities.Products;
import swst.application.entities.ProductsColor;
import swst.application.entities.Roles;
import swst.application.entities.UsernamesModels;
import swst.application.errorsHandlers.ExceptionFoundation;
import swst.application.errorsHandlers.ExceptionresponsesModel.EXCEPTION_CODES;
import swst.application.models.ActionResponseModel;
import swst.application.repositories.ProductsColorRepository;
import swst.application.repositories.ProductsRepository;
import swst.application.repositories.RolesRepository;
import swst.application.repositories.UsernameRepository;
import swst.application.services.FileStorageService;

@Service
@PropertySource("userdefined.properties")
@Slf4j
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

	@Value("${application.pagerequest.maxsize.products}")
	private int maxsizeProducts;

	@Value("${application.pagerequest.defaultsize.products}")
	private int defaultSizeProduct;

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

	// [ createNewproduct ]
	public Products createNewproduct(Products incoming, MultipartFile imageFile, HttpServletRequest request) {

		Products newProduct = new Products();

		if (incoming.getCaseID() != 0) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SAVE_OBJECT_EXISTED,
					"[ EXISTED ] Better to edit it instead of create a new one.");
		}
		newProduct.setProductImage("404notfound.png");
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
			newProduct.setProductImage(fileStorageService.saveProductImage(imageFile, "products"));
		}

		newProduct = productsRepository.save(newProduct);
		loopSaveProductColor(incoming.getProductColor(), newProduct);

		return newProduct;

	}

	// [ listProductColorsByProduct ]
	public List<ProductsColor> listProductColorsByProduct(int caseId) {
		Products searchBy = productsRepository.findById(caseId)
				.orElseThrow(() -> new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND,
						"[ NOT FOUNG ] This product with this ID is not exist."));
		return productsColorRepository.findAllByProduct(searchBy);
	}

	// [ editExistingProduct ]
	public ActionResponseModel editExistingProduct(Products incoming, MultipartFile file, int productId,
			HttpServletRequest request) {
		// log.info("Entered OK : ID :" + productId);
		Products editProduct = productsRepository.findById(productId)
				.orElseThrow(() -> new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND,
						"[ NOT FOUND ] This product ID never exist."));

		UsernamesModels owner = usernameRepository.findByUserName(TokenUtills.getUserNameFromToken(request));

		// log.info("Found OK :" + editProduct.getUsernameID() + " : " +
		// owner.getUserNameID());
		if (editProduct.getUsernameID() != owner.getUserNameID()) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SAVE_NOT_THE_OWNER,
					"[ NOT PERMITTED ] You are not the owner of this product.");
		}
		// log.info("Owner OK");
		editProduct.setCaseName((incoming.getCaseName() == "" ? editProduct.getCaseName() : incoming.getCaseName()));
		editProduct.setCaseDescription((incoming.getCaseDescription() == "" ? editProduct.getCaseDescription()
				: incoming.getCaseDescription()));
		editProduct.setModels((incoming.getModels() == null ? editProduct.getModels() : incoming.getModels()));
		editProduct.setCasePrice((incoming.getCasePrice() == 0 ? editProduct.getCasePrice() : incoming.getCasePrice()));
		// log.info("assign OK");
		productsRepository.save(editProduct);
		// log.info("save OK");
		if (file != null) {
			fileStorageService.deleteImage(editProduct.getProductImage(), "products");
			editProduct.setProductImage(fileStorageService.saveProductImage(file, "products"));
			productsRepository.save(editProduct);
		}

		return new ActionResponseModel("[ SAVED ] Product id " + productId + " is saved.", true);
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

}
