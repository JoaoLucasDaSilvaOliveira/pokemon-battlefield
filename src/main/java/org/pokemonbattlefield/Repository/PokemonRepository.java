package org.pokemonbattlefield.Repository;

import org.pokemonbattlefield.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PokemonRepository extends JpaRepository<Pokemon, UUID> {
    @Query(
        "SELECT COUNT(p) > 0 FROM Pokemon p WHERE p.treinador.id = ?1 "
    )
    boolean existsPokemonByTreinadorId(UUID idAutor);
}
