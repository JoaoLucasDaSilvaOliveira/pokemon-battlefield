package org.pokemonbattlefield.exception;

public class IdInvalidoException extends RuntimeException {
    public IdInvalidoException(String tipoId) {
        super();
        this.tipoId = tipoId;
    }

    private final String tipoId;

    public String getTipoId() {
        return tipoId;
    }
}
