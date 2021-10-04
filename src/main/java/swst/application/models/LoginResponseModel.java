package swst.application.models;

import lombok.Data;

@Data
public class LoginResponseModel {
	private String usernames;
	private String acceessToken;
	private String refreshToken;

	public LoginResponseModel(String usernames, String acceessToken, String refreshToken) {
		this.usernames = usernames;
		this.acceessToken = acceessToken;
		this.refreshToken = refreshToken;
	}
}
