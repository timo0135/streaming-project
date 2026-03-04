package com.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDto {

    @Nullable
    private Long id;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotBlank(message = "Status cannot be blank")
    private String status;
}
