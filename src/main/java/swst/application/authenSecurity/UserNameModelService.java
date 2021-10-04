package swst.application.authenSecurity;

import swst.application.entities.UsernamesModels;

public interface UserNameModelService {

	UsernamesModels saveNewUser(UsernamesModels newUser);

	void assignRole(String username, String roleName);

	UsernamesModels getUserByName(String username);

}
