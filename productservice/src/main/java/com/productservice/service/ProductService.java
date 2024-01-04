package com.productservice.service;

import com.productservice.payload.ProductRequest;
import com.productservice.payload.ProductResponse;
import com.productservice.repositories.ProductRepository;

public interface ProductService {

    Long addProduct(ProductRequest productRequest);
    ProductResponse getProductById(Long productId);
    void reduceQuantity(Long productId, Long quantity);
    void deleteProductById(Long productId);
}
