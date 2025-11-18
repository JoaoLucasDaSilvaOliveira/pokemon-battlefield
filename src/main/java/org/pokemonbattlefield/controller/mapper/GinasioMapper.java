package org.pokemonbattlefield.controller.mapper;

import org.mapstruct.Mapper;
import org.pokemonbattlefield.controller.dto.GinasioDTO;
import org.pokemonbattlefield.model.Ginasio;

@Mapper(componentModel = "spring")
public interface GinasioMapper {

    GinasioDTO deGinasioParaDTO(Ginasio ginasio);
    Ginasio deDtoParaGinasio(GinasioDTO dto);
}
