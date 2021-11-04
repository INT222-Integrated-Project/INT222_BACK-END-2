package swst.application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import swst.application.entities.ProductsColor;
import swst.application.models.relationkey.ProductsColorRelationKey;

public interface ProductsColorRepository extends JpaRepository<ProductsColor,Long> {

	List<ProductsColor> findAllBycaseID(long caseID);
}
