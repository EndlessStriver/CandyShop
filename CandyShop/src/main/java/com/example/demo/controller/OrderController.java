package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.example.demo.dto.PagedResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Order;
import com.example.demo.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

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
	public ResponseEntity<?> createOrder(@RequestBody OrderRequestDTO orderRequestDTO)
			throws ResourceNotFoundException, Exception {
		Order order = orderService.createOrder(orderRequestDTO);
		ApiResponseDTO<Order> response = new ApiResponseDTO<>("Order created successfully", HttpStatus.CREATED.value(),
				order);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/{orderId}/cancel")
	public ResponseEntity<?> cancelOrder(@PathVariable String orderId) throws ResourceNotFoundException, Exception {
		Order order = orderService.cancelOrder(orderId);
		ApiResponseDTO<Order> response = new ApiResponseDTO<>("Order canceled successfully", HttpStatus.OK.value(),
				order);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/{orderId}/confirm")
	public ResponseEntity<?> confirmOrder(@PathVariable String orderId) throws ResourceNotFoundException, Exception {
		Order order = orderService.confirmOrder(orderId);
		ApiResponseDTO<Order> response = new ApiResponseDTO<>("Order confirmed successfully", HttpStatus.OK.value(),
				order);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/{orderId}")
	public ResponseEntity<?> updateOrder(@PathVariable String orderId, @RequestBody OrderRequestDTO orderRequestDTO)
			throws ResourceNotFoundException, Exception {
		Order order = orderService.updateOrder(orderId, orderRequestDTO);
		ApiResponseDTO<Order> response = new ApiResponseDTO<>("Order updated successfully", HttpStatus.OK.value(),
				order);
		return ResponseEntity.ok(response);
	}
}
