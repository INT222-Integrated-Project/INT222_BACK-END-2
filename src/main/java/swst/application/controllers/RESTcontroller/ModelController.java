package swst.application.controllers.RESTcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import swst.application.models.products.Models;
import swst.application.repositories.ModelsRepository;

@Service
public class ModelController {

	@Autowired
	private ModelsRepository modelsRepository;
	
	/*public Page<Models>listModelsByPage(int page,int size){
		
	}*/
}
