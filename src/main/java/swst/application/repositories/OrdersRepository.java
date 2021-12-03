package swst.application.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import swst.application.entities.OrderDetail;
import swst.application.entities.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
	List<Orders> findAllByUserNameID(int userNameID); 
	
	Page<Orders> findAllByOrderByOrderIDDesc(Pageable pageable);

	@Query(value = "SELECT o FROM Orders o WHERE o.userNameID = ?1 ORDER BY orderID DESC")
	Page<Orders> findAllByUserNameID(int userNameID, Pageable pageable);
	
	@Query(value = "SELECT o FROM Orders o WHERE o.orderDetails = ?1 ORDER BY orderID DESC")
	Page<Orders> findByOrderDetails(OrderDetail orderDetail,Pageable pageable);
	
	boolean existsByUserNameID(int userNameID);
}
