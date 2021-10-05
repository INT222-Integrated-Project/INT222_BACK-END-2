package swst.application.models;

import javax.persistence.Basic;

import lombok.Data;

@Data
public class GetUserModel {
	private String userName;
	private String userRole;
}
