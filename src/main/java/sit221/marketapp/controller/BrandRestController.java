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
import sit221.marketapp.models.Brand;
import sit221.marketapp.repositories.BrandRepository;

@RestController
@RequestMapping("/api/brands")
public class BrandRestController {

	@Autowired
	private BrandRepository brandRepository;

	BrandRestController(BrandRepository brandRepository) {
		this.brandRepository = brandRepository;
	}

	// SELECT FROM BRAND
	@GetMapping("")
	public List<Brand> findAllColor() {
		return brandRepository.findAll();
	}

	// SELECT FROM BRAND WHERE ID=
	@GetMapping("/{id}")
	public ResponseEntity<Brand> findColorById(@PathVariable(value = "id") int searchid)
			throws ResourceNotFoundException {
		Brand search = brandRepository.findById(searchid)
				.orElseThrow(() -> new ResourceNotFoundException("[ Not OK ] : The brand id " + searchid + " is not exist. "));
		return ResponseEntity.ok().body(search);
	}

}
