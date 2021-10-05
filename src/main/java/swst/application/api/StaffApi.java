package swst.application.api;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import swst.application.authenSecurity.TokenUtills;
import swst.application.entities.Products;
import swst.application.repositories.ProductsRepository;
import swst.application.repositories.UsernameRepository;

@RequestMapping("/staff")
@RestController
@RequiredArgsConstructor
public class StaffApi {

	@Autowired
	private final ProductsRepository productsRepository;
	@Autowired
	private final UsernameRepository usernameRepository;

	// [ createNewproductTextOnly ]
	/*@PostMapping("/createProduct")
	public ResponseEntity<?> createNewproductTextOnly(HttpServletRequest request, Products incomingNewProduct) {
		Products newproduct = new Products();

		int userId = usernameRepository.findByUserName(TokenUtills.getUserNameFromToken(request)).getUserNameID();
		//newproduct.
		
		productsRepository.save(newproduct);
		URI uri = URI
				.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/staff/createProduct").toString());
		return ResponseEntity.created(uri).body(null);
	}*/

}
