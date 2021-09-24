package swst.application.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import swst.application.models.products.Products;

public interface ProductsRepository extends JpaRepository<Products, Integer> {
	// List<Products> findAllByPage
	Page<Products> findByIsOnStore(int onStore, Pageable pageable);
}
