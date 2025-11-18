package org.pokemonbattlefield.controller.dto;

import java.util.UUID;

public record DetalhesPokemonDTO(

        UUID id,
        String nome,
        String tipo

) {
}
