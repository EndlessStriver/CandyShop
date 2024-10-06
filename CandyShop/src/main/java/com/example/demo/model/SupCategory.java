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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sup_category")
public class SupCategory {
	
	@Id
	@Column(name = "sup_category_id")
	private String supCategoryId;
	
	@Column(name = "sup_category_name", nullable = false, unique = true)
	private String supCategoryName;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "product_id")
	private Product product;
}
