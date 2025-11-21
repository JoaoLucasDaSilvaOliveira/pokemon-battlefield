package org.pokemonbattlefield.Repository;

import org.pokemonbattlefield.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
    @Query(
        "SELECT COUNT(p) > 0 FROM Pokemon p WHERE p.treinador.id = ?1 "
    )
    boolean existsPokemonByTreinadorId(UUID idTreinador);

    @Query(
            "SELECT COUNT(t) > 0 FROM Treinador t JOIN t.pokemons p WHERE p.id= ?1"
    )
    boolean existsPokemonComTreinador(Integer idPokemon);
}
