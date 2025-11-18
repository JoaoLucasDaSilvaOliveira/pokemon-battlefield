package org.pokemonbattlefield.controller.mapper;

import org.mapstruct.Mapper;
import org.pokemonbattlefield.controller.dto.BatalhaDTO;
import org.pokemonbattlefield.model.Batalha;

@Mapper(componentModel = "spring")
public interface BatalhaMapper {

    BatalhaDTO deBatalhaParaDTO(Batalha batalha);
    Batalha deDtoParaBatalha(BatalhaDTO dto);
}
