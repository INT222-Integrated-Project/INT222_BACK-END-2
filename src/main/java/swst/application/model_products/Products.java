package swst.application.model_products;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "products")
public class Products {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int caseID;

	private String caseName;
	private String caseDescription;
	private Double casePrice;

	private int usernameID;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private String caseDate;

	@ManyToMany
	@JoinTable(name = "productscolor", joinColumns = @JoinColumn(name = "caseID", referencedColumnName = "caseID"), inverseJoinColumns = @JoinColumn(name = "codeColor", referencedColumnName = "codeColor"))
	private List<Colors> colors;
	
	@ManyToMany
	@JoinTable(name = "productmodel", joinColumns = @JoinColumn(name = "caseID", referencedColumnName = "caseID"), inverseJoinColumns = @JoinColumn(name = "modelID", referencedColumnName = "modelID"))
	private List<Models> models;
	
	private String productImage;

}
