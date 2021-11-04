package swst.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import swst.application.entities.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

}
