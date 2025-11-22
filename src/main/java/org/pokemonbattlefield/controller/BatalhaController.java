package org.pokemonbattlefield.controller;

import lombok.RequiredArgsConstructor;
import org.pokemonbattlefield.Service.BatalhaService;
import org.pokemonbattlefield.controller.dto.BatalhaDTO;
import org.pokemonbattlefield.controller.util.URIConfigurer;
import org.pokemonbattlefield.model.Batalha;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("batalha")
@RequiredArgsConstructor
public class BatalhaController {

    private final BatalhaService service;

    @GetMapping
    public ResponseEntity<List<Batalha>> obterTodas (
            @RequestParam(value = "id-treinador", required = false) String idTreinador
    ){
        return new ResponseEntity<>(service.obterTodas(idTreinador), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Batalha> obterPorId(@PathVariable String id){
        return new ResponseEntity<>(service.obterPorId(id), HttpStatus.OK);
    }

    @GetMapping("/infos{id}")
    public ResponseEntity<Map<String, Integer>> obterDadosBatalhaPorIdTreinador (@PathVariable(name = "id") String idTreinador){
        return new ResponseEntity<>(service.obterDetalhesTreinador(idTreinador), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> registrarBatalha(@RequestBody BatalhaDTO dto){
        UUID idBatalha = service.registrarBatalha(dto);
        return ResponseEntity.created(URIConfigurer.createLocation(idBatalha)).build();
    }
}
