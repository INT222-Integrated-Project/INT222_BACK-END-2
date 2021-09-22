package swst.application.models.users;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "username")
public class UsernamesModels {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userNameID;

	private String userName;
	@JsonIgnore
	private String userPassword;

	private int roleID;
	
	private String firstName;
	private String lastName;
	
	@Basic(optional = true)
	@Column(nullable = true)
	private String email;
	
	private String phoneNumber;
	
	@Basic(optional = true)
	@Column(name = "cusImage", nullable = true)
	private String profileImage;
	
	@Basic(optional = true)
	@Column(nullable = true)
	private String address;
	
}
