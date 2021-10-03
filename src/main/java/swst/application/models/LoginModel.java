package swst.application.models;

import javax.persistence.Basic;

import lombok.Data;

@Data
public class LoginModel {

	@Basic(optional = false)
	private String userName;
	@Basic(optional = false)
	private String userPassword;
}
