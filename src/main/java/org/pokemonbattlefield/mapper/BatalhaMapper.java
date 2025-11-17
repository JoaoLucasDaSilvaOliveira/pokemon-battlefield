package org.pokemonbattlefield.mapper;

import org.mapstruct.Mapper;
import org.pokemonbattlefield.model.Batalha;

@Mapper(componentModel = "spring")
public interface BatalhaMapper {

    BatalhaDTO deBatalhaParaDTO(Batalha batalha);
    Batalha deDtoParaBatalha(BatalhaDTO dto)
}
