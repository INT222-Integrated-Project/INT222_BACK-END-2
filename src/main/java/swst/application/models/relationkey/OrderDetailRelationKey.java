package swst.application.models.relationkey;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

@SuppressWarnings("serial")
@Embeddable
@Data
public class OrderDetailRelationKey implements Serializable {
	
	@Column(name = "orderID")
	int orderID;
	@Column(name = "caseID")
	int caseID;
	
}
