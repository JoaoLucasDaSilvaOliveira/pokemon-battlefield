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
    @JoinTable(name = "batalha_pokemons")
    private List<Pokemon> pokemonsBatalha;

    @OneToOne(mappedBy = "id_treinador_vencedor")
    @JoinTable(name = "batalha")
    private Treinador ganhador;

    @OneToOne
    @JoinTable(name = "ginasio")
    private Ginasio ginasio;

}
