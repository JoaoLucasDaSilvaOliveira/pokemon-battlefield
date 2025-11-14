package org.pokemonbattlefield.model;

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

    @ManyToMany(mappedBy = "id_pokemon")
    @JoinTable(
            name = "batalha_pokemons",
            joinColumns = @JoinColumn(name = "id_batalha"),
            inverseJoinColumns = @JoinColumn(name = "id_pokemon")
    )
    private List<Pokemon> pokemonsBatalha;

    @ManyToOne
    @JoinColumn(name = "id_treinador_vencedor")
    private Treinador ganhador;

    @OneToOne
    @JoinColumn(name = "id_ginasio")
    private Ginasio ginasio;

}
