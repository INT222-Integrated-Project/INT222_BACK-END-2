package swst.application.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import swst.application.entities.Models;

public interface ModelsRepository extends JpaRepository<Models, Integer> {
	@Query(value = "SELECT m FROM Models m WHERE m.modelName like %?1%")
	Page<Models> findByModelNameContaining(String modelName, Pageable pageable);
}
