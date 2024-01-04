package com.orderservice.service;

import com.orderservice.payload.OrderRequest;
import com.orderservice.payload.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}