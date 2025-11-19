package org.pokemonbattlefield.controller.dto;

import org.pokemonbattlefield.model.util.AcaoPokemon;
import org.pokemonbattlefield.model.util.EvolucaoPokemon;

import java.util.List;

public record PokemonExternoDTO (
        String nome,
        Integer pontosVida,
        Integer pontosAtaque,
        Integer pontosDefesa,
        EvolucaoPokemon evolucao,
        List<AcaoPokemon> acoesPokemon,
        String tipo
) {
}
