package src.main.java.com.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UsersDto {

    @NotBlank(message = "Le pseudo est obligatoire")
    private String pseudo;
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDePasse;
    @NotBlank(message = "L'adresse est obligatoire")
    private String adresse;
    @NotBlank(message = "Le statut est obligatoire")
    private Boolean statut;
    @NotNull
    private Integer age;
    @NotNull
    private Integer id;
    private List<Integer> idReservations = new ArrayList<>();
}
