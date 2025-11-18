package org.pokemonbattlefield.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.pokemonbattlefield.controller.dto.DetalhesTreinadorDTO;
import org.pokemonbattlefield.controller.dto.TreinadorDTO;
import org.pokemonbattlefield.model.Treinador;

@Mapper(componentModel = "spring", uses = PokemonMapper.class)
public interface TreinadorMapper  {

    TreinadorDTO deTreinadorParaDTO(Treinador treinador);

    Treinador deDtoParaTreinador(TreinadorDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pokemons", ignore = true)
    @Mapping(target = "levelExperiencia", ignore = true)
    Treinador deDtoParaTreinador(TreinadorDTO dto, @MappingTarget Treinador target);

    @Mapping(target = "pokemons", qualifiedByName = "toDetalhesPokemonDTO")
    DetalhesTreinadorDTO toDetalhesDTO (Treinador treinador);
}
