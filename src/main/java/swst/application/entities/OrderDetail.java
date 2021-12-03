package swst.application.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import swst.application.entities.seperated.ProductColorToProducts;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orderdetail")
public class OrderDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderdetailID;

	private int quantityOrder;

	@Column(name = "unitPrice")
	private float unitPrice;

	private long productcolorID;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "orderid", nullable = false)
	@JsonIgnore
	private Orders orders;
	
	@ManyToOne
	@Basic(optional = true)
	@JoinColumn(name = "productcolorID", referencedColumnName = "productcolorID", insertable = false,updatable = false)
	private ProductColorToProducts productColorToProducts;
	
}
