package org.pokemonbattlefield.mapper;

import org.mapstruct.Mapper;
import org.pokemonbattlefield.model.Treinador;

@Mapper(componentModel = "spring")
public interface TreinadorMapper  {

    TreinadorDTO deTreinadorParaDTO(Treinador treinador);
    Treinador deDtoParaTreinador(TreinadorDTO dto);
}
