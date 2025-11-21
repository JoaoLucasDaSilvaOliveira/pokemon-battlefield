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
                // USAR USER-AGENT DE NAVEGADOR REAL:
                .defaultHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .defaultHeader("Accept", "application/json")
                .build();
    }
}