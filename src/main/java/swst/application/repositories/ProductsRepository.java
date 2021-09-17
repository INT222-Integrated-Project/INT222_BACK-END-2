package swst.application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import swst.application.model_products.Products;

public interface ProductsRepository extends JpaRepository<Products, Integer> {
	//List<Products> findAllByPage
}
