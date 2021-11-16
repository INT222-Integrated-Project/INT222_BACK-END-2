package swst.application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import swst.application.entities.ProductsColor;

public interface ProductsColorRepository extends JpaRepository<ProductsColor, Long> {

	@Query(value = "SELECT p FROM ProductsColor p WHERE p.caseID = ?1")
	List<ProductsColor> findAllBycaseID(int caseID);
}
