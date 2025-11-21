package org.pokemonbattlefield.controller.dto.ApiPokemon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Representa a estrutura exata do JSON que vem da PokeAPI.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PokeApiResponse(
        Integer id,
        String name,
        List<StatEntry> stats,
        List<TypeEntry> types,
        SpriteEntry sprites,
        List<MoveEntry> moves
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record StatEntry(
            @JsonProperty("base_stat") Integer baseStat,
            StatInfo stat
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record StatInfo(String name) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record TypeEntry(TypeInfo type) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record TypeInfo(String name) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record MoveEntry(MoveInfo move) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record MoveInfo(String name) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record SpriteEntry(
            @JsonProperty("front_default") String frontDefault,
            @JsonProperty("back_default") String backDefault,
            @JsonProperty("back_shiny") String backShiny,
            @JsonProperty("front_shiny") String frontShiny
    ){}
}