package swst.application.models.relationkey;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

@SuppressWarnings("serial")
@Data
public class ProductsColorRelationKey implements Serializable {
	private int caseID;
	private int codeColor;
}
