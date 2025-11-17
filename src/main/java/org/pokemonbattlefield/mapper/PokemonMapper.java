package org.pokemonbattlefield.mapper;

import org.mapstruct.Mapper;
import org.pokemonbattlefield.model.Pokemon;

@Mapper(componentModel = "spring")
public interface PokemonMapper {

    PokemonDTO dePokemonParaDTO(Pokemon pokemon);
    Pokemon deDtoParaPokemon(PokemonDTO dto);
}
