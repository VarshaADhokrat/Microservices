package com.paymentservice.service.impl;

import com.paymentservice.entities.TransactionDetails;
import com.paymentservice.exception.PaymentServiceCustomException;
import com.paymentservice.payload.PaymentRequest;
import com.paymentservice.payload.PaymentResponse;
import com.paymentservice.repository.TransactionDetailsRepository;
import com.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final TransactionDetailsRepository transactionDetailsRepository;

    @Override
    public Long doPayment(PaymentRequest paymentRequest) {
        log.info("PaymentServiceImpl | doPayment is called");

        log.info("PaymentServiceImpl | doPayment | Recording Payment Details: {}", paymentRequest);

        TransactionDetails transactionDetails = TransactionDetails.builder()
                .orderId(paymentRequest.getOrderId())
                .paymentMode(paymentRequest.getPaymentMode())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .paymentDate(Instant.now())
                .paymentStatus("SUCCESS")
                .amount(paymentRequest.getAmount())
                .build();

        transactionDetails = transactionDetailsRepository.save(transactionDetails);

        log.info("Transaction Completed with Id: {}", transactionDetails.getId());

        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(Long orderId) {
        log.info("PaymentServiceImpl | getPaymentDetailsByOrderId is called");

        log.info("PaymentServiceImpl | getPaymentDetailsByOrderId | Getting payment details for the Order Id: {}", orderId);

        TransactionDetails transactionalDetails = transactionDetailsRepository.findByOrderId(orderId).orElseThrow(() ->
                new PaymentServiceCustomException("TransactionDetails with given id not found",
                        "TRANSACTION_NOT_FOUND"));


        PaymentResponse paymentResponse = PaymentResponse.builder()
                .paymentId(transactionalDetails.getId())
                .paymentMode(transactionalDetails.getPaymentMode())
                .paymentDate(transactionalDetails.getPaymentDate())
                .orderId(transactionalDetails.getOrderId())
                .status(transactionalDetails.getPaymentStatus())
                .amount(transactionalDetails.getAmount())
                .build();

        log.info("PaymentServiceImpl | getPaymentDetailsByOrderId | paymentResponse: {}", paymentResponse.toString());

        return paymentResponse;
    }
}
