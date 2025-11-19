package org.pokemonbattlefield.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.pokemonbattlefield.model.util.AcaoPokemon;
import org.pokemonbattlefield.model.util.EvolucaoPokemon;

import java.util.List;

@Entity
@Table
@Data
public class Pokemon {

    public Pokemon (){}

    @Id
    private Integer id;

    @Column(name = "nome_pokemon")
    private String nome;

    @Column(name = "pontos_vida")
    private Integer pontosVida;

    @Column(name = "pontos_ataque")
    private Integer pontosAtaque;

    @Column(name = "pontos_defesa")
    private Integer pontosDefesa;

    @Enumerated(EnumType.STRING)
    @Column(name = "evolucao")
    private EvolucaoPokemon evolucao;

    @Column(name = "tipo")
    private String tipo;

    @ElementCollection
    @CollectionTable(
            name = "acao_pokemons",
            joinColumns = @JoinColumn(name = "id_pokemon")
    )
    private List<AcaoPokemon> acoesPokemon;

    @JoinColumn(name = "id_treinador")
    @ManyToOne
    @JsonBackReference
    private Treinador treinador;

    @ManyToMany(mappedBy = "pokemonsBatalha")
    @JsonManagedReference
    private List<Batalha> batalhas;

    // O @JsonProperty garante que o JSON vai ter um campo "qtdVitorias"
    @JsonProperty("qtdVitorias")
    public Integer getQtdVitorias() {
        if (batalhas == null || batalhas.isEmpty() || treinador == null) {
            return 0;
        }
        // Filtra as batalhas onde o vencedor existe E o ID é igual ao do meu treinador
        return (int) batalhas.stream()
                .filter(b -> b.getGanhador() != null &&
                        b.getGanhador().getId().equals(this.treinador.getId()))
                .count();
    }

    @JsonProperty("qtdDerrotas")
    public Integer getQtdDerrotas() {
        if (batalhas == null || batalhas.isEmpty() || treinador == null) {
            return 0;
        }
        // Filtra as batalhas onde o vencedor existe E o ID é DIFERENTE do meu treinador
        return (int) batalhas.stream()
                .filter(b -> b.getGanhador() != null &&
                        !b.getGanhador().getId().equals(this.treinador.getId()))
                .count();
    }

    // O Jackson usa esse getter também
    @JsonProperty("qtdBatalhas")
    public Integer getQtdBatalhas() {
        // Apenas soma os dois calculados acima
        return getQtdVitorias() + getQtdDerrotas();
    }

}
