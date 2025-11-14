package org.pokemonbattlefield.Repository;

import org.pokemonbattlefield.model.Ginasio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GinasioRepository extends JpaRepository<Ginasio, UUID> {

}
