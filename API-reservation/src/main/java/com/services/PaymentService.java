package com.services;


import com.dtos.PaymentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Arrays;
import java.util.List;

@Service
public class PaymentService {

    @Value("${payment.api.url}")
    private String paymentApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // GET /payments
    public List<PaymentDto> getAllPayments() {
        PaymentDto[] payments = restTemplate.getForObject(
                paymentApiUrl + "/payments", PaymentDto[].class);
        assert payments != null;
        return Arrays.asList(payments);
    }

    // GET /payments/{id}
    public PaymentDto getPaymentById(int id) {
        return restTemplate.getForObject(
                paymentApiUrl + "/payments/" + id, PaymentDto.class);
    }
}
