package swst.application.models.relationkey;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

@SuppressWarnings("serial")
@Embeddable
@Data
public class ProductsColorRelationKey implements Serializable {

	@Column(name = "caseID")
	int caseID;
	@Column(name = "codeColor")
	int codeColor;

}
