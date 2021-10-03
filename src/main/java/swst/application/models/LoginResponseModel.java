package swst.application.models;

import lombok.Data;
import swst.application.entities.UsernamesModels;

@Data
public class LoginResponseModel {
	private UsernamesModels usernamesModels;
	private boolean authenSuccess;
	private String token;

	public LoginResponseModel(UsernamesModels usernamesModels, boolean authenSuccess, String token) {
		this.usernamesModels = usernamesModels;
		this.authenSuccess = authenSuccess;
		this.token = token;
	}
}
