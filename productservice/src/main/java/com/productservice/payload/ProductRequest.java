package com.productservice.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {

    private String name;
    private Long price;
    private Long quantity;
}
