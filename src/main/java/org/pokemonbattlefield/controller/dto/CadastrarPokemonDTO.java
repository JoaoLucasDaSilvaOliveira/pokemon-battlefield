package org.pokemonbattlefield.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastrarPokemonDTO(
        @NotBlank( message = "Forneça o id do treinador que deseja atrelar o pokémon")
        String idTreinador,
        @NotNull(message = "Forneça o id do pokémon que deseja cadastrar")
        Integer idPokemon
) {
}
