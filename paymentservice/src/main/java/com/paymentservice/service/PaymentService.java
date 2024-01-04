package com.paymentservice.service;

import com.paymentservice.payload.PaymentRequest;
import com.paymentservice.payload.PaymentResponse;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(Long orderId);
}
