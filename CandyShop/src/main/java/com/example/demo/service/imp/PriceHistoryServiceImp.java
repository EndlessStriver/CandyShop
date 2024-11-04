package com.example.demo.service.imp;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PagedResponseDTO;
import com.example.demo.dto.PriceHistoryRequestDTO;
import com.example.demo.exception.ResourceConflictException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.PriceHistory;
import com.example.demo.model.Product;
import com.example.demo.repository.PriceHistoryRepository;
import com.example.demo.service.PriceHistoryService;
import com.example.demo.service.ProductService;

import jakarta.transaction.Transactional;

@Service
public class PriceHistoryServiceImp implements PriceHistoryService{
	
	private PriceHistoryRepository priceHistoryRepository;
	private ProductService productService;
	
	public PriceHistoryServiceImp(PriceHistoryRepository priceHistoryRepository, ProductService productService) {
		this.priceHistoryRepository = priceHistoryRepository;
		this.productService = productService;
	}

	@Override
	@Transactional
	public PriceHistory createPriceHistory(String productId, PriceHistoryRequestDTO priceHistoryRequestDTO) {
		Product product = productService.getProduct(productId);
		PriceHistory priceHistory = new PriceHistory();
		priceHistory.setProduct(product);
		priceHistory.setNewPrice(priceHistoryRequestDTO.getNewPrice());
		priceHistory.setPriceChangeReason(priceHistoryRequestDTO.getPriceChangeReason());
		priceHistory.setPriceChangeEffectiveDate(priceHistoryRequestDTO.getPriceChangeEffectiveDate());
		return priceHistoryRepository.save(priceHistory);
	}

	@Override
	@Transactional
	public PriceHistory updatePriceHistory(String priceHistoryId, PriceHistoryRequestDTO priceHistoryRequestDTO) {
		PriceHistory priceHistory = priceHistoryRepository.findById(priceHistoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Price history not found"));
		if (priceHistory.getPriceChangeEffectiveDate().isBefore(LocalDateTime.now()))
			throw new ResourceConflictException("Price change effective date must be after the current effective date");
		if (priceHistoryRequestDTO.getNewPrice() != 0)
			priceHistory.setNewPrice(priceHistoryRequestDTO.getNewPrice());
		if (priceHistoryRequestDTO.getPriceChangeReason() != null)
			priceHistory.setPriceChangeReason(priceHistoryRequestDTO.getPriceChangeReason());
		if (priceHistoryRequestDTO.getPriceChangeEffectiveDate() != null)
			priceHistory.setPriceChangeEffectiveDate(priceHistoryRequestDTO.getPriceChangeEffectiveDate());
		return priceHistoryRepository.save(priceHistory);
	}

	@Override
	public PriceHistory getPriceHistory(String id) {
		PriceHistory priceHistory = priceHistoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Price history not found"));
		return priceHistory;
	}

	@Override
	public PagedResponseDTO<PriceHistory> getPriceHistoriesByProductId(String productId, int page, int size,
			String sortField, String sortOder) {
		
		Sort sort = sortOder.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<PriceHistory> pagePriceHistory =  priceHistoryRepository.findAll(pageable);
		
		PagedResponseDTO<PriceHistory> pagedResponseDTO = new PagedResponseDTO<PriceHistory>();
		pagedResponseDTO.setContent(pagePriceHistory.getContent());
		pagedResponseDTO.setTotalPages(pagePriceHistory.getTotalPages());
		pagedResponseDTO.setTotalElements(pagePriceHistory.getTotalElements());
		pagedResponseDTO.setPageSize(pagePriceHistory.getSize());
		pagedResponseDTO.setPageNumber(pagePriceHistory.getNumber());
		
		return pagedResponseDTO;
	}

	@Override
	@Transactional
	public void deletePriceHistory(String id) {
		PriceHistory priceHistory = priceHistoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Price history not found"));
		if (priceHistory.getPriceChangeEffectiveDate().isBefore(LocalDateTime.now()))
			throw new ResourceConflictException("Price change effective date must be after the current effective date");
		priceHistoryRepository.delete(priceHistory);
	}

	@Override
	public PriceHistory getCurrentPriceProductByProductId(String productId) {
		return priceHistoryRepository.findCurrentPriceProductByProduct(productId, LocalDateTime.now())
				.orElseThrow(() -> new ResourceNotFoundException("Price history not found"));
	}

}
