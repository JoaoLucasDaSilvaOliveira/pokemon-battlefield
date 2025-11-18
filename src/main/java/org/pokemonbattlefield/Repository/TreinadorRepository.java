package org.pokemonbattlefield.Repository;

import org.pokemonbattlefield.model.Treinador;
import org.pokemonbattlefield.model.util.ClasseTreinador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TreinadorRepository extends JpaRepository<Treinador, UUID> {

    boolean existsTreinadorByNomeIgnoreCaseAndClasseTreinador(String nome, ClasseTreinador classe);

    @Query("SELECT DISTINCT t FROM Treinador t " +
            "WHERE (:nome IS NULL OR UPPER(t.nome) LIKE UPPER(CONCAT('%', :nome, '%'))) " +
            "AND (:classeTreinador IS NULL OR t.classeTreinador = :classeTreinador)")
    List<Treinador> findTreinadorByNomeIgnoreCaseOrClasseTreinador(String nome, ClasseTreinador classeTreinador);

    @Query("SELECT DISTINCT t FROM Treinador t JOIN t.pokemons p " +
            "WHERE UPPER(p.nome) LIKE UPPER(CONCAT('%', :nomePokemon, '%'))")
    List<Treinador> findTreinadorByNomePokemon(String nomePokemon);

    boolean existsTreinadorByNomeIgnoreCase(String nome);
}
