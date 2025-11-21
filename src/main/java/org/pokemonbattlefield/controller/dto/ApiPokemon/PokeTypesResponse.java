package org.pokemonbattlefield.controller.dto.ApiPokemon;

import java.util.List;

public record PokeTypesResponse(
        List<PokeTypes> results
) {

}
