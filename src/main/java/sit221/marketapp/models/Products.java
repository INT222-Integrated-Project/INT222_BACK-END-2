package sit221.marketapp.models;

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
	private int caseId;

	private String caseName;
	private String caseDescription;
	private Double casePrice;
	private String caseImage;

	@ManyToOne
	@JoinColumn(name = "brand_codeBrand", nullable = false)
	private Brand brand;

	@ManyToMany
	@JoinTable(name = "productcolor", joinColumns = @JoinColumn(name = "productCaseId"), inverseJoinColumns = @JoinColumn(name = "productColorCode"))
	private List<Color> color;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private String caseDate;

}
