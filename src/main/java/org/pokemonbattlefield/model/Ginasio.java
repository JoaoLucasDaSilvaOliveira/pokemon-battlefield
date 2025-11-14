package org.pokemonbattlefield.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table
@Data
public class Ginasio {

    public Ginasio (){}

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    private Integer capacidade;

    private String bairro;

}
