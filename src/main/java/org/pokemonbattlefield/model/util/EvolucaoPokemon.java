package org.pokemonbattlefield.model.util;

public enum EvolucaoPokemon {
    BASE(0),
    EVO_I(0.5),
    EVO_II(1),
    EVO_III(1.5),
    SHINE(2);

    EvolucaoPokemon(double multiplicador) {
        this.multiplicador = multiplicador;
    }

    private final double multiplicador;

    public double getMultiplicador() {
        return multiplicador;
    }
}
