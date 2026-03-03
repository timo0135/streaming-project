package com.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationDto {
	
	@NotNull
	private Long id;
	
	private String description;

	private String date_debut;

	private String date_fin;

	private int movieId;

	private int evaluationId;

	private int userId;

	private int paiementId;
	
}
