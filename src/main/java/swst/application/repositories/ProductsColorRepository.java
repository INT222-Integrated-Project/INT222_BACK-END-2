package swst.application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import swst.application.model_products.ProductsColor;
import swst.application.relationkeyclasses.ProductsColorRelationKey;

public interface ProductsColorRepository extends JpaRepository<ProductsColor, ProductsColorRelationKey> {
	/*
	@Query(value = "SELECT p FROM productscolor AS p WHERE p.caseID = %?1% ")
	List<ProductsColor> findAllColorAvailableInThisProduct(int castID);*/
}
