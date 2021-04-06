package it.nlatella.dvdrental.data.entity;

import java.io.Serializable;

import javax.annotation.concurrent.Immutable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the sales_by_film_category database table.
 * 
 */
@Entity
@Immutable
@Table(name="sales_by_film_category")
@NamedQuery(name="SalesByFilmCategory.findAll", query="SELECT s FROM SalesByFilmCategory s")
public class SalesByFilmCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String category;

	@Column(name="total_sales")
	private BigDecimal totalSales;

	public SalesByFilmCategory() {
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public BigDecimal getTotalSales() {
		return this.totalSales;
	}

	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}

}