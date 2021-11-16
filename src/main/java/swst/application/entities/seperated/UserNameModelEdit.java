package swst.application.entities.seperated;

import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNameModelEdit {
	@Basic(optional = true)
	private String profileImage;
	@Basic(optional = true)
	private String address;
	@Basic(optional = true)
	private String firstName;
	@Basic(optional = true)
	private String lastName;

}
