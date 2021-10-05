package swst.application.controllers;

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

}
