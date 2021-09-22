package swst.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import swst.application.models.products.Colors;

public interface ColorsRepository extends JpaRepository<Colors, Integer>{

}
