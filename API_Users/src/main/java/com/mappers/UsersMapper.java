package src.main.java.com.mappers;

import src.main.java.com.dtos.UsersDto;
import src.main.java.com.entities.Users;
import org.springframework.stereotype.Component;

/**
 * Mapper responsable de la conversion entre les entités user et les DTOs UsersDto.
 * Un mapper permet de séparer la couche de persistance de la couche de présentation.
 *
 * Points clés du pattern Mapper :
 * - Conversion bidirectionnelle entre DTO et Entity
 * - Gestion des null-safety
 * - Pas de logique métier, uniquement de la transformation
 */
@Component
public class UsersMapper {

    /**
     * Convertit une entité user en DTO UsersDto
     * Cette méthode est utilisée pour exposer les données aux clients de l'API
     *
     * @param users l'entité à convertir
     * @return le DTO correspondant ou null si l'entité est null
     */
    public UsersDto toDto(Users users) {
        if (users == null) {
            return null;
        }

        UsersDto usersDto = new UsersDto();
        usersDto.setPseudo(users.getPseudo());
        usersDto.setNom(users.getNom());
        usersDto.setPrenom(users.getPrenom());
        usersDto.setAge(users.getAge());
        usersDto.setMotDePasse(users.getMotDePasse());
        usersDto.setStatut(users.getStatut());
        usersDto.setAdresse(users.getAdresse());
        usersDto.setId(users.getId());
        return usersDto;
    }

    /**
     * Convertit un DTO UsersDto en entité user
     * Cette méthode est utilisée pour persister les données reçues des clients
     *
     * @param usersDto le DTO à convertir
     * @return l'entité correspondante ou null si le DTO est null
     */
    public Users toEntity(UsersDto usersDto) {
        if (usersDto == null) {
            return null;
        }

        Users users = new Users();
        // On ne set l'ID que s'il existe (cas d'une mise à jour)
        if (usersDto.getId() != null) {
            users.setId(usersDto.getId());
        }
        users.setPseudo(usersDto.getPseudo());
        users.setNom(usersDto.getNom());
        users.setPrenom(usersDto.getPrenom());
        users.setAge(usersDto.getAge());
        users.setMotDePasse(usersDto.getMotDePasse());
        users.setStatut(usersDto.getStatut());
        users.setAdresse(usersDto.getAdresse());
        return users;
    }
}