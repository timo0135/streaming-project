package com.mappers;

import com.dtos.ReservationDto;
import com.entities.Reservation;
import org.springframework.stereotype.Component;

/**
 * Mapper responsable de la conversion entre les entités Dog et les DTOs DogDto.
 * Un mapper permet de séparer la couche de persistance de la couche de présentation.
 * Points clés du pattern Mapper :
 * - Conversion bidirectionnelle entre DTO et Entity
 * - Gestion des null-safety
 * - Pas de logique métier, uniquement de la transformation
 */
@Component
public class ReservationMapper {

    /**
     * Convertit une entité Dog en DTO DogDto
     * Cette méthode est utilisée pour exposer les données aux clients de l'API
     * 
     * @param reservation l'entité à convertir
     * @return le DTO correspondant ou null si l'entité est null
     */
    public ReservationDto toDto(Reservation reservation) {
        if (reservation == null) {
            return null;
        }

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());

        reservationDto.setDescription(reservation.getDescription());

        reservationDto.setDate_debut(reservation.getDate_debut());
        reservationDto.setDate_fin(reservation.getDate_fin());
        reservationDto.setMovieId(reservation.getMovieId());
        reservationDto.setUserId(reservation.getUserId());
        reservationDto.setEvaluationId(reservation.getEvaluationId());
        reservationDto.setPaiementId(reservation.getPaiementId());

        return reservationDto;
    }

    /**
     * Convertit un DTO DogDto en entité Dog
     * Cette méthode est utilisée pour persister les données reçues des clients
     * Note: La date de naissance n'est pas dans le DTO mais est présente dans l'entité
     * 
     * @param reservationDto le DTO à convertir
     * @return l'entité correspondante ou null si le DTO est null
     */
    public Reservation toEntity(ReservationDto reservationDto) {
        if (reservationDto == null) {
            return null;
        }

        Reservation reservation = new Reservation();
        // On ne set l'ID que s'il existe (cas d'une mise à jour)
        if (reservationDto.getId() != null) {
            reservation.setId(reservationDto.getId());
        }

        reservation.setDescription(reservationDto.getDescription());
        reservation.setDate_debut(reservationDto.getDate_debut());
        reservation.setDate_fin(reservationDto.getDate_fin());
        reservation.setMovieId(reservationDto.getMovieId());
        reservation.setUserId(reservationDto.getUserId());
        reservation.setEvaluationId(reservationDto.getEvaluationId());
        reservation.setPaiementId(reservationDto.getPaiementId());

        return reservation;
    }
} 