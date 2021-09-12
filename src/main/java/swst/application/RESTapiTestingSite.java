package swst.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swst.application.models.OrderDetail;
import swst.application.models.ProductsColor;
import swst.application.repositories.OrderDetailRepository;
import swst.application.repositories.ProductsColorRepository;

@RestController
@RequestMapping("/test")
public class RESTapiTestingSite {
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private ProductsColorRepository productsColorRepository;
	
	@RequestMapping("/orderdetails")
	public List<OrderDetail> listAllorder(){
		return orderDetailRepository.findAll();
	}
	
	@RequestMapping("/productcolors")
	public List<ProductsColor> listAllPrCo(){
		return productsColorRepository.findAll();
	}
}
