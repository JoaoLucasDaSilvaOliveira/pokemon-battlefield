package org.pokemonbattlefield.Repository;

import org.pokemonbattlefield.model.Treinador;
import org.pokemonbattlefield.model.util.ClasseTreinador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TreinadorRepository extends JpaRepository<Treinador, UUID> {

    boolean existsTreinadorByNomeIgnoreCaseAndClasseTreinadorIgnoreCase(String nome, ClasseTreinador classe);
}
