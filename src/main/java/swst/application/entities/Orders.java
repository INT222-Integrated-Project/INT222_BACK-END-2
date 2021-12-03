package swst.application.entities;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderID;

	private int userNameID;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private String dateTime;

	@Column(name = "amount", nullable = true)
	private float allPrice;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private String paymentDate;

	@OneToMany(mappedBy = "orders", orphanRemoval = true, fetch = FetchType.LAZY)
	@Nullable
	private List<OrderDetail> orderDetails;

	@ManyToOne
	@Basic(optional = true)
	@JoinColumn(name = "statusID", referencedColumnName = "statusID")
	private OrderStatus orderStatus;
	
	@ManyToOne
	@Basic(optional = true)
	@JoinColumn(name = "userNameID",referencedColumnName = "userNameID",insertable = false, updatable = false)
	private UsernamesModels user;
	
	/*
	@ManyToOne
	@Basic(optional = true)
	@JoinColumn(name="modelID", referencedColumnName = "modelID")
	private Models models;
*/
}
