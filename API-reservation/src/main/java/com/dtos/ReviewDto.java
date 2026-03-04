package com.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReviewDto {

    @Nullable
    private Long id;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private int note;

    private String commentaire;
}
