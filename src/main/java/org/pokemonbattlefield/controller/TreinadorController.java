package org.pokemonbattlefield.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pokemonbattlefield.controller.dto.TreinadorDTO;
import org.pokemonbattlefield.controller.util.URIConfigurer;
import org.pokemonbattlefield.model.Pokemon;
import org.pokemonbattlefield.model.util.ClasseTreinador;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController("treinador")
@RequiredArgsConstructor
public class TreinadorController {

    private final BatalhaService service;

    @PostMapping
    public ResponseEntity<Void> salvar (@RequestBody @Valid TreinadorDTO dto){
        //TODO: no service, incluir no try catch da conversão do uuid vindo o lançamento do erro especial:
        UUID idSalvo = service.salvar(dto);
        return ResponseEntity.created(URIConfigurer.createLocation(idSalvo)).build();
    }

    @GetMapping
    public ResponseEntity<TreinadorDTO> obterTreinadorComFiltro (
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "classe", required = false) ClasseTreinador classeTreinador,
            @RequestParam(value = "pokemons") List<Pokemon> pokemons
            ){
        TreinadorDTO dto = service.findWithFilter(dto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
