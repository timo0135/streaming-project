package api.payment.apipayment.services;

import api.payment.apipayment.dtos.PaymentDto;
import api.payment.apipayment.dtos.UpdatePaymentDto;

import java.util.List;

public interface PaymentService {

    PaymentDto savePayment(PaymentDto paymentDto);

    PaymentDto getPaymentById(Long paymentId);

    boolean deletePayment(Long paymentId);

    List<PaymentDto> getAllPayments();

    PaymentDto updatePayment(UpdatePaymentDto updatePaymentDto, Long paymentId);

}
