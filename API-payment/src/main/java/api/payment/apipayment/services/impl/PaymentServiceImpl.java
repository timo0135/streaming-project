package api.payment.apipayment.services.impl;

import api.payment.apipayment.dtos.PaymentDto;
import api.payment.apipayment.dtos.UpdatePaymentDto;
import api.payment.apipayment.mappers.PaymentMapper;
import api.payment.apipayment.repositories.PaymentRepository;
import api.payment.apipayment.services.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service("paymentService")
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }


    @Override
    public PaymentDto savePayment(PaymentDto paymentDto) {
        var paymentEntity = paymentMapper.toEntity(paymentDto);
        var savedPayment = paymentRepository.save(paymentEntity);
        return paymentMapper.toDto(savedPayment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentDto getPaymentById(Long paymentId) {
        var paymentEntity = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found with id: " + paymentId));
        return paymentMapper.toDto(paymentEntity);
    }

    @Override
    public boolean deletePayment(Long paymentId) {
        if (!paymentRepository.existsById(paymentId)) {
            return false;
        }
        paymentRepository.deleteById(paymentId);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDto> getAllPayments() {
        var paymentEntities = paymentRepository.findAll();
        return paymentEntities.stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Override
    public PaymentDto updatePayment(UpdatePaymentDto updatePaymentDto, Long paymentId) {
        var existingPayment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found with id: " + paymentId));

        if(updatePaymentDto.getAmount() == null && updatePaymentDto.getStatus() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one field (amount or status) must be provided for update");
        }
        if (updatePaymentDto.getAmount() != null) {
            existingPayment.setAmount(updatePaymentDto.getAmount());
        }
        if (updatePaymentDto.getStatus() != null) {
            existingPayment.setStatus(updatePaymentDto.getStatus());
        }
        var updatedPayment = paymentRepository.save(existingPayment);
        return paymentMapper.toDto(updatedPayment);
    }
}
