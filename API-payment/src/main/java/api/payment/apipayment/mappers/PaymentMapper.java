package api.payment.apipayment.mappers;

import api.payment.apipayment.dtos.PaymentDto;
import api.payment.apipayment.entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentDto toDto(Payment payment) {
        if (payment == null) {
            return null;
        }
        PaymentDto dto = new PaymentDto();
        dto.setId(payment.getId());
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        return dto;
    }

    public Payment toEntity(PaymentDto paymentDto) {
        if (paymentDto == null) {
            return null;
        }
        Payment payment = new Payment();
        if (paymentDto.getId() != null) {
            payment.setId(paymentDto.getId());
        }
        payment.setAmount(paymentDto.getAmount());
        payment.setStatus(paymentDto.getStatus());
        return payment;
    }

}
