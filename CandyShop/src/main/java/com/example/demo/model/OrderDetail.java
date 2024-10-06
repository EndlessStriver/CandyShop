package com.example.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class OrderDetail {
	
	@Id
	@Column(name = "order_detail_id")
	private String orderDetailId;
	
	@Column(name = "quantity", nullable = false)
	private double quantity;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "price_history_id", nullable = false)
	private PriceHistory priceHistory;
}
