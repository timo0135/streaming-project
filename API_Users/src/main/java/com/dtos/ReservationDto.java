package com.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationDto {

    @NotNull
    private Integer id;
    @NotNull
    private String description;
    @NotNull
    private String dateDebut;
    @NotNull
    private String dateFin;
}
