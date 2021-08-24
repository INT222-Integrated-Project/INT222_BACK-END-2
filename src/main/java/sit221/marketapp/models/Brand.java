package sit221.marketapp.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@JsonIgnoreProperties({"products"})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "brand")
public class Brand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Basic(optional = true)
//	@Column(name = "codeBrand", nullable = false)
	private int codeBrand;

//	@Basic(optional = false)
//	@Column(name = "caseBrand", nullable = false)
	private String caseBrand;
	
	@JsonIgnore
	@OneToMany(orphanRemoval = true, mappedBy = "brand")
	private List<Products> products;


}
