package swst.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swst.application.models.OrderDetail;
import swst.application.relationkeyclasses.OrderDetailRelationKey;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailRelationKey> {

}
