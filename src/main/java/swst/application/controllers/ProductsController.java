package swst.application.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.management.relation.RoleResult;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.groovy.syntax.TokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;
import swst.application.authenSecurity.TokenUtills;
import swst.application.entities.Products;
import swst.application.entities.ProductsColor;
import swst.application.entities.Roles;
import swst.application.entities.UsernamesModels;
import swst.application.errorsHandlers.ExceptionresponsesModel;
import swst.application.errorsHandlers.ExceptionFoundation;
import swst.application.errorsHandlers.ExceptionresponsesModel.EXCEPTION_CODES;
import swst.application.models.ActionResponseModel;
import swst.application.repositories.ProductsColorRepository;
import swst.application.repositories.ProductsRepository;
import swst.application.repositories.RolesRepository;
import swst.application.repositories.UsernameRepository;

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

	@Value("${application.pagerequest.maxsize.products}")
	private int maxsizeProducts;

	@Value("${application.pagerequest.defaultsize.products}")
	private int defaultSizeProduct;

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

		if (searchname == "" || searchname == null) {
			result = productsRepository.findByIsOnStore(true, sendPageRequest);
		} else {
			result = productsRepository.findBycaseNameContainingAndIsOnStoreTrue(searchname, sendPageRequest);
			if (result.getTotalPages() < page + 1) {
				throw new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND, "[ NOT FOUND ] Nothing here. :(");
			}
		}

		if (result.getTotalPages() < page + 1) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND, "[ NOT FOUND ] Nothing here. :(");
		}
		return result;
	}

	// [ createNewproductTextOnly ]
	public Products createNewproductTextOnly(String userName, Products incoming) {
		if (incoming.getCaseID() == 0) {
			incoming.setUsernameID(usernameRepository.findByUserName(userName).getUserNameID());
			if (incoming.getCaseDescription() == "") {
				incoming.setCaseDescription("A case added by " + userName);
			}
			incoming.setIsOnStore(true);
			productsRepository.save(incoming);
			return incoming;
		} else {
			throw new ExceptionFoundation(EXCEPTION_CODES.SAVE_OBJECT_EXISTED,
					"[ EXISTED ] Better to edit it instead of create a new one.");
		}
	}

	// [ editExistingProduct ]
	public ActionResponseModel editExistingProduct(HttpServletRequest request, Products incoming) {
		String actionTitle = "Add product";
		Products editPtoduct = new Products();
		try {
			editPtoduct.setCaseName(incoming.getCaseName());
			editPtoduct.setCaseDescription(incoming.getCaseDescription());
			editPtoduct.setCasePrice(incoming.getCasePrice());
			editPtoduct.setCaseDate(incoming.getCaseDate());
			editPtoduct.setProductColor(incoming.getProductColor());
			editPtoduct.setModels(incoming.getModels());
			editPtoduct.setProductImage(incoming.getProductImage());
		} catch (Exception e) {
			return new ActionResponseModel(actionTitle, false);
		}
		productsRepository.save(editPtoduct);
		return new ActionResponseModel(actionTitle, true);
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

	// [ listProductColors ]
	public List<ProductsColor> listProductColors(int productId) {
		return productsColorRepository.findAllBycaseID(productId);
	}

}
