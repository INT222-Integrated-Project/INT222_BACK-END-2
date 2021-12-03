package swst.application.entities.seperated;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import swst.application.entities.Colors;
import swst.application.entities.Products;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "productcolor")
public class ProductColorToProducts {
	@Id
	@Column(name = "productcolorID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productcolorID;

	/*@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "caseID", nullable = false)
	private Products product;*/
	
	@ManyToOne
	@Basic(optional = true)
	@JoinColumn(name = "caseID", referencedColumnName = "caseID")
	private Products products;

	@ManyToOne
	@JoinColumn(name = "codeColor", referencedColumnName = "codeColor")
	private Colors color;

	private String imageCase;
	private int quantity;
}
