package org.pokemonbattlefield.model;

import javax.persistence.*;

import java.util.UUID;

@Entity
@Table
public class Ginasio {

    public Ginasio (){}

    @Id
    private UUID id;

    private String nome;

    private Integer capacidade;

    private String bairro;

    @Column(name = "background_image")
    private String backgroundImage;

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

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

}
