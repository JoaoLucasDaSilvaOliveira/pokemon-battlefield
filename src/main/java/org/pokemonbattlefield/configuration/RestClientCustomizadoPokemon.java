package org.pokemonbattlefield.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientCustomizadoPokemon {

    @Bean("restClientPokeAPI")
    public RestTemplate restClientPokeAPI(RestTemplateBuilder builder){
        return builder
                .rootUri("https://pokeapi.co/api/v2")
                .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .build();
    }
}
