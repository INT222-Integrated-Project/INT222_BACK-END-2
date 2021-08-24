package sit221.marketapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit221.marketapp.models.Products;

public interface ProductsRepository extends JpaRepository<Products, Integer> {

}
