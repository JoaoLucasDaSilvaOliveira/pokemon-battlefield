package org.pokemonbattlefield.controller.dto;

import org.pokemonbattlefield.model.Ginasio;
import org.pokemonbattlefield.model.Pokemon;
import org.pokemonbattlefield.model.Treinador;

import java.util.List;

public record BatalhaDTO(
        List<Pokemon> pokemons,
        Treinador ganhador,
        Ginasio ginasio
) {

}
