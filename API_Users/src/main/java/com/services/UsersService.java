package com.services;

import com.dtos.UsersDto;

import java.util.List;

/**
 * Interface définissant les opérations métier disponibles pour la gestion des user.
 * Cette interface suit le principe d'Interface Segregation (SOLID).
 */
public interface UsersService {
    /**
     * Sauvegarde un user dans le système
     * @param usersDto les données du user à sauvegarder
     * @return le user sauvegardé avec son ID généré
     */
    UsersDto saveUser(UsersDto usersDto);

    /**
     * Récupère un chien par son identifiant
     * @param userId l'identifiant du user recherché
     * @return le user trouvé
     * @throws jakarta.persistence.EntityNotFoundException si le user n'existe pas
     */
    UsersDto getUserById(Long userId);

    /**
     * Supprime un chien du système
     * @param userId l'identifiant du user à supprimer
     * @return true si la suppression a réussi
     */
    boolean deleteUser(Long userId);

    /**
     * Récupère tous les users du système
     * @return la liste des users
     */
    List<UsersDto> getAllUser();
}
