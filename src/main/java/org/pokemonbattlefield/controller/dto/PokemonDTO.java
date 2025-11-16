package org.pokemonbattlefield.controller.dto;

public record PokemonDTO (
        String nomePokemon,
        Integer pontosVida,
        Integer pontosAtaque,
        Integer pontosDefesa,
        String tipo
) {
}
