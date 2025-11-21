package org.pokemonbattlefield.pokemonbattlefield;

import org.junit.jupiter.api.Test;
import org.pokemonbattlefield.Service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

public class TestPokeApi {

    @Autowired
    PokemonService service;

    @Test
    void testeProcuraPorNomeOuID(){
        System.out.println(service.findByNameOrIdOnPokeAPI("pikachu"));
    }


    @Test
    void testeTipos(){
        System.out.println(service.todosOsTiposDosPokemons());
    }
}
