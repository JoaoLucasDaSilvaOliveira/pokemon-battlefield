package org.pokemonbattlefield.controller.dto.ApiPokemon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PokeTypes(String name) {
}
