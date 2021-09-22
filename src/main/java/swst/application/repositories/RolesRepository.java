package swst.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import swst.application.models.users.Roles;


@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
	Roles findByRoleName(int roleID);
}
