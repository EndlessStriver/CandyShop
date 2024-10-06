package com.example.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "province")
public class Province {
	
	@Id
	@Column(name = "province_id")
	private String provinceId;
	
	@Column(name = "province_name", nullable = false, unique = true)
	private String provinceName;
	
	@OneToMany(mappedBy = "province", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private List<Address> addresses;
	
	@OneToMany(mappedBy = "province", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private List<Order> orders;
	
}
