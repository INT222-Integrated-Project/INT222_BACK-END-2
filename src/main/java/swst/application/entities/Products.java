package swst.application.entities;

import java.util.List;

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
	private float casePrice;

	private int usernameID;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private String caseDate;

	@OneToMany(cascade = CascadeType.MERGE)
	@JoinColumn(name = "caseID", referencedColumnName = "caseID")
	private List<ProductsColor> productColor;

	@ManyToMany
	@JoinTable(name = "productmodel", joinColumns = @JoinColumn(name = "caseID", referencedColumnName = "caseID"), inverseJoinColumns = @JoinColumn(name = "modelID", referencedColumnName = "modelID"))
	private List<Models> models;

	private String productImage;

	private Boolean isOnStore;

}

/*
 * @ManyToMany
 * 
 * @JoinTable(name = "productscolor", joinColumns = @JoinColumn(name = "caseID",
 * referencedColumnName = "caseID"), inverseJoinColumns = @JoinColumn(name =
 * "codeColor", referencedColumnName = "codeColor")) private List<Colors>
 * colors;
 */
