package swst.application.models;

import lombok.Data;

@Data
public class CreateNewUserModel {
/*username password firstname lastname phone */
	private String userName;
	private String userPassword;
	private String firstName;
	private String lastName;
	private String phone;
	
}
