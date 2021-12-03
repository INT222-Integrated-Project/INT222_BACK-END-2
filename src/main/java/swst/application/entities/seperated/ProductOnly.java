package swst.application.entities.seperated;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import swst.application.entities.Models;
import swst.application.entities.Products;
import swst.application.entities.ProductsColor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "products")
public class ProductOnly {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int caseID;

	private String caseName;
	private String caseDescription;
	private float casePrice;

	private int usernameID;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private String caseDate;

	@ManyToMany
	@JoinTable(name = "productmodel", joinColumns = @JoinColumn(name = "caseID", referencedColumnName = "caseID"), inverseJoinColumns = @JoinColumn(name = "modelID", referencedColumnName = "modelID"))
	private List<Models> models;

	private String productImage;

	private Boolean isOnStore;
}
