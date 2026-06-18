package org.pokemonbattlefield.Service;

import org.pokemonbattlefield.Repository.GinasioRepository;
import org.pokemonbattlefield.exception.IdInvalidoException;
import org.pokemonbattlefield.exception.RegistroNaoEncontradoException;
import org.pokemonbattlefield.model.Ginasio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GinasioService {

    private final GinasioRepository repository;

    public GinasioService(GinasioRepository repository) {
        this.repository = repository;
    }

    public List<Ginasio> obterTodos(){
        return repository.findAll();
    }

    public Ginasio obterPorId(String id){
        try {
            UUID idGinasio = UUID.fromString(id);
            return repository.findById(idGinasio).orElseThrow(()-> new RegistroNaoEncontradoException("Ginasio"));
        } catch (IllegalArgumentException e){
            throw new IdInvalidoException("UUID");
        }
    }
}
