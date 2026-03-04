package com.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MovieDto {

    @Nullable
    private Long id;

    private String titre;
}
