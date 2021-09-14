package swst.application.model_products;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "models")
public class Models {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int modelID;

	private String modelName;
	/*
	@OneToMany
	private Set<ProductModel> productModels; 
	/*
	@ManyToOne
	@JoinColumn(name = "codeBrand",nullable = false)
	private Brands brand;
	
	@ManyToMany
	@JoinTable(name = "productmodel", inverseJoinColumns = @JoinColumn(name = "caseID", referencedColumnName = "caseID"), joinColumns = @JoinColumn(name = "modelID", referencedColumnName = "modelID"))
	private List<Products> product;*/
}
