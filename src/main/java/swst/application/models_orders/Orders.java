package swst.application.models_orders;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderID;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private String dateTime;

	private float amount;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private String paymentDate;
	
	
	/*@OneToOne
	private Status statusID;
	private int userNameID;
*/
}
