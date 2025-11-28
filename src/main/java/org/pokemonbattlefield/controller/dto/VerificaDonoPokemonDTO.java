package org.pokemonbattlefield.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record VerificaDonoPokemonDTO(
    @NotNull(message = "Por favor, forneça a lista de pokemons a verificar")
    List<Integer> pokemonsIds
) {
}
