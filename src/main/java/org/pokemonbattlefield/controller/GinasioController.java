package org.pokemonbattlefield.controller;

import javax.validation.constraints.NotBlank;
import org.pokemonbattlefield.Service.GinasioService;
import org.pokemonbattlefield.model.Ginasio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("ginasio")
public class GinasioController {

    private final GinasioService service;

    public GinasioController(GinasioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Ginasio>> obterTodosOsGinasios(){
        List<Ginasio> ginasios = service.obterTodos();
        return new ResponseEntity<>(ginasios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ginasio> obterGinasioPorId(@PathVariable @NotBlank(message = "Por favor, forneça o id do ginasio") String id){
        Ginasio ginasio = service.obterPorId(id);
        return new ResponseEntity<>(ginasio, HttpStatus.OK);
    }
}
