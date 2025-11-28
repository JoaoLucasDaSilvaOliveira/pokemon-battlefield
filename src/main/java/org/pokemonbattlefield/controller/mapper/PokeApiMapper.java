package org.pokemonbattlefield.controller.mapper;

import lombok.RequiredArgsConstructor;
import org.pokemonbattlefield.Repository.PokemonRepository;
import org.pokemonbattlefield.controller.dto.ApiPokemon.MoveDetailsDTO;
import org.pokemonbattlefield.controller.dto.ApiPokemon.PokeApiResponse;
import org.pokemonbattlefield.controller.dto.ApiPokemon.PokeTypes;
import org.pokemonbattlefield.controller.dto.ApiPokemon.PokeTypesResponse;
import org.pokemonbattlefield.controller.dto.PokemonExternoDTO;
import org.pokemonbattlefield.model.util.AcaoPokemon;
import org.pokemonbattlefield.model.util.EvolucaoPokemon;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PokeApiMapper {

    // Injetamos o cliente configurado para a PokeAPI
    @Qualifier("restClientPokeAPI")
    private final RestClient restClient;
    private final PokemonRepository repository;

    public PokemonExternoDTO converterParaDTO(PokeApiResponse raw) {
        if (raw == null) return null;

        // 1. Extrair Status da lista da API
        Integer vida = getStatValue(raw.stats(), "hp");
        Integer ataquePokemon = getStatValue(raw.stats(), "attack");
        Integer defesaPokemon = getStatValue(raw.stats(), "defense");
        Integer id = raw.id();
        Map<String, String> mapaSprites = Map.of(
                "back", raw.sprites().backDefault(),
                "front", raw.sprites().frontDefault(),
                "back-shiny", raw.sprites().backShiny(),
                "front-shiny", raw.sprites().frontShiny()
        );

        // 2. Pegar o tipo principal
        String tipo = raw.types().isEmpty() ? "NORMAL" : raw.types().getFirst().type().name().toUpperCase();

        EvolucaoPokemon evolucao = EvolucaoPokemon.BASE;

        // 4. Mapear Ações (Moves) BUSCANDO DA API REAL
        List<AcaoPokemon> acoes = mapearAcoes(raw.moves(), ataquePokemon, defesaPokemon);

        return new PokemonExternoDTO(
                id,
                raw.name(),
                vida,
                ataquePokemon,
                defesaPokemon,
                evolucao,
                acoes,
                mapaSprites,
                tipo
        );
    }

    private List<AcaoPokemon> mapearAcoes(List<PokeApiResponse.MoveEntry> moves, Integer ataquePokemon, Integer defesaPokemon) {
        List<AcaoPokemon> acoesMapeadas = new ArrayList<>();
        if (moves == null) return acoesMapeadas;

        int maxAcoes = 4;

        // Itera sobre os golpes da lista até preencher os 4 slots
        int contadorAtaques = 0;
        int contadorDefesa = 0;
        int contadorCura = 0;
        for (PokeApiResponse.MoveEntry moveEntry : moves) {
            if (acoesMapeadas.size() >= maxAcoes) break;
            String nomeGolpe = moveEntry.move().name();

            try {
                // --- CHAMADA REAL À POKEAPI ---
                MoveDetailsDTO detalhes = restClient.get()
                        .uri("/move/{name}", nomeGolpe)
                        .retrieve()
                        .body(MoveDetailsDTO.class);

                if (detalhes == null || detalhes.damageClass() == null) continue;

                String tipoDanoApi = detalhes.damageClass().name();
                // Golpes de status geralmente vêm com power null, tratamos como 0 base
                int poderBase = detalhes.power() != null ? detalhes.power() : 0;

                // REGRA 1: Ignorar SPECIAL
                if ("special".equalsIgnoreCase(tipoDanoApi)) {
                    continue;
                }

                AcaoPokemon acao = null;

                // REGRA 2: PHYSICAL vira ATAQUE (Poder do Golpe + Ataque do Pokémon)
                if ("physical".equalsIgnoreCase(tipoDanoApi) && contadorAtaques < 2) {
                    int valorFinal = poderBase + ataquePokemon;
                    // Garante um mínimo de dano caso o golpe seja fraco
                    if (valorFinal < 10) valorFinal = 10 + (ataquePokemon / 2);

                    acao = new AcaoPokemon(nomeGolpe, valorFinal/6, AcaoPokemon.TipoDeAcao.ATAQUE);
                    contadorAtaques++;
                }

                // REGRA 3: STATUS vira DEFESA ou CURA
                else if ("status".equalsIgnoreCase(tipoDanoApi)) {
                    // Boost fixo para status moves (já que power costuma ser 0)
                    int boostFixo = 10;
                    if (contadorDefesa < 1) {
                        // Defesa = PoderBase + Defesa do Pokémon + Boost
                        int valorFinal = poderBase + defesaPokemon + boostFixo;
                        acao = new AcaoPokemon(nomeGolpe, valorFinal/6, AcaoPokemon.TipoDeAcao.DEFESA);
                        contadorDefesa++;
                    } else if (contadorCura < 1){
                        // Cura = PoderBase + (Defesa / 2) + Boost
                        int valorFinal = poderBase + (defesaPokemon / 2) + boostFixo;
                        acao = new AcaoPokemon(nomeGolpe, valorFinal/6, AcaoPokemon.TipoDeAcao.CURA);
                        contadorCura++;
                    }
                }

                if (acao != null) {
                    acoesMapeadas.add(acao);
                }

            } catch (Exception e) {
                // Loga o erro mas não para o processo (pode ser que um golpe específico dê 404)
                System.err.println("Falha ao buscar detalhes do golpe " + nomeGolpe + ": " + e.getMessage());
            }
        }
        return acoesMapeadas;
    }

    // --- Métodos Auxiliares ---

    public PokeTypesResponse normalize (PokeTypesResponse raw){
        return new PokeTypesResponse(
                raw.results()
                .stream()
                .map(tipo -> new PokeTypes(tipo.name().toUpperCase()))
                .toList()
        );
    }
    private Integer getStatValue(List<PokeApiResponse.StatEntry> stats, String statName) {
        if (stats == null) return 10;
        return stats.stream()
                .filter(s -> s.stat().name().equalsIgnoreCase(statName))
                .findFirst()
                .map(PokeApiResponse.StatEntry::baseStat)
                .orElse(10);
    }
}