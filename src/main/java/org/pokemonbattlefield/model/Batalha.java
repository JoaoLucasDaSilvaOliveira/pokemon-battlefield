package org.pokemonbattlefield.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table
public class Batalha {

    public Batalha (){}

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToMany
    @JoinTable(
            name = "batalha_pokemons",
            joinColumns = @JoinColumn(name = "id_batalha"),
            inverseJoinColumns = @JoinColumn(name = "id_pokemon")
    )
    @JsonBackReference
    private List<Pokemon> pokemonsBatalha;

    @ManyToOne
    @JoinColumn(name = "id_treinador_vencedor")
    @JsonIgnoreProperties({"pokemons"})
    private Treinador ganhador;

//    @JsonIgnoreProperties({"treinador"})
//    @JsonProperty("pokemonsVencedores")
//    public List<Pokemon> pokemonsDoVencedor (){
//        //todo: implementar mais p frente
//    }

    @OneToOne
    @JoinColumn(name = "id_ginasio")
    private Ginasio ginasio;

    public Batalha(UUID id, List<Pokemon> pokemonsBatalha, Treinador ganhador, Ginasio ginasio) {
        this.id = id;
        this.pokemonsBatalha = pokemonsBatalha;
        this.ganhador = ganhador;
        this.ginasio = ginasio;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Pokemon> getPokemonsBatalha() {
        return pokemonsBatalha;
    }

    public void setPokemonsBatalha(List<Pokemon> pokemonsBatalha) {
        this.pokemonsBatalha = pokemonsBatalha;
    }

    public Treinador getGanhador() {
        return ganhador;
    }

    public void setGanhador(Treinador ganhador) {
        this.ganhador = ganhador;
    }

    public Ginasio getGinasio() {
        return ginasio;
    }

    public void setGinasio(Ginasio ginasio) {
        this.ginasio = ginasio;
    }

}
