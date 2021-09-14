package swst.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swst.application.models_users.UsernamesModels;

public interface UsernameRepository extends JpaRepository<UsernamesModels, Integer> {

}
