package swst.application.model_products;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import swst.application.relationkeyclasses.ProductModelRelationKey;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "productmodel")
public class ProductModel {

	@EmbeddedId
	private ProductModelRelationKey id;

}
