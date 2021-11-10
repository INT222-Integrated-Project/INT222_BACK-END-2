package swst.application.entities;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@JsonIgnore
	@Basic(optional = true)
	private int caseID;

	@ManyToOne
	@MapsId(value = "codeColor")
	@JoinColumn(name = "codeColor", referencedColumnName = "codeColor")
	private Colors color;

	private String imageCase;
	private int quantity;
}
