package swst.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import swst.application.models.products.ProductModel;
import swst.application.models.relationkey.ProductModelRelationKey;

public interface ProductModelRepository extends JpaRepository<ProductModel, ProductModelRelationKey>{

}
