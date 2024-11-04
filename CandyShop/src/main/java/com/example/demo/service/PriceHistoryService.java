package com.example.demo.service;

import com.example.demo.dto.PagedResponseDTO;
import com.example.demo.dto.PriceHistoryRequestDTO;
import com.example.demo.model.PriceHistory;

public interface PriceHistoryService {
	
	public PriceHistory createPriceHistory(String productId, PriceHistoryRequestDTO priceHistoryRequestDTO);

	public PriceHistory updatePriceHistory(String priceHistoryId, PriceHistoryRequestDTO priceHistoryRequestDTO);

	public PriceHistory getPriceHistory(String id);

	public PagedResponseDTO<PriceHistory> getPriceHistoriesByProductId(String productId, int page, int size,
			String sortField, String sortOder);
	
	public void deletePriceHistory(String id);
	
	public PriceHistory getCurrentPriceProductByProductId(String productId);
}
