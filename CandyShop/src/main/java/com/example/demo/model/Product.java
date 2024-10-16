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
@Table(name = "product")
public class Product {
	
	@Id
	@Column(name = "product_id")
	private String productId;
	
	@Column(name = "product_name", nullable = false)
	private String productName;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "dimension", nullable = false)
	private String dimension;
	
	@Column(name = "weight", nullable = false)
	private double weight;
	
	@Column(name = "mainImage", nullable = false)
	private String mainImage;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "sub_category_id", nullable = false)
	private SubCategory subCategory;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "publisher_id", nullable = false)
	private Publisher publisher;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Image> images;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PriceHistory> priceHistories;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<OrderDetail> orderDetails;
}
