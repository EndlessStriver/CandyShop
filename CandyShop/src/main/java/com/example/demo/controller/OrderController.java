 package com.example.demo.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.OrderPageResponseDTO;
import com.example.demo.dto.OrderRequestDTO;
import com.example.demo.dto.OrderRequestUpdateDTO;
import com.example.demo.dto.PagedResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Order;
import com.example.demo.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	private OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<?> getOrderById(@PathVariable String orderId) {
		Order order = orderService.getOrderById(orderId);
		ApiResponseDTO<Order> response = new ApiResponseDTO<>("Order retrieved successfully", HttpStatus.OK.value(),
				order);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping
	public ResponseEntity<?> getAllOrders(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "0") int limit, @RequestParam(defaultValue = "createdAt") String sortField,
			@RequestParam(defaultValue = "asc") String sortOrder) {
		PagedResponseDTO<OrderPageResponseDTO> orders = orderService.getAllOrders(page, limit, sortField, sortOrder);
		ApiResponseDTO<PagedResponseDTO<OrderPageResponseDTO>> response = new ApiResponseDTO<>(
				"Orders retrieved successfully", HttpStatus.OK.value(), orders);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping
	public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO, BindingResult bindingResult)
			throws ResourceNotFoundException, Exception {
		logger.info("Creating order with request: {}", orderRequestDTO);
		if(bindingResult.hasErrors()) {
			Map<String, Object> errors = new LinkedHashMap<String, Object>();
			bindingResult.getFieldErrors().forEach(error -> {
				errors.put(error.getField(), error.getDefaultMessage());
			});
			ApiResponseDTO<Map<String, Object>> response = new ApiResponseDTO<>("Validation failed", HttpStatus.BAD_REQUEST.value(), errors);
			logger.error("Validation failed: {}", response);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		Order order = orderService.createOrder(orderRequestDTO);
		ApiResponseDTO<Order> response = new ApiResponseDTO<>("Order created successfully", HttpStatus.CREATED.value(),
				order);
		logger.info("Order created: {}", response);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/{orderId}/cancel")
	public ResponseEntity<?> cancelOrder(@PathVariable String orderId) throws ResourceNotFoundException, Exception {
		logger.info("Canceling order with id: " + orderId);
		Order order = orderService.cancelOrder(orderId);
		ApiResponseDTO<Order> response = new ApiResponseDTO<>("Order canceled successfully", HttpStatus.OK.value(),
				order);
		logger.info("Order canceled: {}", response);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/{orderId}/confirm")
	public ResponseEntity<?> confirmOrder(@PathVariable String orderId) throws ResourceNotFoundException, Exception {
		logger.info("Confirming order with id: " + orderId);
		Order order = orderService.confirmOrder(orderId);
		ApiResponseDTO<Order> response = new ApiResponseDTO<>("Order confirmed successfully", HttpStatus.OK.value(),
				order);
		logger.info("Order confirmed: {}", response);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/{orderId}")
	public ResponseEntity<?> updateOrder(@PathVariable String orderId, @Valid @RequestBody OrderRequestUpdateDTO orderRequestUpdateDTO, BindingResult bindingResult)
			throws ResourceNotFoundException, Exception {
		logger.info("Updating order with request: {}", orderRequestUpdateDTO);
		if(bindingResult.hasErrors()) {
			Map<String, Object> errors = new LinkedHashMap<String, Object>();
			bindingResult.getFieldErrors().forEach(error -> {
				errors.put(error.getField(), error.getDefaultMessage());
			});
			ApiResponseDTO<Map<String, Object>> response = new ApiResponseDTO<>("Validation failed", HttpStatus.BAD_REQUEST.value(), errors);
			logger.error("Validation failed: {}", response);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		Order order = orderService.updateOrder(orderId, orderRequestUpdateDTO);
		ApiResponseDTO<Order> response = new ApiResponseDTO<>("Order updated successfully", HttpStatus.OK.value(),
				order);
		logger.info("Order updated: {}", response);
		return ResponseEntity.ok(response);
	}
}
