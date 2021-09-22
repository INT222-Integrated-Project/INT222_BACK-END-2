package swst.application.controllers.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swst.application.models.products.Colors;
import swst.application.repositories.ColorsRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/public/colors")
public class ColorsRESTContoller {

	@Autowired
	private ColorsRepository colorsRepository;

	@GetMapping("")
	public List<Colors> fineAllColor() {
		return colorsRepository.findAll();
	}
}
