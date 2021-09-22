package swst.application.models.products;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import swst.application.models.relationkey.ProductModelRelationKey;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "productmodel")
public class ProductModel {

	@EmbeddedId
	private ProductModelRelationKey id;

}
