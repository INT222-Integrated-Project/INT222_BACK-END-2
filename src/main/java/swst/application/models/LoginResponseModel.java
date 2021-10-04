package swst.application.models;

import lombok.Data;

@Data
public class LoginResponseModel {
	private String usernames;
	private String acceessToken;

	public LoginResponseModel(String usernames, String acceessToken) {
		this.usernames = usernames;
		this.acceessToken = acceessToken;
	}
}
