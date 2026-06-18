package org.pokemonbattlefield.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record CadastrarPokemonDTO(
        @NotBlank( message = "Forneça o id do treinador que deseja atrelar o pokémon")
        String idTreinador,
        @NotNull(message = "Forneça o id do pokémon que deseja cadastrar")
        Integer idPokemon
) {
}
