package swst.application.entities;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties({"products"})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "colors")
public class Colors {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codeColor;
	
	private String caseColor;
	
	@OneToMany(mappedBy = "colors")
	@JsonIgnore
	private Set<Products> products;

}
