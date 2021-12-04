package swst.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import swst.application.entities.Models;
import swst.application.errorsHandlers.ExceptionFoundation;
import swst.application.errorsHandlers.ExceptionresponsesModel.EXCEPTION_CODES;
import swst.application.repositories.ModelsRepository;

@Service
@PropertySource("userdefined.properties")
public class ModelController {

	@Autowired
	private ModelsRepository modelsRepository;

	@Value("${application.pagerequest.maxsize.models}")
	private int maxsizeModel;

	@Value("${application.pagerequest.defaultsize.models}")
	private int defaultSizeModel;

	// [ listModelsByPage ]
	public Page<Models> listModelsByPage(int page, int size, String searchname) {
		if (page < 0) {
			page = 0;
		}
		if (size < 1 || size > maxsizeModel) {
			size = defaultSizeModel;
		}
		Pageable sendPageRequest = PageRequest.of(page, size);
		Page<Models> result;
		if (searchname == "") {
			result = modelsRepository.findAll(sendPageRequest);
		} else {
			result = modelsRepository.findByModelNameContainingIgnorecase(searchname, sendPageRequest);
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
