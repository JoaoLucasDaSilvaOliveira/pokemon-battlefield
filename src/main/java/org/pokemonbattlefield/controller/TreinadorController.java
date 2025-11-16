package org.pokemonbattlefield.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pokemonbattlefield.controller.dto.TreinadorDTO;
import org.pokemonbattlefield.controller.util.URIConfigurer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("treinador")
@RequiredArgsConstructor
public class TreinadorController {

    private final BatalhaService service;

    @PostMapping
    public ResponseEntity<?> salvar (@RequestBody @Valid TreinadorDTO dto){
        //TODO: no service, incluir no try catch da conversão do uuid vindo o lançamento do erro especial:
        UUID idSalvo = service.salvar(dto);
        return ResponseEntity.created(URIConfigurer.createLocation(idSalvo)).build();
    }

}
