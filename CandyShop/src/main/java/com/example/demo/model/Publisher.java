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
@Table(name = "publisher")
public class Publisher {
	
	@Id
	@Column(name = "publisher_id")
	private String publisherId;
	
	@Column(name = "publisher_name", nullable = false)
	private String publisherName;
	
	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private List<Product> products;
}
