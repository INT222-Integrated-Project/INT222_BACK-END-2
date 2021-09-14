package swst.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swst.application.model_products.ProductModel;
import swst.application.relationkeyclasses.ProductModelRelationKey;

public interface ProductModelRepository extends JpaRepository<ProductModel, ProductModelRelationKey>{

}
