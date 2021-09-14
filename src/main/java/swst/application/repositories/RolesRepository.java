package swst.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swst.application.models_users.Roles;

public interface RolesRepository extends JpaRepository<Roles, Integer> {

}
