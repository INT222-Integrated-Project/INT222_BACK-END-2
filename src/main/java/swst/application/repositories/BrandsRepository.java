package swst.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import swst.application.models.products.Brands;

public interface BrandsRepository extends JpaRepository<Brands, Integer> {

}
