package swst.application.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import swst.application.entities.Products;

public interface ProductsRepository extends JpaRepository<Products, Integer> {
	// List<Products> findAllByPage
	
	@Query(value = "SELECT p FROM Products p WHERE p.isOnStore = ?1 ORDER BY caseID DESC")
	Page<Products> findByIsOnStore(int onStore, Pageable pageable);
	
	@Query(value = "SELECT p FROM Products p WHERE p.caseName LIKE %?1% ORDER BY caseID DESC")
	Page<Products> findBycaseNameContaining(String caseName, Pageable pageable);
	
	
}
