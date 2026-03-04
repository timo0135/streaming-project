package api.payment.apipayment.controllers;

import api.payment.apipayment.dtos.DisplayResponseDto;
import api.payment.apipayment.dtos.PaymentDto;
import api.payment.apipayment.dtos.UpdatePaymentDto;
import api.payment.apipayment.services.impl.PaymentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentServiceImpl paymentService;
    public PaymentController(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public DisplayResponseDto<List<PaymentDto>> getPayments() {
        DisplayResponseDto<List<PaymentDto>> response = new DisplayResponseDto<>();
        List<PaymentDto> payments = paymentService.getAllPayments();
        response.setMessage("Payments retrieved successfully");
        response.setType("collection");
        response.setData(payments);
        return response;
    }

    @GetMapping("/{id}")
    public DisplayResponseDto<PaymentDto> getPayment(@PathVariable Long id){
        DisplayResponseDto<PaymentDto> response = new DisplayResponseDto<>();
        PaymentDto payment = paymentService.getPaymentById(id);
        response.setMessage("Payment retrieved successfully");
        response.setType("item");
        response.setData(payment);
        return response;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DisplayResponseDto<PaymentDto> savePayment(@Valid @RequestBody PaymentDto paymentDto){
          DisplayResponseDto<PaymentDto> response = new DisplayResponseDto<>();
          PaymentDto savedPayment = paymentService.savePayment(paymentDto);
          response.setMessage("Payment created successfully");
          response.setType("item");
          response.setData(savedPayment);
          return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean deletePayment(@PathVariable Long id){
        Boolean result = paymentService.deletePayment(id);
        if (!result) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found with id: " + id);
        }
        return result;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public DisplayResponseDto<PaymentDto> updatePayment(@Valid @RequestBody UpdatePaymentDto updatePaymentDto, @PathVariable Long id){
        DisplayResponseDto<PaymentDto> response = new DisplayResponseDto<>();
        PaymentDto updatedPayment = paymentService.updatePayment(updatePaymentDto, id);
        response.setMessage("Payment updated successfully");
        response.setType("item");
        response.setData(updatedPayment);
        return response;
    }
}
