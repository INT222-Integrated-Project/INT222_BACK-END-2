package swst.application.relationkeyclasses;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductsColorRelationKey implements Serializable {
	
	@Column(name = "caseID")
	int caseID;
	
	@Column(name = "codeColor")
	int codeColor;
}
