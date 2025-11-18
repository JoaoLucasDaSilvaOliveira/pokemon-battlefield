package org.pokemonbattlefield.controller;

import org.pokemonbattlefield.model.util.ClasseTreinador;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController()
@RequestMapping("classes-treinadores")
public class ClasseTreinadorController {
    @GetMapping
    public ResponseEntity<Map<String, Map<String, Object>>> obterClassesDeTreinador(){
        return new ResponseEntity<>(ClasseTreinador.getDetalhesClassesComoMap(), HttpStatus.OK);
    }
}
