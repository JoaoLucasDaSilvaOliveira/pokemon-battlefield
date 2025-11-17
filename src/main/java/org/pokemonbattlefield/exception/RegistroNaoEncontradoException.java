package org.pokemonbattlefield.exception;

import lombok.Getter;

@Getter
public class RegistroNaoEncontradoException extends RuntimeException {
    public RegistroNaoEncontradoException(String tipoEntidade) {
        super();
        this.tipoEntidade = tipoEntidade;
    }

    private final String tipoEntidade;
}
