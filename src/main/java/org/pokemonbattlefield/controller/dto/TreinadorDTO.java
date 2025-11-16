package org.pokemonbattlefield.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.pokemonbattlefield.model.util.ClasseTreinador;

public record TreinadorDTO (
        @NotBlank(message = "Por favor, informe um nome válido para o seu treinador")
        String nome,
        @NotNull(message = "Por favor, informe a classe do seu treinador")
        ClasseTreinador classe
){
}
