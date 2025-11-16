package org.pokemonbattlefield.controller.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErroRespostaDTO(
        int status,
        String message,
        List<ErroCampoDTO> campos
) {
    //TRATAMENTOS PADRÕES
    public static ErroRespostaDTO cadastroDuplicado(String message, List<ErroCampoDTO> campos){
        return new ErroRespostaDTO(HttpStatus.CONFLICT.value(), message, campos);
    }

    public static ErroRespostaDTO uuidIncorreto (List<ErroCampoDTO> campos){
        return new ErroRespostaDTO(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", campos);
    }

}
