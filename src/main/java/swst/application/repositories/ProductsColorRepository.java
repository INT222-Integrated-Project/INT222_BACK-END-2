package swst.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swst.application.model_products.ProductsColor;
import swst.application.relationkeyclasses.ProductsColorRelationKey;

public interface ProductsColorRepository extends JpaRepository<ProductsColor, ProductsColorRelationKey> {

}
