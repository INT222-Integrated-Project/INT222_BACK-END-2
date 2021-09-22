package swst.application.models.orders;

import javax.persistence.*;

import lombok.*;
import swst.application.models.relationkey.OrderDetailRelationKey;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orderdetail")
public class OrderDetail {
	
	@EmbeddedId
	private OrderDetailRelationKey id;
	
	private int quantityOrder;
	private float unitPrice;
	
	//@ManyToOne
	//@JoinColumn(name = "caseID", referencedColumnName = "caseID")  
	//private Products product;
	
}
