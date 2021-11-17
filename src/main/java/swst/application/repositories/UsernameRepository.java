package swst.application.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swst.application.entities.UsernamesModels;

@Repository
public interface UsernameRepository extends JpaRepository<UsernamesModels, Integer> {

	UsernamesModels findByUserName(String userName);

	boolean existsByEmailIgnoreCase(String email);

	boolean existsByUserNameIgnoreCase(String userName);

	boolean existsByPhoneNumber(String phoneNumber);

	@Query(value = "SELECT u FROM UsernamesModels u WHERE u.userName LIKE %?1% OR u.email LIKE %?1% ORDER BY u.userNameID DESC")
	Page<UsernamesModels> findByUserNameContainingOrEmailContaining(String searchContent, Pageable pageable);

	@Query(value = "SELECT u FROM UsernamesModels u WHERE u.phoneNumber LIKE ?1% ORDER BY u.userNameID DESC")
	Page<UsernamesModels> findByPhoneNumber(String searchContent,Pageable pageable);
}
