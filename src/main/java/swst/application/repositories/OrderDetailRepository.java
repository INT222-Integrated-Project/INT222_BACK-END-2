package swst.application.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import swst.application.entities.OrderDetail;
import swst.application.entities.Orders;
import swst.application.models.relationkey.OrderDetailRelationKey;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailRelationKey> {
	
	boolean existsByproductcolorID(long productcolorID);
	
	Page<OrderDetail> findAllByProductcolorID(long[] productcolorID,Pageable pageable);
}
