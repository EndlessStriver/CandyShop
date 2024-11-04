package com.example.demo.service.imp;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.PagedResponseDTO;
import com.example.demo.dto.ProductRequestDTO;
import com.example.demo.dto.ProductRequestUpdateDTO;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Image;
import com.example.demo.model.PriceHistory;
import com.example.demo.model.Product;
import com.example.demo.model.Publisher;
import com.example.demo.model.SubCategory;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import com.example.demo.service.PublisherService;
import com.example.demo.service.S3Service;
import com.example.demo.service.SubCategoryService;

import jakarta.transaction.Transactional;

@Service
public class ProductServiceImp implements ProductService {

	private ProductRepository productRepository;
	private SubCategoryService subCategoryService;
	private PublisherService publisherService;
	private S3Service s3Service;
	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	public ProductServiceImp(ProductRepository productRepository, SubCategoryService subCategoryService,
			PublisherService publisherService, S3Service s3Service) {
		this.productRepository = productRepository;
		this.subCategoryService = subCategoryService;
		this.publisherService = publisherService;
		this.s3Service = s3Service;
	}
	
	@Override
	@Transactional
	public Product createProduct(ProductRequestDTO productRequestDTO) throws IOException, Exception {
		String avatarName = null;
		try {
			Product product = new Product();
			product.setProductName(productRequestDTO.getProductName());
			product.setDescription(productRequestDTO.getDescription());
			product.setDimension(productRequestDTO.getDimension());
			product.setWeight(productRequestDTO.getWeight());
			
			String subCategoryId = productRequestDTO.getSubCategoryId();
			SubCategory subCategory = subCategoryService.getSubCategory(subCategoryId);
			
			String publisherId = productRequestDTO.getPublisherId();
			Publisher publisher = publisherService.getPublisher(publisherId);
			
			product.setSubCategory(subCategory);
			product.setPublisher(publisher);
			
			double price = productRequestDTO.getPrice();
			PriceHistory priceHistory = new PriceHistory();
			priceHistory.setNewPrice(price);
			priceHistory.setPriceChangeReason("Initial price set up for new product");
			priceHistory.setPriceChangeEffectiveDate(LocalDateTime.now());
			priceHistory.setProduct(product);
			
			product.getPriceHistories().add(priceHistory);
			
			MultipartFile mainImage = productRequestDTO.getMainImage();
			avatarName = s3Service.uploadFile(mainImage);
			String avatarUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, "ap-southeast-1",
					avatarName);
			product.setMainImageName(avatarName);
			product.setMainImageUrl(avatarUrl);
			return productRepository.save(product);
		} catch (Exception e) {
			if (avatarName != null)
				s3Service.deleteFile(avatarName);
			throw e;
		}
	}

	@Override
	public Product getProduct(String id) {
		return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
	}

	@Override
	@Transactional
	public Product updateProduct(String id, ProductRequestUpdateDTO productRequestUpdateDTO) {
		Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		if (productRequestUpdateDTO.getProductName() != null) product.setProductName(productRequestUpdateDTO.getProductName());
		if (productRequestUpdateDTO.getDescription() != null) product.setDescription(productRequestUpdateDTO.getDescription());
		if (productRequestUpdateDTO.getDimension() != null) product.setDimension(productRequestUpdateDTO.getDimension());
		if (productRequestUpdateDTO.getWeight() != 0) product.setWeight(productRequestUpdateDTO.getWeight());
		if (productRequestUpdateDTO.getSubCategoryId() != null) {
			SubCategory subCategory = subCategoryService.getSubCategory(productRequestUpdateDTO.getSubCategoryId());
			product.setSubCategory(subCategory);
		}
		if (productRequestUpdateDTO.getPublisherId() != null) {
			Publisher publisher = publisherService.getPublisher(productRequestUpdateDTO.getPublisherId());
			product.setPublisher(publisher);
		}
		return productRepository.save(product);
	}

	@Override
	@Transactional
	public void deleteProduct(String id) throws Exception {
		try {
			Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
			s3Service.deleteFile(product.getMainImageName());
			for (Image image : product.getImages()) {
				s3Service.deleteFile(image.getImageTitle());
			}
			productRepository.delete(product);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public PagedResponseDTO<Product> getProducts(int page, int limit, String sortField, String sortOrder) {
		Sort sort = sortOrder.equalsIgnoreCase("desc") ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
		Pageable pageable = PageRequest.of(page, limit, sort);
		Page<Product> pageProduct = productRepository.findAll(pageable);
		PagedResponseDTO<Product> pagedResponseDTO = new PagedResponseDTO<>();
		pagedResponseDTO.setContent(pageProduct.getContent());
		pagedResponseDTO.setTotalElements(pageProduct.getTotalElements());
		pagedResponseDTO.setTotalPages(pageProduct.getTotalPages());
		pagedResponseDTO.setPageNumber(pageProduct.getNumber());
		pagedResponseDTO.setPageSize(pageProduct.getSize());
		return pagedResponseDTO;
	}

	@Override
	@Transactional
	public Product updateProductMainImage(String id, MultipartFile mainImage) throws Exception {
		String avatarName = null;
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		try {
			avatarName = s3Service.uploadFile(mainImage);
			String avatarUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, "ap-southeast-1", avatarName);
			s3Service.deleteFile(product.getMainImageName());
			product.setMainImageName(avatarName);
			product.setMainImageUrl(avatarUrl);
		} catch (Exception e) {
			if (avatarName != null) s3Service.deleteFile(avatarName);
			throw e;
		}
		return productRepository.save(product);
	}

	@Override
	@Transactional
	public Product updateProductImages(String id, MultipartFile[] images) throws IOException, Exception {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		if (product.getImages().size() + images.length > 5) throw new BadRequestException("Maximum 5 images are allowed");
		
		List<Image> imageList = new ArrayList<Image>();
		try {
			for (MultipartFile image : images) {
				String imageName = s3Service.uploadFile(image);
				String imageUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, "ap-southeast-1",
						imageName);
				Image imageObj = new Image();
				imageObj.setImageTitle(imageName);
				imageObj.setUrl(imageUrl);
				imageObj.setProduct(product);
				imageList.add(imageObj);
			}
			product.getImages().addAll(imageList);
			product = productRepository.save(product);
		} catch (Exception e) {
			for (Image image : imageList) {
				s3Service.deleteFile(image.getImageTitle());
			}
			throw e;
		}
		return product;
	}

}
