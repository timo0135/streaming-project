package api.payment.apipayment.dtos;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdatePaymentDto {

    @Nullable
    private BigDecimal amount;

    @Nullable
    private String status;


}
