package com.productservice.service.impl;

import com.productservice.entities.Product;
import com.productservice.exception.ProductServiceCustomException;
import com.productservice.payload.ProductRequest;
import com.productservice.payload.ProductResponse;
import com.productservice.repositories.ProductRepository;
import com.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Long addProduct(ProductRequest productRequest) {
        log.info("ProductServiceImpl | addProduct is called");

        Product product = Product.builder()
                .productName(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();

        product = productRepository.save(product);

        log.info("ProductServiceImpl | addProduct | Product Created");
        log.info("ProductServiceImpl | addProduct | Product Id : "+ product.getProductId());
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        log.info("ProductServiceImpl | getProductById is called");
        log.info("ProductServiceImpl | getProductById | Get the productId: {}", productId);

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductServiceCustomException("Product with given id not found", "PRODUCT_NOT_FOUND")
        );

        ProductResponse productResponse = new ProductResponse();
        copyProperties(product, productResponse);

        log.info("ProductServiceImpl | getProductById | productResponse :" + productResponse.toString());
        return productResponse;
    }

    @Override
    public void reduceQuantity(Long productId, Long quantity) {
        log.info("Reduce Quantity {} for id: {}", quantity,productId);

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductServiceCustomException("Product with given Id not found",
                        "PRODUCT_NOT_FOUND")
        );

        if(product.getQuantity() < quantity){
            throw new ProductServiceCustomException(  "Product does not have sufficient Quantity",
                    "INSUFFICIENT_QUANTITY");
        }

        product.setQuantity(product.getQuantity()-quantity);
        productRepository.save(product);
        log.info("Product Quantity updated Successfully");
    }

    @Override
    public void deleteProductById(Long productId) {
        log.info("Product id: {}", productId);

        if(!productRepository.existsById(productId)){
            log.info("I m in this loop{} ", !productRepository.existsById(productId));
            throw new ProductServiceCustomException("Product with given with Id: " + productId + " not found:",
                    "PRODUCT_NOT_FOUND");
        }
        log.info("Deleting Product with id: {}", productId);
    }
}
