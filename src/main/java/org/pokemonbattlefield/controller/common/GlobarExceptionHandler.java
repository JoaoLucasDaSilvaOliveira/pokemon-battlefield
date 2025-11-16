package org.pokemonbattlefield.controller.common;

import org.pokemonbattlefield.controller.dto.ErroCampoDTO;
import org.pokemonbattlefield.controller.dto.ErroRespostaDTO;
import org.pokemonbattlefield.exception.DuplicadoException;
import org.pokemonbattlefield.exception.IdInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobarExceptionHandler {

    //PARA ERROS DE VALIDAÇÃO DO PACOTE VALIDATION DO SPRING
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroRespostaDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> erros = e.getFieldErrors();
        List<ErroCampoDTO> camposComErro = erros
                .stream()
                .map(erro -> new ErroCampoDTO(erro.getField(), erro.getDefaultMessage())).toList();
        return new ErroRespostaDTO(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", camposComErro);
    }

    //PARA ERRO DE CADASTROS DUPLICADOS
    @ExceptionHandler(DuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroRespostaDTO handleDuplicadoException(DuplicadoException e){
        return new ErroRespostaDTO(HttpStatus.CONFLICT. value(), e.getMessage(), List.of());
    }

    //PARA ERRO DE UUIDs INVALIDOS
    @ExceptionHandler(IdInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroRespostaDTO handleUUIDInvalidoException (IdInvalidoException e){
        return new ErroRespostaDTO(HttpStatus.UNPROCESSABLE_ENTITY. value(), "Identificador informado está incorreto, informar um id no formato: "+ e.getTipoId(), List.of());
    }

    //PARA ERROS NÃO VISTOS
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroRespostaDTO handleRuntimeException (RuntimeException e){
        return new ErroRespostaDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Algum erro inesperado ocorreu, por favor contate o administrador", List.of(new ErroCampoDTO(e.toString(), e.getMessage())));
    }

}
