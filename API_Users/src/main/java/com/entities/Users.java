package com.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data
public class Users {

    private String pseudo;
    private String nom;
    private String prenom;
    private String motDePasse;
    private String adresse;
    private Integer age;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean statut;
    private Integer idReservation;

    @ElementCollection
    private List<Integer> idReservations = new ArrayList<>();
}
