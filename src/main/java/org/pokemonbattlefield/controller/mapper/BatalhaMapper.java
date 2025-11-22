package org.pokemonbattlefield.controller.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.pokemonbattlefield.Service.GinasioService;
import org.pokemonbattlefield.Service.PokemonService;
import org.pokemonbattlefield.Service.TreinadorService;
import org.pokemonbattlefield.controller.dto.BatalhaDTO;
import org.pokemonbattlefield.model.Batalha;

@Mapper(componentModel = "spring")
public interface BatalhaMapper {

    BatalhaDTO deBatalhaParaDTO(Batalha batalha);

    @Mapping(target = "pokemonsBatalha", expression = "java(pokemonService.findPokemonsById(dto.idPokemons()))")
    @Mapping(target = "ganhador", expression = "java(treinadorService.obterPorId(dto.idTreinadorGanhador()))")
    @Mapping(target = "ginasio", expression = "java(ginasioService.obterPorId(dto.idGinasio()))")
    Batalha deDtoParaBatalha(BatalhaDTO dto, @Context PokemonService pokemonService,
                             @Context TreinadorService treinadorService,
                             @Context GinasioService ginasioService);
}
