package org.pokemonbattlefield.model.util;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Column;
@Embeddable
public class AcaoPokemon {

    @Column(name = "nome_acao", nullable = false)
    private String nomeAcao;

    @Column(name = "valor_acao", nullable = false)
    private Integer valorAcao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_acao", nullable = false)
    private TipoDeAcao tipoDeAcao;

    public AcaoPokemon() {
    }

    public AcaoPokemon(String nomeAcao, Integer valorAcao, TipoDeAcao tipoDeAcao) {
        this.nomeAcao = nomeAcao;
        this.valorAcao = valorAcao;
        this.tipoDeAcao = tipoDeAcao;
    }

    public String getNomeAcao() {
        return nomeAcao;
    }

    public void setNomeAcao(String nomeAcao) {
        this.nomeAcao = nomeAcao;
    }

    public Integer getValorAcao() {
        return valorAcao;
    }

    public void setValorAcao(Integer valorAcao) {
        this.valorAcao = valorAcao;
    }

    public TipoDeAcao getTipoDeAcao() {
        return tipoDeAcao;
    }

    public void setTipoDeAcao(TipoDeAcao tipoDeAcao) {
        this.tipoDeAcao = tipoDeAcao;
    }

    public enum TipoDeAcao {
        ATAQUE,
        DEFESA,
        CURA
    }
}
