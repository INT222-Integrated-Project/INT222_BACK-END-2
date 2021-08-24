package sit221.marketapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import sit221.marketapp.exceptions.ResourceNotFoundException;
import sit221.marketapp.models.Color;
import sit221.marketapp.repositories.ColorRepository;

@RestController
@RequestMapping("/api/colors")
public class ColorRestController {

	@Autowired
	private ColorRepository colorRepository;

	ColorRestController(ColorRepository colorRepository) {
		this.colorRepository = colorRepository;
	}

	// SELECT color
	@GetMapping("")
	private List<Color> findAllColor() {
		return colorRepository.findAll();
	}

	// SELECT color by ID
	@GetMapping("/{id}")
	private ResponseEntity<Color> findProductById(@PathVariable(value = "id") int searchid)
			throws ResourceNotFoundException {
		Color search = colorRepository.findById(searchid)
				.orElseThrow(() -> new ResourceNotFoundException("[ Not OK ] : The product id " + searchid + " is not exist. "));
		return ResponseEntity.ok().body(search);
	}

}
