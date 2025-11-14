package org.pokemonbattlefield.model;

import jakarta.persistence.*;
import lombok.Data;
import org.pokemonbattlefield.model.util.ClasseTreinador;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
public class Treinador {

    public Treinador (){}

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    @Column(name="level_experiencia")
    private Integer levelExperiencia;

    @Column(name = "classetreinador")
    private ClasseTreinador classeTreinador;

    @OneToMany(mappedBy = "treinador")
    private List<Pokemon> pokemons;

}
