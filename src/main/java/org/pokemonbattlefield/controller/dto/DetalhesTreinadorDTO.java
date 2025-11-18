package org.pokemonbattlefield.controller.dto;

import org.pokemonbattlefield.model.Pokemon;
import org.pokemonbattlefield.model.util.ClasseTreinador;

import java.util.List;
import java.util.UUID;

public record DetalhesTreinadorDTO(

        UUID id,
        String nome,
        Integer levelExperiencia,
        ClasseTreinador classeTreinador,
        List<DetalhesPokemonDTO> pokemons

) { }
