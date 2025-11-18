package org.pokemonbattlefield.controller.util;

@FunctionalInterface
public interface IdValidador<T> {
     T validarERetornarId (String id);
}
