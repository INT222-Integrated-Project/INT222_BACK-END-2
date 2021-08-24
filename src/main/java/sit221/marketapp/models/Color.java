package sit221.marketapp.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@JsonIgnoreProperties({"products"})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "color")
public class Color {

	@Id
	//@Basic(optional = true)
	//@Column(name = "codeColor", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codeColor;

	//@Basic(optional = false)
	//@Column(name = "caseColor", nullable = false)
	private String caseColor;

	@ManyToMany
	private List<Products> products;
	
}
