package org.pokemonbattlefield.controller.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import org.pokemonbattlefield.model.Ginasio;
import org.pokemonbattlefield.model.Pokemon;
import org.pokemonbattlefield.model.Treinador;

import java.util.List;
import java.util.UUID;

public record BatalhaDTO(
        @NotNull(message = "Por favor, forneça os ids dos Pokemons que batalharam (mínimo 2)")
        List<Integer> idPokemons,
        @NotNull(message = "Por favor, forneça o id do treinador ganhador")
        String idTreinadorGanhador,
        @NotNull(message = "Por favor, forneça o id do ginasio")
        String idGinasio
) {

}
