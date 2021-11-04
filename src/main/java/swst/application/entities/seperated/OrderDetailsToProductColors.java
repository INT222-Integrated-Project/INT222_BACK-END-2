package swst.application.entities.seperated;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import swst.application.entities.Models;
import swst.application.entities.Products;
import swst.application.entities.ProductsColor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "products")
public class OrderDetailsToProductColors {
	@Id
	private long id;
}
