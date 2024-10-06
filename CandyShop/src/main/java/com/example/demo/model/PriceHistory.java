package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "price_history")
public class PriceHistory {
	
	@Id
	@Column(name = "private_history_id")
	private String privateHistoryId;
	
	@Column(name = "price_change_date", nullable = false)
	private LocalDateTime priceChangeDate;
	
	@Column(name = "previous_price", nullable = false)
	private double previousPrice;
	
	@Column(name = "new_price", nullable = false)
	private double newPrice;
	
	@Column(name = "price_change_reason", nullable = true)
	private String priceChangeReason;
	
	@Column(name = "price_change_effective_date", nullable = false)
	private LocalDateTime priceChangeEffectiveDate;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	@OneToMany(mappedBy = "priceHistory", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<OrderDetail> orderDetails;
}
