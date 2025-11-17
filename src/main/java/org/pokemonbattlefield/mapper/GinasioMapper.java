package org.pokemonbattlefield.mapper;

import org.mapstruct.Mapper;
import org.pokemonbattlefield.model.Ginasio;

@Mapper(componentModel = "spring")
public interface GinasioMapper {

    GinasioDTO deGinasioParaDTO(Ginasio ginasio);
    Ginasio deDtoParaGinasio(GinasioDTO dto);
}
