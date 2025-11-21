package org.pokemonbattlefield.controller.dto.ApiPokemon;

import org.pokemonbattlefield.controller.dto.PokemonExternoDTO;

import java.util.List;

// Em PokemonListaResponseDTO.java
public record PokemonListaResponseDTO(
        int count,
        String next,
        String previous,
        List<PokemonExternoDTO> results
) {}