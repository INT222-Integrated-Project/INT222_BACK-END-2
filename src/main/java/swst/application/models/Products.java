package swst.application.models;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

	// ONE TO MANY RELATION
	private int codeBrand;

	// ONE TO ONE
	private int sellerID;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private String caseDate;

	@ManyToMany
	@JoinTable(name = "productscolor", joinColumns = @JoinColumn
	(name = "caseID", referencedColumnName = "caseID"), inverseJoinColumns = @JoinColumn(name = "codeColor" , referencedColumnName = "codeColor"))
	private List<Colors> colors;

}
