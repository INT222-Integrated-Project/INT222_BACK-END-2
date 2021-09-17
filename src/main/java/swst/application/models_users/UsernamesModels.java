package swst.application.models_users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private String userPassword;

/*	@Basic(optional = true)
	@Column(name = "sellerID", nullable = true)
	private int sellerID;
	
	@Basic(optional = true)
	@Column(name = "customerID", nullable = true)
	private int customerID;*/

	private int roleID;
	
	/*@OneToOne(mappedBy = "username", cascade = CascadeType.ALL)
	private Customers customer;*/
/*
	@JoinColumn(name = "sellerID", referencedColumnName = "sellerID")
	private Sellers seller;*/
	
	/*@OneToOne
	@JoinColumn(name = "roleID", referencedColumnName = "roleID")
	private Roles role;*/
}
