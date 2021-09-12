package swst.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swst.application.models.Colors;
import swst.application.repositories.ColorsRepository;

@RestController
@RequestMapping("/public/colors")
public class ColorsRESTcontroller {
	@Autowired
	private ColorsRepository colorsRepository;

	public ColorsRESTcontroller(ColorsRepository colorsRepository) {
		this.colorsRepository = colorsRepository;
	}

	// SELECT color
	@GetMapping("")
	private List<Colors> findAllColor() {
		return colorsRepository.findAll();
	}

}
