package swst.application.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "productcolor")
public class ProductsColor {
	@Id
	@Column(name = "productcolorID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productcolorID;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "caseID", nullable = false)
	@JsonBackReference
	private Products product;

	@ManyToOne
	@JoinColumn(name = "codeColor", referencedColumnName = "codeColor")
	private Colors color;

	private String imageCase;
	private int quantity;

}
