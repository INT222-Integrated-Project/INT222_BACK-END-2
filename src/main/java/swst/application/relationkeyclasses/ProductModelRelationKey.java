package swst.application.relationkeyclasses;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ProductModelRelationKey implements Serializable {

	@Column(name = "modelID")
	private int modelID;
	@Column(name = "caseID")
	private int caseID;

}
