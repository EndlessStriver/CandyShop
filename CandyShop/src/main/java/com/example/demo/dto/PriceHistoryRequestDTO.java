package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceHistoryRequestDTO {
	private double newPrice;
	private String priceChangeReason;
	private LocalDateTime priceChangeEffectiveDate;
}
