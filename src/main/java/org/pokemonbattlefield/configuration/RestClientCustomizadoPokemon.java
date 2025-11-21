package org.pokemonbattlefield.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientCustomizadoPokemon {

    @Bean("restClientPokeAPI")
    public RestClient restClientPokeAPI (RestClient.Builder builder){
        return builder
                .baseUrl("https://pokeapi.co/api/v2")
                .defaultHeader("User-Agent", "PokemonBattlefield-App/1.0 (Java Application)")
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
