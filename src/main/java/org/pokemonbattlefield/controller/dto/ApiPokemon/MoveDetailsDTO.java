package org.pokemonbattlefield.controller.dto.ApiPokemon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MoveDetailsDTO(
        Integer power,
        @JsonProperty("damage_class") DamageClass damageClass
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record DamageClass(String name) {}
}