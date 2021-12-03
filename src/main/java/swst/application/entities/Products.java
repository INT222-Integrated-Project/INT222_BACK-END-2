package swst.application.entities;

import java.util.List;

import javax.persistence.*;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

	@OneToMany(mappedBy = "product", orphanRemoval = true, fetch = FetchType.LAZY)
	@Nullable
	@JsonManagedReference
	private List<ProductsColor> productColor;

	@ManyToMany
	@JoinTable(name = "productmodel", joinColumns = @JoinColumn(name = "caseID", referencedColumnName = "caseID"), inverseJoinColumns = @JoinColumn(name = "modelID", referencedColumnName = "modelID"))
	private List<Models> models;

	private String productImage;

	private Boolean isOnStore;

}