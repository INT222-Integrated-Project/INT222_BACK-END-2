package swst.application.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import swst.application.entities.UsernamesModels;
import swst.application.repositories.UsernameRepository;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminApi {

	@Autowired
	private final UsernameRepository usernameRepository;

	@GetMapping("/us")
	public ResponseEntity<List<UsernamesModels>> listAllUsers() {
		return ResponseEntity.ok().body(usernameRepository.findAll());
	}
}
