package swst.application.responsemodels;

import lombok.Data;
import swst.application.models.users.UsernamesModels;

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
