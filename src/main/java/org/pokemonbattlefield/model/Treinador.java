package org.pokemonbattlefield.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.pokemonbattlefield.model.util.ClasseTreinador;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table
@Data
@DynamicInsert
public class Treinador {

    public Treinador (){}

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    @Column(name="level_experiencia")
    private Integer levelExperiencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "classe_treinador")
    private ClasseTreinador classeTreinador;

    @OneToMany(mappedBy = "treinador")
    @JsonManagedReference
    private List<Pokemon> pokemons;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Treinador treinador)) return false;
        return Objects.equals(nome, treinador.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nome);
    }
}
