package swst.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import swst.application.entities.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer>{

}
