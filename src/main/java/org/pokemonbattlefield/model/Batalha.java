package org.pokemonbattlefield.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
public class Batalha {

    public Batalha (){}

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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

}
