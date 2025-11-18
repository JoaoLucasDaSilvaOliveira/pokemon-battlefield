package org.pokemonbattlefield.model.util;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public enum ClasseTreinador {
    /*
    MAX BONUSES:
        ATAQUE:6
        DEF:6
        CURA: 6
     */
    GUERREIRO(
            4,4, 1,
            "Durável e confiável. O guerreiro possui vida e ataques balanceados com uma pequena taxa de cura"
    ),
    ESTRATEGISTA(
            3,3,3,
            "Balanceado, como tudo deve ser. O Estrategista possui o mesmo nível de ataque, defesa e cura"
    ),
    ASSASSINO(
            6,0,0,
            "A lâmina mais mortal é aquela que não se vê. O assassino possui muito dano, mas cuidado ele pode ser muito frágil"
    ),
    TANQUE(
            0,6,0,
            "Muralha de guerra. O tanque possui muita defesa, mas inflinge pouco dano"
    ),
    CURADOR(
            0,0 ,6,
            "Morto não ganha batalha. O curador tem uma alta capacidade de cura mas pode ser frágil, cuidado com os perigos perto dele"
    );

    private final Integer bonusAtq;
    private final Integer bonusDefesa;
    private final Integer bonusCura;
    private final String descricao;

    ClasseTreinador(Integer bonusAtq, Integer bonusDefesa, Integer bonusCura, String descricao) {
        this.bonusAtq = bonusAtq;
        this.bonusDefesa = bonusDefesa;
        this.bonusCura = bonusCura;
        this.descricao = descricao;
    }

    public static Map<String, Map<String, Object>> getDetalhesClassesComoMap() {

        // 1. Pega todas as constantes do enum (como um array)
        return Arrays.stream(ClasseTreinador.values())

                // 2. Coleta em um Map
                .collect(Collectors.toMap(

                        // Chave: O nome da constante (ex: "GUERREIRO")
                        ClasseTreinador::name,

                        // Valor: Cria um novo Map para os detalhes da classe
                        classe -> Map.of(
                                "Bônus ataque", classe.getBonusAtq(),
                                "Bônus defesa", classe.getBonusDefesa(),
                                "Bônus cura", classe.getBonusCura(),
                                "Descricao", classe.getDescricao()
                        )
                ));
    }
}
