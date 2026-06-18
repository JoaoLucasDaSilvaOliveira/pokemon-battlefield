package org.pokemonbattlefield.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import org.pokemonbattlefield.model.util.AcaoPokemon;
import org.pokemonbattlefield.model.util.EvolucaoPokemon;

import java.util.List;
import java.util.Map;

@Entity
@Table
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

    @ElementCollection
    @CollectionTable (
            name = "pokemon_sprites",
            joinColumns = @JoinColumn(name = "id_pokemon")
    )
    @MapKeyColumn(name = "sprite_key")
    @Column(name = "sprite_url")
    Map<String, String> sprites;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getPontosVida() {
        return pontosVida;
    }

    public void setPontosVida(Integer pontosVida) {
        this.pontosVida = pontosVida;
    }

    public Integer getPontosAtaque() {
        return pontosAtaque;
    }

    public void setPontosAtaque(Integer pontosAtaque) {
        this.pontosAtaque = pontosAtaque;
    }

    public Integer getPontosDefesa() {
        return pontosDefesa;
    }

    public void setPontosDefesa(Integer pontosDefesa) {
        this.pontosDefesa = pontosDefesa;
    }

    public EvolucaoPokemon getEvolucao() {
        return evolucao;
    }

    public void setEvolucao(EvolucaoPokemon evolucao) {
        this.evolucao = evolucao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<AcaoPokemon> getAcoesPokemon() {
        return acoesPokemon;
    }

    public void setAcoesPokemon(List<AcaoPokemon> acoesPokemon) {
        this.acoesPokemon = acoesPokemon;
    }

    public Treinador getTreinador() {
        return treinador;
    }

    public void setTreinador(Treinador treinador) {
        this.treinador = treinador;
    }

    public List<Batalha> getBatalhas() {
        return batalhas;
    }

    public void setBatalhas(List<Batalha> batalhas) {
        this.batalhas = batalhas;
    }

    public Map<String, String> getSprites() {
        return sprites;
    }

    public void setSprites(Map<String, String> sprites) {
        this.sprites = sprites;
    }

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
