package swst.application.controllers;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import swst.application.authenSecurity.TokenUtills;
import swst.application.entities.OrderDetail;
import swst.application.entities.OrderStatus;
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
@PropertySource("userdefined.properties")
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

	@Value("${application.pagerequest.maxsize.orders}")
	private int maxsizeOrders;

	@Value("${application.pagerequest.defaultsize.orders}")
	private int defaultSizeOrders;

	// [ purchaseOneProduct ]
	public Orders purchaseOneProduct(long productColorid, int quantity, HttpServletRequest request) {
		log.info(productColorid + " : rderers " + quantity);

		ProductsColor productColorOrdered = productsColorRepository.findById(productColorid)
				.orElseThrow(() -> new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND,
						"[ NOT FOUND ] This product is not exist."));

		int currentUsername = usernameRepository.findByUserName(TokenUtills.getUserNameFromToken(request))
				.getUserNameID();

		if (productColorOrdered.getProduct().getUsernameID() == currentUsername) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SHOP_THIS_IS_YOUR_PRODUCT,
					"[ NOPE ] This is your product, just take it by hand.");
		}

		log.info(productColorOrdered.getQuantity() + "");
		if (quantity > productColorOrdered.getQuantity()) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SHOP_NOT_ENOUGH_GOODS_FOR_SELL,
					"[ OUT OF STOCK ] You cannot order more than how many available on stock.");
		}

		productColorOrdered.setQuantity(productColorOrdered.getQuantity() - quantity);
		productsColorRepository.save(productColorOrdered);

		Timestamp currenTime = new Timestamp(System.currentTimeMillis());

		Orders newOrder = new Orders();
		newOrder.setDateTime(currenTime.toString());
		newOrder.setAllPrice(quantity * productColorOrdered.getProduct().getCasePrice());
		newOrder.setPaymentDate(null);
		newOrder.setOrderStatus(orderStatusRepository.findByStatus("To Pay"));
		newOrder.setUserNameID(currentUsername);
		newOrder = ordersRepository.save(newOrder);

		OrderDetail newOrderDetail = new OrderDetail();
		newOrderDetail.setOrders(newOrder);
		newOrderDetail.setProductcolorID(productColorid);
		newOrderDetail.setQuantityOrder(quantity);
		newOrderDetail.setUnitPrice(productColorOrdered.getProduct().getCasePrice());
		orderDetailRepository.save(newOrderDetail);

		return newOrder;
	}

	// [ ListOrderByUserID]
	public Page<Orders> listOrderByUserID(int page, int size, HttpServletRequest request) {
		if (page < 0) {
			page = 0;
		}
		if (size < 1 || size > defaultSizeOrders) {
			size = defaultSizeOrders;
		}

		int currentUser = usernameRepository.findByUserName(TokenUtills.getUserNameFromToken(request)).getUserNameID();

		Pageable sendPageRequest = PageRequest.of(page, size);
		Page<Orders> result = ordersRepository.findAllByUserNameID(currentUser, sendPageRequest);

		if (result.getTotalPages() < page + 1) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND, "[ NOT FOUND ] Nothing here. :(");
		}

		return result;
	}
	
	// [ ListOrderedOrderByUserID ]
	public Page<Orders> ListOrderedOrderByUserID(HttpServletRequest request){
		int currentUser = usernameRepository.findByUserName(TokenUtills.getUserNameFromToken(request)).getUserNameID();
		OrderStatus orderStatus = orderStatusRepository.findByStatus("To Pay");
		Pageable sendPageRequest = PageRequest.of(0, defaultSizeOrders);
		Page<Orders> orderedOrder = ordersRepository.findAllByOrderStatusAndUserNameID(orderStatus,currentUser,sendPageRequest);
		return orderedOrder;
	}

	// [ ListAllOrders ]
	public Page<Orders> ListAllOrders(int page, int size, String searchByUserName, String searchStatus) {
		if (page < 0) {
			page = 0;
		}
		if (size < 1 || size > defaultSizeOrders) {
			size = defaultSizeOrders;
		}
		int currentUser = 0;
		Page<Orders> result;
		Pageable sendPageRequest = PageRequest.of(page, size);

		if (!searchByUserName.equals("") && !searchStatus.equals("")) {
			if (usernameRepository.existsByUserNameIgnoreCase(searchByUserName)) {
				currentUser = usernameRepository.findByUserName(searchByUserName).getUserNameID();
				OrderStatus currnetSearch = orderStatusRepository.findByStatus(searchStatus);
				result = ordersRepository.findAllByOrderStatusAndUserNameID(currnetSearch, currentUser,	sendPageRequest);
			} else {
				throw new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND, "[ NOT FOUND ] Nothing here. :(");
			}
		} else if (!searchByUserName.equals("") || !searchStatus.equals("")) {
			if (!searchByUserName.equals("")) {
				if (usernameRepository.existsByUserNameIgnoreCase(searchByUserName)) {
					currentUser = usernameRepository.findByUserName(searchByUserName).getUserNameID();
					result = ordersRepository.findAllByUserNameID(currentUser, sendPageRequest);
				} else {
					throw new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND, "[ NOT FOUND ] Nothing here. :(");
				}
			} else {
				OrderStatus currnetSearch = orderStatusRepository.findByStatus(searchStatus);
				result = ordersRepository.findAllByOrderStatus(currnetSearch, sendPageRequest);
			}
		} else {
			result = ordersRepository.findAllByOrderByOrderIDDesc(sendPageRequest);
		}
		if (result.getTotalPages() < page + 1) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND, "[ NOT FOUND ] Nothing here. :(");
		}
		return result;
	}

	// [ addOrder ]
	public ActionResponseModel addOrder(HttpServletRequest request, Orders orders) {

		int userNameId = usernameRepository.findByUserName(TokenUtills.getUserNameFromToken(request)).getUserNameID();
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		Orders addOrder = new Orders();

		addOrder.setUserNameID(userNameId);
		addOrder.setDateTime(currentTime.toString());
		addOrder.setAllPrice(0);
		addOrder.setPaymentDate(null);
		addOrder.setOrderStatus(orderStatusRepository.findByStatus("To Pay"));

		log.info("" + addOrder.getDateTime());

		addOrder = ordersRepository.save(addOrder);

		loopSaveOrderDetails(orders.getOrderDetails(), addOrder);
		addOrder.setOrderDetails(orders.getOrderDetails());

		return new ActionResponseModel("Adding orders.", true);
	}

	// [ loopSaveOrderDetails ]
	private Orders loopSaveOrderDetails(List<OrderDetail> orderDetailList, Orders addOrder) {

		int detailsNumber = orderDetailList.size();

		long[] productColorIdList = new long[detailsNumber];
		int[] orderQuantityList = new int[detailsNumber];

		float allPrice = 0.0f;

		for (int i = 0; i < detailsNumber; i++) {
			OrderDetail currentOrder = orderDetailList.get(i);
			OrderDetail newOrder = new OrderDetail();
			Optional<ProductsColor> targerProductColor = productsColorRepository
					.findById(currentOrder.getProductcolorID());
			if (targerProductColor == null) {
				deleteOrder(addOrder.getOrderID());
				throw new ExceptionFoundation(EXCEPTION_CODES.SHOP_NOT_ON_STORE,
						"[ NOT ON STORE ] This item is not exist or not for sell.");
			}
			Optional<Products> targerProduct = productsRepository
					.findById(targerProductColor.get().getProduct().getCaseID());
			if (targerProduct == null) {
				deleteOrder(addOrder.getOrderID());
				throw new ExceptionFoundation(EXCEPTION_CODES.SHOP_NOT_ON_STORE,
						"[ NOT ON STORE ] This item is not exist or not for sell.");
			}

			newOrder.setQuantityOrder(currentOrder.getQuantityOrder());
			newOrder.setUnitPrice(targerProduct.get().getCasePrice());
			newOrder.setOrders(addOrder);
			newOrder.setProductcolorID(currentOrder.getProductcolorID());

			allPrice += (newOrder.getQuantityOrder() * newOrder.getUnitPrice());
			orderQuantityList[i] = newOrder.getQuantityOrder();
			productColorIdList[i] = newOrder.getProductcolorID();

			orderDetailRepository.save(newOrder);

		}

		for (int i = 0; i < productColorIdList.length; i++) {
			ProductsColor currentProduct = productsColorRepository.findById(productColorIdList[i])
					.orElseThrow(() -> new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND,
							"[ NOT FOUND ] Product of this type is not exist."));
			currentProduct.setQuantity(currentProduct.getQuantity() - orderQuantityList[i]);
			productsColorRepository.save(currentProduct);
		}

		Optional<ProductsColor> newPro = productsColorRepository.findById((long) 1);
		newPro.get().setQuantity(newPro.get().getQuantity() + 1);
		productsColorRepository.save(newPro.get());

		addOrder.setAllPrice(allPrice);
		addOrder = ordersRepository.save(addOrder);
		return addOrder;
	}

	// [ cancelOrder ]
	public Orders cancelOrder(long orderId, HttpServletRequest request) {

		UsernamesModels currentUserName = usernameRepository.findByUserName(TokenUtills.getUserNameFromToken(request));

		Orders currentOrder = ordersRepository.findById(orderId)
				.orElseThrow(() -> new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND,
						"[ NOT FOUND ] Order with this ID is not exist."));

		if (currentOrder.getUserNameID() != currentUserName.getUserNameID()) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SAVE_NOT_THE_OWNER,
					"[ NOT ALLOWED ] This order is not belong to you.");
		}
		if (currentOrder.getOrderStatus().getStatusID() != 1) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SHOP_NOT_ALLOW_TO_CANCLE,
					"[ PAID ] You paid for this product or is not in a status that you will be able to cancle.");
		}
		returnStockFromCancledOrder(currentOrder.getOrderDetails());
		currentOrder.setOrderStatus(orderStatusRepository.findById(5)
				.orElseThrow(() -> new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND,
						"[ NOT FOUND ] This status with this ID is not exist.")));
		ordersRepository.save(currentOrder);
		return currentOrder;
	}

	// [ changeOrderStatusByStaff]
	public Orders changeOrderStatusByStaff(long orderId, int statusId) {
		Orders currentOrder = ordersRepository.findById(orderId)
				.orElseThrow(() -> new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND,
						"[ NOT FOUND ] Order with this ID is nit exist."));
		OrderStatus status = orderStatusRepository.findById(statusId)
				.orElseThrow(() -> new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND,
						"[ NOT FOUND ] This status with this ID is not exist."));
		if (currentOrder.getOrderStatus().getStatusID() == 4) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SHOP_NOT_ALLOW_TO_CHANGE_STATUS,
					"[ NOT ALLOWED ] Can't give status to a completed orders.");
		}
		if (currentOrder.getOrderStatus().getStatusID() == 5) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SHOP_NOT_ALLOW_TO_CANCLE,
					"[ CANCLED ] Can't give status to a cancelled orders.");
		}
		if (statusId == 5) {
			List<OrderDetail> cancellationList = currentOrder.getOrderDetails();
			returnStockFromCancledOrder(cancellationList);
		}

		currentOrder.setOrderStatus(status);
		currentOrder = ordersRepository.save(currentOrder);
		return currentOrder;
	}

	// [ returnStockFromCancledOrder ]
	private void returnStockFromCancledOrder(List<OrderDetail> cancellationList) {
		for (int i = 0; i < cancellationList.size(); i++) {
			Optional<ProductsColor> currentProduct = productsColorRepository
					.findById(cancellationList.get(i).getProductcolorID());
			currentProduct.get()
					.setQuantity(cancellationList.get(i).getQuantityOrder() + currentProduct.get().getQuantity());
		}
	}

	// [Delete order]
	private void deleteOrder(long id) {
		ordersRepository.deleteById(id);
	}

}