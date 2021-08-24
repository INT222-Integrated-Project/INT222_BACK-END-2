package sit221.marketapp.controller;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sit221.marketapp.exceptions.ResourceNotFoundException;
import sit221.marketapp.imageManager.ImageServices;
import sit221.marketapp.models.Products;
import sit221.marketapp.repositories.ProductsRepository;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

	@Autowired
	private ProductsRepository productRepository;
	@Autowired
	private ImageServices imageService;

	ProductRestController(ProductsRepository productRepository) {
		this.productRepository = productRepository;
	}

	// Select Product WHERE id = ?
	@GetMapping("/{id}")
	public ResponseEntity<Products> findProductsById(@PathVariable int id) throws ResourceNotFoundException {
		Products search = productRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("[ Not OK ] : The product id " + id + " is not exist. "));
		return ResponseEntity.ok().body(search);
	}

	// Select ALL Product
	@GetMapping("")
	public List<Products> findAllProducts() {
		return productRepository.findAll();
	}

	// Image retrieve only
	@GetMapping(value = "/image/{filename}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	@ResponseBody
	public Resource getImage(@PathVariable String filename) throws IOException {
		return imageService.load(filename);
	}

	// Upload both image and JSON. An image file name will be changed after passed
	// this method.
	// Required Both.
	@PostMapping(value =  "", produces = MediaType.APPLICATION_JSON_VALUE)
	public void uploadImage(@RequestParam("imageFile") MultipartFile imageFile,
			@RequestPart("body") Products newProduct) {
		try {
			// Left 27 Right 122 TargetLength 10
			int targetLength = 10;
			int lowerstAlp = 65;
			int hightAlp = 90;

			Random randomString = new Random();
			StringBuilder buffer = new StringBuilder(targetLength);

			for (int i = 0; i < targetLength; i++) {
				int randomLimitedInt = lowerstAlp + (int) (randomString.nextFloat() * (hightAlp - lowerstAlp + 1));
				buffer.append((char) randomLimitedInt);
			}
			newProduct.setCaseImage("PiKa-" + buffer.toString() + "-" + imageFile.getOriginalFilename());
			productRepository.save(newProduct);
			imageService.save(imageFile, newProduct.getCaseImage());
			System.out.println("[ OK ] File " + imageFile.getOriginalFilename() + " is successory uploaded. ");

		} catch (Exception e) {
			throw new RuntimeException(
					"[ FAILED ] Things went wrond at method\"uploadImage\". Can't save data or image. ");
		}

	}

	// Update Product and image only
	@PutMapping("/{id}")
	public ResponseEntity<Products> editSelling(@PathVariable(value = "id") int editingId,
			@RequestParam(required = false, value = "image") MultipartFile imageFile,
			@RequestPart("edit") Products newDetails) throws ResourceNotFoundException {
		Products editting = productRepository.findById(editingId).orElseThrow(
				() -> new ResourceNotFoundException("[ Not OK ] : The product id " + editingId + " is not exist. "));
		if (editting != null) {
			editting.setCaseDescription(newDetails.getCaseDescription());
			editting.setCaseName(newDetails.getCaseName());
			editting.setCasePrice(newDetails.getCasePrice());
			editting.setCaseDate(newDetails.getCaseDate());
			editting.setBrand(newDetails.getBrand());
			editting.setColor(newDetails.getColor());

			if (imageFile != null) {
				imageService.save(imageFile, editting.getCaseImage());
			}

			Products updatedProduct = productRepository.save(editting);
			return ResponseEntity.ok(updatedProduct);
		}
		return null;
	}

	// Delete From
	@DeleteMapping("/{id}")
	public String deleteSelling(@PathVariable(value = "id") int removeId) throws ResourceNotFoundException {
		Products removing = productRepository.findById(removeId).orElseThrow(
				() -> new ResourceNotFoundException("[ Not OK ] : The product id " + removeId + " is not exist. "));
		String imageToRemove = removing.getCaseImage();
		imageService.delete(imageToRemove);

		productRepository.deleteById(removing.getCaseId());
		return "OK";
	}

}
