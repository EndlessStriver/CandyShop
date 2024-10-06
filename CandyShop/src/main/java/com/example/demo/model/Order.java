package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.model.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "my_order")
public class Order {
	
	@Id
	@Column(name = "order_id")
	private String orderId;
	
	@Column(name = "order_date", nullable = false)
	private LocalDateTime orderDate;
	
	@Column(name = "total_amount", nullable = false)
	private double totalAmount;
	
	@Column(name = "note", nullable = true)
	private String note;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "province_id", nullable = false)
	private Province province;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "district_id", nullable = false)
	private District district;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ward_id", nullable = false)
	private Ward ward;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private OrderStatus status;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderDetail> orderDetails;
}
