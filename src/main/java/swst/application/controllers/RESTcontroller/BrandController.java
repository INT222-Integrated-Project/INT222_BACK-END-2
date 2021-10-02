package swst.application.controllers.RESTcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import swst.application.repositories.BrandsRepository;

@Service
public class BrandController {
	@Autowired
	private BrandsRepository brandsRepository;
	
	
}
