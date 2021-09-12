package swst.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swst.application.models.Status;

public interface StatusRepository extends JpaRepository<Status, Integer>{

}
