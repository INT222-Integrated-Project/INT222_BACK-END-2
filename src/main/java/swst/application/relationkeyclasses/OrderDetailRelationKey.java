package swst.application.relationkeyclasses;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class OrderDetailRelationKey implements Serializable {
	
	@Column(name = "orderID")
	int orderID;
	@Column(name = "caseID")
	int caseID;
	
}
