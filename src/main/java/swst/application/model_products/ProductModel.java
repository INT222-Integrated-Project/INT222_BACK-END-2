package swst.application.model_products;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import swst.application.relationkeyclasses.ProductModelRelationKey;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "productmodel")
public class ProductModel {

	@EmbeddedId
	private ProductModelRelationKey id;
	
	/*
	@ManyToOne
	@MapsId(value = "caseID")
	@JoinColumn(name = "caseID", referencedColumnName = "caseID")
	private Products product;
	
	@ManyToOne
	@MapsId(value = "modelID")
	@JoinColumn(name = "modelID", referencedColumnName = "modelID")
	private Models model;*/
}
