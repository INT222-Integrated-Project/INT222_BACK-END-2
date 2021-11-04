package swst.application.controllers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import swst.application.authenSecurity.TokenUtills;
import swst.application.entities.OrderDetail;
import swst.application.entities.Orders;
import swst.application.entities.Products;
import swst.application.entities.ProductsColor;
import swst.application.entities.UsernamesModels;
import swst.application.errorsHandlers.ExceptionFoundation;
import swst.application.errorsHandlers.ExceptionresponsesModel.EXCEPTION_CODES;
import swst.application.models.ActionResponseModel;
import swst.application.repositories.OrderDetailRepository;
import swst.application.repositories.OrderStatusRepository;
import swst.application.repositories.OrdersRepository;
import swst.application.repositories.ProductsColorRepository;
import swst.application.repositories.ProductsRepository;
import swst.application.repositories.UsernameRepository;

@Service
@PropertySource("")
@Slf4j
public class ProductOrderController {
	@Autowired
	private OrdersRepository ordersRepository;
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private UsernameRepository usernameRepository;
	@Autowired
	private ProductsRepository productsRepository;
	@Autowired
	private ProductsColorRepository productsColorRepository;
	@Autowired
	private OrderStatusRepository orderStatusRepository;

	// [ addOrder ]
	public Orders addOrder(HttpServletRequest request, Orders orders) {

		int userNameId = usernameRepository.findByUserName(TokenUtills.getUserNameFromToken(request)).getUserNameID();
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		Timestamp paymentTime = new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 120);
		Orders addOrder = new Orders();

		addOrder.setUserNameID(userNameId);
		addOrder.setDateTime(currentTime.toString());
		addOrder.setAllPrice(0);
		addOrder.setPaymentDate(paymentTime.toString());
		addOrder.setOrderStatus(orderStatusRepository.findByStatus("To Pay"));
		addOrder = ordersRepository.save(addOrder);

		LoopSaveOrderDetail productColorIdList = loopSaveOrderDetails(orders.getOrderDetails(), addOrder);
		if (!productColorIdList.getIsContinueable()) {
			ordersRepository.deleteById(addOrder.getOrderID());
			throw new ExceptionFoundation(EXCEPTION_CODES.SHOP_NOT_ENOUGH_GOODS_FOR_SELL,
					"[ NOT ENOUGH ITEM FOR SELL ] This store will not be able to complete your order because there are not enough item for sell.");
		}

		// orders.setUserNameID(ownerModel.getUserNameID());

		// ordersRepository.save(addOrder);
		return addOrder;// new ActionResponseModel("Adding orders.", true);
	}

	// [ loopSaveOrderDetails ]
	private LoopSaveOrderDetail loopSaveOrderDetails(List<OrderDetail> orderDetailList, Orders addOrder) {

		int detailsNumber = orderDetailList.size();

		long[] productColorIdList = new long[detailsNumber];
		int[] orderQuantityList = new int[detailsNumber];

		Boolean allowContinueSaving = true;
		Double allPrice = 0.0;

		for (int i = 0; i < detailsNumber; i++) {
			OrderDetail currentOrder = orderDetailList.get(i);
			OrderDetail newOrder = new OrderDetail();
			Optional<ProductsColor> targerProductColor = productsColorRepository
					.findById(currentOrder.getProductcolorID());
			if (targerProductColor == null) {
				throw new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND,
						"[ NOT FOUND ] Targeted product variation not found. ");
			}
			Optional<Products> targerProduct = productsRepository.findById(targerProductColor.get().getCaseID());
			if (targerProduct == null) {
				throw new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND,
						"[ NOT FOUND ] Targeted product not found. ");
			}
			newOrder.setQuantityOrder(currentOrder.getQuantityOrder());
			newOrder.setUnitPrice(targerProduct.get().getCasePrice());
			newOrder.setOrders(addOrder);
			newOrder.setProductcolorID(currentOrder.getProductcolorID());

			orderQuantityList[i] = newOrder.getQuantityOrder();
			productColorIdList[i] = newOrder.getProductcolorID();

			orderDetailRepository.save(newOrder);

		}
		return new LoopSaveOrderDetail(productColorIdList, orderQuantityList, allPrice, allowContinueSaving);
	}

	// orderDetailRepository.save(newOrder);

	// currentOrder.setOrder(orderSavedTo);

	// orderDetailRepository.save(currentOrder);
	// ProductsColor currentProduct =
	// productsColorRepository.getById(currentOrder.getOrderdetailID());
	// log.error(currentProduct.getCaseID()+"");
	// int productId = currentProduct.getCaseID();
	// int availableQuantity = currentProduct.getQuantity();
	/* log.info(availableQuantity + ""); */

	/*
	 * currentOrder.setUnitPrice(productsRepository.findById(productId).get().
	 * getCasePrice()); productColorIdList[i] = currentOrder.getProductcolorID();
	 * orderQuantityList[i] = currentOrder.getQuantityOrder(); if (availableQuantity
	 * < currentOrder.getQuantityOrder()) { allowContinueSaving = false; break; }
	 */

	// [ loopSaveProductQuantity ]
	private Boolean loopSaveProductQuantity() {
		return false;
	}

	// []

	// []

	// []

	// []
}

@Data
@AllArgsConstructor
class LoopSaveOrderDetail {
	private long[] productColorIdList;
	private int[] quantity;
	private Double allPrice;
	private Boolean isContinueable;
}