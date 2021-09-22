package swst.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swst.application.models.products.Products;

public interface ProductsRepository extends JpaRepository<Products, Integer> {
	// List<Products> findAllByPage
}
