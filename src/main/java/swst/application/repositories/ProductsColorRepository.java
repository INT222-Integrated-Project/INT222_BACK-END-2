package swst.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import swst.application.entities.ProductsColor;
import swst.application.models.relationkey.ProductsColorRelationKey;

public interface ProductsColorRepository extends JpaRepository<ProductsColor, ProductsColorRelationKey> {
	/*
	@Query("SELECT p FROM ProductsColor AS p WHERE p.caseID = ?1 ")
	List<ProductsColor> findAllColorAvailableInThisProduct(@Param(value = "castID") int castID);
*/}
