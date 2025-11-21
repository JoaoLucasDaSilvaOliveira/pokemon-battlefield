package org.pokemonbattlefield.controller.util;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Component
public class URIConfigurer {

    public static <T> URI createLocation (T idSalvo){
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(idSalvo)
                .toUri();
    }

}
