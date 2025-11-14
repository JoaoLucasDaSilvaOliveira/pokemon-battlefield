package org.pokemonbattlefield.model;

import jakarta.persistence.*;
import lombok.Data;
import org.pokemonbattlefield.model.util.EvolucaoPokemon;

import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
public class Pokemon {

    public Pokemon (){}

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome_pokemon")
    private String nomePokemon;

    @Column(name = "pontos_vida")
    private Integer pontosVida;

    @Column(name = "pontos_ataque")
    private Integer pontosAtaque;

    @Column(name = "pontos_defesa")
    private Integer pontosDefesa;

    @Column(name = "evolucao")
    private EvolucaoPokemon evolucao;

    @Column(name = "qtd_vitorias")
    private Integer qtdVitorias;

    @Column(name = "qtd_derrotas")
    private Integer qtdDerrotas;

    @Column(name = "tipo")
    private String tipo;

    @JoinColumn(name = "id_treinador")
    @ManyToOne
    private Treinador treinador;

    @ManyToMany(mappedBy = "pokemonsBatalha")
    private List<Batalha> batalhas;

    public Integer getQtdBatalhas (){
        return (qtdDerrotas != null ? qtdDerrotas : 0) + (qtdVitorias != null ? qtdVitorias : 0);
    }

}
