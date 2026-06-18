package org.pokemonbattlefield.exception;

import java.util.Map;

public class DeleteNaoPermitidoException extends RuntimeException {
    public DeleteNaoPermitidoException(String entidade, Map<String, String> motivos) {
        super();
        this.entidade = entidade;
        this.motivos = motivos;
    }

    private final String entidade;
    private final Map<String, String> motivos;

    public String getEntidade() {
        return entidade;
    }

    public Map<String, String> getMotivos() {
        return motivos;
    }
}
