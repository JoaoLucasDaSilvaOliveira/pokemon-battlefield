package org.pokemonbattlefield.Repository;

import org.pokemonbattlefield.model.Batalha;
import org.pokemonbattlefield.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BatalhaRepository extends JpaRepository<Batalha, UUID> {

    @Query(
            "SELECT COUNT (b) > 0 FROM Batalha b JOIN b.pokemonsBatalha p WHERE p.id = ?1"
    )
    boolean existsBatalhaByPokemonId(Integer id);

    Integer countBatalhaByGanhador_Id(UUID ganhadorId);

    Integer countDistinctByPokemonsBatalha_Treinador_Id(UUID id);

    List<Batalha> findBatalhaByPokemonsBatalha_Treinador_Id(UUID idTreinadorSanitizado);
}
