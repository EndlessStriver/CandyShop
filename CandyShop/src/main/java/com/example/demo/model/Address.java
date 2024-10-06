package com.example.demo.model;

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
@Table(name = "address")
public class Address {
	
	@Id
	@Column(name = "address_id")
	private String addressId;
	
	@Column(name = "customer_name", nullable = false)
	private String customerName;
	
	@Column(name = "phone_number", nullable = false)
	private String phoneNunber;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "province_id")
	private Province province;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "district_id")
	private District district;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ward_id")
	private Ward ward;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "user_id")
	private User user;
}
