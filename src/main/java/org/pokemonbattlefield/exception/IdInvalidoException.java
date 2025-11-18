package org.pokemonbattlefield.exception;

import lombok.Getter;

@Getter
public class IdInvalidoException extends RuntimeException {
    public IdInvalidoException(String tipoId) {
        super();
        this.tipoId = tipoId;
    }

    private final String tipoId;
}
