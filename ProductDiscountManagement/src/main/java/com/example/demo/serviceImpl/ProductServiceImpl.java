package com.example.demo.serviceImpl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.DiscountRequest;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
	
	@Override
	public BigDecimal applyDiscount(DiscountRequest request) {
	    Optional<Product> productOpt = productRepository.findById(request.getProductId());
	    if (productOpt.isEmpty()) {
	        throw new RuntimeException("Product not found");
	    }

	    Product product = productOpt.get();
	    if (product.getQuantity() <= 0) {
	        throw new RuntimeException("Product out of stock");
	    }

	    BigDecimal originalPrice = product.getPrice();
	    BigDecimal discountAmount = BigDecimal.ZERO;

	    // 1. Apply flat or percentage discount
	    if ("percentage".equalsIgnoreCase(request.getDiscountType())) {
	        discountAmount = originalPrice.multiply(request.getDiscountValue()).divide(BigDecimal.valueOf(100));
	    } else if ("flat".equalsIgnoreCase(request.getDiscountType())) {
	        discountAmount = request.getDiscountValue();
	    } else {
	        throw new RuntimeException("Invalid discount type");
	    }

	    // 2. Add seasonal discount if applicable (e.g., 25%)
	    if (request.isSeasonalDiscountActive()) {
	        BigDecimal seasonalDiscount = originalPrice.multiply(BigDecimal.valueOf(25)).divide(BigDecimal.valueOf(100));
	        discountAmount = discountAmount.add(seasonalDiscount);
	    }

	    // 3. Cap the discount to not exceed the product price
	    if (discountAmount.compareTo(originalPrice) > 0) {
	        discountAmount = originalPrice;
	    }

	    // 4. Final price calculation
	    return originalPrice.subtract(discountAmount);
	}

	
//	@Override
//	public BigDecimal applyDiscount(DiscountRequest request) {
//        Optional<Product> productOpt = productRepository.findById(request.getProductId());
//        if (productOpt.isEmpty()) {
//            throw new RuntimeException("Product not found");
//        }
//
//        Product product = productOpt.get();
//        if (product.getQuantity() <= 0) {
//            throw new RuntimeException("Product out of stock");
//        }
//
//        BigDecimal discountAmount = request.getDiscountValue();
//        if ("percentage".equalsIgnoreCase(request.getDiscountType())) {
//            discountAmount = product.getPrice().multiply(discountAmount).divide(BigDecimal.valueOf(100));
//        }
//
//        if (discountAmount.compareTo(product.getPrice()) > 0) {
//            throw new RuntimeException("Discount exceeds product price");
//        }
//
//        return product.getPrice().subtract(discountAmount);
//    }

	@Override
	public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
    }

}
