package swst.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import swst.application.entities.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {

	Roles findByroleName(String roleName);
}
