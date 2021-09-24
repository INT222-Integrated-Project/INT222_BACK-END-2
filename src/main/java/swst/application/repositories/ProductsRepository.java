package swst.application.repositories;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import swst.application.models.products.Products;

public interface ProductsRepository extends JpaRepository<Products, Integer> {
	// List<Products> findAllByPage
	//Page<Products> findByDateOfPlaced(int caseID, Pageable pageable);
}
