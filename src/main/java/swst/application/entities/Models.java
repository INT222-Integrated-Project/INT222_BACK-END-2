package swst.application.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	
	@ManyToOne
	@JoinColumn(name = "codeBrand", nullable = false)
	private Brands brand;
}
