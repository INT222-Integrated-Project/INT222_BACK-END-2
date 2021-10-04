package swst.application.models.relationkey;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

@SuppressWarnings("serial")
@Embeddable
@Data
public class ProductModelRelationKey implements Serializable {

	@Column(name = "modelID")
	private int modelID;
	@Column(name = "caseID")
	private int caseID;

}
