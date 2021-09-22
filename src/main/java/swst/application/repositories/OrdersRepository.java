package swst.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import swst.application.models.orders.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

}
