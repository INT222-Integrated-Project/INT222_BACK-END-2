package swst.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swst.application.models.Products;

public interface ProductsRepository extends JpaRepository<Products, Integer> {

}
