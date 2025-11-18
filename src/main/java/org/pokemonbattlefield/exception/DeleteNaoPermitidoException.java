package org.pokemonbattlefield.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class DeleteNaoPermitidoException extends RuntimeException {
    public DeleteNaoPermitidoException(String entidade, Map<String, String> motivos) {
        super();
        this.entidade = entidade;
        this.motivos = motivos;
    }

    private final String entidade;
    private final Map<String, String> motivos;
}
