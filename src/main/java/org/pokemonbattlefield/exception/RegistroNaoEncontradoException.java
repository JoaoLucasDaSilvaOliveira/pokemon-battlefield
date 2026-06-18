package org.pokemonbattlefield.exception;

public class RegistroNaoEncontradoException extends RuntimeException {
    public RegistroNaoEncontradoException(String tipoEntidade) {
        super();
        this.tipoEntidade = tipoEntidade;
    }

    private final String tipoEntidade;

    public String getTipoEntidade() {
        return tipoEntidade;
    }
}
