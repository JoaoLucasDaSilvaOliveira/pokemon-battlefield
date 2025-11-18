package org.pokemonbattlefield.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pokemonbattlefield.Service.TreinadorService;
import org.pokemonbattlefield.controller.dto.DetalhesTreinadorDTO;
import org.pokemonbattlefield.controller.dto.TreinadorDTO;
import org.pokemonbattlefield.controller.util.URIConfigurer;
import org.pokemonbattlefield.model.Treinador;
import org.pokemonbattlefield.model.util.ClasseTreinador;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("treinador")
@RequiredArgsConstructor
public class TreinadorController {

    private final TreinadorService service;

    @PostMapping
    public ResponseEntity<Void> salvar (@RequestBody @Valid TreinadorDTO dto){
        UUID idSalvo = service.salvar(dto).getId();
        return ResponseEntity.created(URIConfigurer.createLocation(idSalvo)).build();
    }

    @GetMapping
    public ResponseEntity<List<DetalhesTreinadorDTO>> obterComFiltro (
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "classeTreinador", required = false) ClasseTreinador classe,
            @RequestParam(value = "nome-pokemons", required = false) List<String> nomePokemons
            ){
        List<DetalhesTreinadorDTO> treinadores = service.encontrarComFiltro(nome, classe, nomePokemons);
        return new ResponseEntity<>(treinadores, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Treinador> obterPorId(@PathVariable String id){
        Treinador treinador = service.obterPorId(id);
        return new ResponseEntity<>(treinador, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar (@PathVariable String id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar (@RequestBody TreinadorDTO dto, @PathVariable String id){
        service.atualizar(dto, id);
        return ResponseEntity.noContent().build();
    }

}
