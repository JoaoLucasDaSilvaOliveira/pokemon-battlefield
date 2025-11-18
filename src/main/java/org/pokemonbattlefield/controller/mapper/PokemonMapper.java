package org.pokemonbattlefield.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.pokemonbattlefield.controller.dto.DetalhesPokemonDTO;
import org.pokemonbattlefield.controller.dto.PokemonDTO;
import org.pokemonbattlefield.model.Pokemon;

@Mapper(componentModel = "spring")
public interface PokemonMapper {

    PokemonDTO dePokemonParaDTO(Pokemon pokemon);
    Pokemon deDtoParaPokemon(PokemonDTO dto);
    @Named("toDetalhesPokemonDTO")
    DetalhesPokemonDTO toDetalhesPokemonDTO (Pokemon pokemon);
}
