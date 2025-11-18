package org.pokemonbattlefield.exception;

import lombok.Getter;

@Getter
public class DuplicadoException extends RuntimeException {
    private DuplicadoException(String message) {
        super(message);
    }

    public static DuplicadoException createDuplicado(String entidade){
        return Motivos.CREATE_DUPLICADO.lancarException(entidade);
    }

    public static DuplicadoException updateDuplicado(String entidade){
        return Motivos.UPDATE_DUPLICADO.lancarException(entidade);
    }

    enum Motivos {
        CREATE_DUPLICADO("Já existe um %s com essas informações"),
        UPDATE_DUPLICADO("O %s a ser atualizado é identico ao já cadastrado");

        Motivos(String message){
            this.message = message;
        }

        private final String message;

        public DuplicadoException lancarException (String entidade){
            return new DuplicadoException(String.format(message, entidade));
        }
    }
}
