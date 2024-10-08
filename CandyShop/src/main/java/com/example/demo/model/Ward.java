package com.example.demo.model;

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
@Table(name = "ward")
public class Ward {
	
	@Id
	@Column(name = "ward_id")
	private String wardId;
	
	@Column(name = "ward_name", nullable = false, unique = true)
	private String wardName;
	
	@OneToMany(mappedBy = "ward", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private List<Address> addresses;
	
	@OneToMany(mappedBy = "ward", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private List<Order> orders;
}
