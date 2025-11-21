package org.pokemonbattlefield.controller.common;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.pokemonbattlefield.controller.dto.ErroCampoDTO;
import org.pokemonbattlefield.controller.dto.ErroRespostaDTO;
import org.pokemonbattlefield.exception.*;
import org.pokemonbattlefield.model.util.ClasseTreinador;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroRespostaDTO handleUUIDInvalidoException (IdInvalidoException e){
        return new ErroRespostaDTO(HttpStatus.UNPROCESSABLE_ENTITY. value(), "Identificador informado está incorreto, informar um id no formato: "+ e.getTipoId(), List.of());
    }

    //PARA ERROS DE REGISTRO NÃO ENCONTRADO
    @ExceptionHandler(RegistroNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroRespostaDTO handleRegistroNaoEncontradoException(RegistroNaoEncontradoException e){
        return new ErroRespostaDTO(HttpStatus.NOT_FOUND.value(), e.getTipoEntidade()+" não encontrado", List.of());
    }

    //PARA EXCLUSÕES NÃO PERMITIDAS (INFRIGEM REGRA DE NEGOCIOS)
    @ExceptionHandler(DeleteNaoPermitidoException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErroRespostaDTO handleDeleteNaoPermitidoException (DeleteNaoPermitidoException e){
        return new ErroRespostaDTO(HttpStatus.UNAUTHORIZED.value(), String.format("Exclusão de %s não permitida", e.getEntidade()),
                e.getMotivos()
                        .entrySet()
                        .stream().map(
                                entry -> new ErroCampoDTO(entry.getKey(), entry.getValue())
                        ).toList()
        );
    }

    //PARA ERROS DE CONVERSÃO DE ENUM
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroRespostaDTO handleValidationExceptions(Exception ex) {

        // Caso 1: Erro na URL (ex: passar string onde deveria ser enum/int)
        if (ex instanceof MethodArgumentTypeMismatchException typeMismatchEx) {
            if (typeMismatchEx.getRequiredType() == ClasseTreinador.class) {
                return new ErroRespostaDTO(HttpStatus.BAD_REQUEST.value(), "Classe de treinador inexistente", List.of());
            }
        }

        // Caso 2: Erro no JSON (ex: valor inválido no corpo da requisição)
        else if (ex instanceof HttpMessageNotReadableException readableEx) {
            Throwable cause = readableEx.getCause();
            if (cause instanceof InvalidFormatException formatEx) {
                if (formatEx.getTargetType() == ClasseTreinador.class) {
                    return new ErroRespostaDTO(HttpStatus.BAD_REQUEST.value(), "Classe de treinador inexistente", List.of());
                }
            }
        }

        return new ErroRespostaDTO(HttpStatus.BAD_REQUEST.value(), "Erro na requisição. Verifique os tipos de dados.", List.of());
    }

    //PARA ERROS DE REQUISIÇÃO MAL FEITA
    @ExceptionHandler(RequisicaoMalFeitaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroRespostaDTO handleDeleteNaoPermitidoException (RequisicaoMalFeitaException e){
        return new ErroRespostaDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage(), List.of());
    }

    //PARA ERROS NÃO VISTOS
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroRespostaDTO handleRuntimeException (RuntimeException e){
        return new ErroRespostaDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Algum erro inesperado ocorreu, por favor contate o administrador", List.of(new ErroCampoDTO(e.toString(), e.getMessage())));
    }

}
