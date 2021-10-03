package swst.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import swst.application.entities.OrderDetail;
import swst.application.models.relationkey.OrderDetailRelationKey;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailRelationKey> {

}
