package swst.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import swst.application.entities.seperated.ProductColorToProducts;

public interface ProductColorToProductsRepository extends JpaRepository<ProductColorToProducts, Long>{
	
}
