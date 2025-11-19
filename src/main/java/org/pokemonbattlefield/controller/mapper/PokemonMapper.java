package org.pokemonbattlefield.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.pokemonbattlefield.controller.dto.DetalhesPokemonDTO;
import org.pokemonbattlefield.controller.dto.PokemonExternoDTO;
import org.pokemonbattlefield.model.Pokemon;

@Mapper(componentModel = "spring")
public interface PokemonMapper {

    PokemonExternoDTO dePokemonParaDTO(Pokemon pokemon);
    Pokemon deDtoParaPokemon(PokemonExternoDTO dto);
    @Named("toDetalhesPokemonDTO")
    DetalhesPokemonDTO toDetalhesPokemonDTO (Pokemon pokemon);
}
