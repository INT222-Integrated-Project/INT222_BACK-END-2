package swst.application.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import swst.application.entities.Roles;

@Data
@AllArgsConstructor
public class LoginResponseModel {
	private String usernames;
	private String acceessToken;
	private Roles role;

}
