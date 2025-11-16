package org.pokemonbattlefield.exception;

import lombok.Getter;

@Getter
public class DuplicadoException extends Exception {
    public DuplicadoException() {
        super("Já existe um treinador com essas informações");
    }
}
