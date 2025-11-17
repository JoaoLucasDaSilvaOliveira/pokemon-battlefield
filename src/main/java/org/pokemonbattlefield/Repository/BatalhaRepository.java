package org.pokemonbattlefield.Repository;

import org.pokemonbattlefield.model.Batalha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BatalhaRepository extends JpaRepository<Batalha, UUID> {

}
