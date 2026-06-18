package org.pokemonbattlefield.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.pokemonbattlefield.model.util.ClasseTreinador;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table
@DynamicInsert
public class Treinador {

    public Treinador (){}

    @Id
    @GeneratedValue(generator = "UUID")
    @org.hibernate.annotations.GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getLevelExperiencia() {
        return levelExperiencia;
    }

    public void setLevelExperiencia(Integer levelExperiencia) {
        this.levelExperiencia = levelExperiencia;
    }

    public ClasseTreinador getClasseTreinador() {
        return classeTreinador;
    }

    public void setClasseTreinador(ClasseTreinador classeTreinador) {
        this.classeTreinador = classeTreinador;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

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
