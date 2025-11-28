package org.pokemonbattlefield.controller.dto;

import org.pokemonbattlefield.model.util.AcaoPokemon;
import org.pokemonbattlefield.model.util.EvolucaoPokemon;

import java.util.List;
import java.util.Map;

public record PokemonExternoDTO (
        Integer id,
        String nome,
        Integer pontosVida,
        Integer pontosAtaque,
        Integer pontosDefesa,
        EvolucaoPokemon evolucao,
        List<AcaoPokemon> acoesPokemon,
        Map<String, String> sprites,
        String tipo
) {
}
