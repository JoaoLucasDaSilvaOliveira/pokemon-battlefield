package org.pokemonbattlefield.model.util;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AcaoPokemon {

    @Column(name = "nome_acao", nullable = false)
    private String nomeAcao;

    @Column(name = "valor_acao", nullable = false)
    private Integer valorAcao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_acao", nullable = false)
    private TipoDeAcao tipoDeAcao;

    public enum TipoDeAcao {
        ATAQUE,
        DEFESA,
        CURA
    }
}