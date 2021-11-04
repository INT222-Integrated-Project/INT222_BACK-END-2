package swst.application.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

}
