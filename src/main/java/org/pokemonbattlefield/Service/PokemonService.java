package org.pokemonbattlefield.Service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pokemonbattlefield.Repository.BatalhaRepository;
import org.pokemonbattlefield.Repository.PokemonRepository;
import org.pokemonbattlefield.Repository.TreinadorRepository;
import org.pokemonbattlefield.controller.dto.ApiPokemon.PokeApiResponse;
import org.pokemonbattlefield.controller.dto.ApiPokemon.PokeTypesResponse;
import org.pokemonbattlefield.controller.dto.ApiPokemon.PokemonListaResponseDTO;
import org.pokemonbattlefield.controller.dto.CadastrarPokemonDTO;
import org.pokemonbattlefield.controller.dto.PokemonExternoDTO;
import org.pokemonbattlefield.controller.mapper.PokeApiMapper;
import org.pokemonbattlefield.controller.mapper.PokemonMapper;
import org.pokemonbattlefield.exception.DeleteNaoPermitidoException;
import org.pokemonbattlefield.exception.DuplicadoException;
import org.pokemonbattlefield.exception.RegistroNaoEncontradoException;
import org.pokemonbattlefield.exception.RequisicaoMalFeitaException;
import org.pokemonbattlefield.model.Pokemon;
import org.pokemonbattlefield.model.Treinador;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent; // Importante
import org.springframework.context.event.EventListener; // Importante
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.*;
import java.util.concurrent.CompletableFuture; // Importante
import java.util.concurrent.ConcurrentHashMap; // Importante

@Service
@RequiredArgsConstructor
public class PokemonService {

    // CACHE EM MEMÓRIA: Guarda as páginas já carregadas (Chave: "pagina-tamanho")
    private final Map<String, PokemonListaResponseDTO> cachePaginas = new ConcurrentHashMap<>();

    private final PokemonRepository repository;
    private final TreinadorRepository treinadorRepository;
    private final PokeApiMapper apiMapper;
    private final PokemonMapper mapper;
    private final BatalhaRepository batalhaRepository;

    @Qualifier(value = "restClientPokeAPI")
    private final RestClient restClient;

    // --- NOVO: Carrega a primeira página assim que o sistema sobe ---
    @EventListener(ApplicationReadyEvent.class)
    public void carregarCacheInicial() {
        System.out.println("🚀 Iniciando pré-carregamento da PokeAPI...");

        CompletableFuture.runAsync(() -> {
            int tentativas = 0;
            boolean sucesso = false;

            while (tentativas < 3 && !sucesso) {
                try {
                    // Pequeno delay inicial para garantir que a rede do container subiu
                    if (tentativas == 0) Thread.sleep(3000);

                    // Carrega página 0 e 1
                    carregarPaginaNoCache(0, 30);
                    Thread.sleep(1000); // Delay amigável para não tomar bloqueio da API
                    carregarPaginaNoCache(1, 30);

                    sucesso = true;
                    System.out.println("✅ Cache inicial da PokeAPI carregado com sucesso!");

                } catch (Exception e) {
                    tentativas++;
                    System.err.println("⚠️ Falha no prefetch (Tentativa " + tentativas + "/3): " + e.getMessage());
                    try {
                        Thread.sleep(5000); // Espera 5s antes de tentar de novo
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }

            if (!sucesso) {
                System.err.println("❌ Falha crítica no prefetch após 3 tentativas. O sistema funcionará, mas sem cache inicial.");
            }
        });
    }

    public PokemonListaResponseDTO findPokemonsOnPokeAPI(String nome, String tipo, Integer pagina) {

        // 1. Busca Específica (Mantém lógica antiga, sem cache de lista)
        if (nome != null && !nome.isBlank()){
            if (tipo != null && !tipo.isBlank()){
                throw new RequisicaoMalFeitaException("Deve passar apenas um dos filtros: nome ou tipo");
            }
            try {
                PokemonExternoDTO pokemonEncontrado = findByNameOrIdOnPokeAPI(nome);
                return new PokemonListaResponseDTO(1, null, null, List.of(pokemonEncontrado));
            } catch (RegistroNaoEncontradoException e) {
                return new PokemonListaResponseDTO(0, null, null, List.of());
            }
        }

        // 2. Listagem Geral (COM CACHE E PREFETCH)
        String chaveCache = pagina + "-" + 30;

        PokemonListaResponseDTO resposta;

        if (cachePaginas.containsKey(chaveCache)) {
            // HIT: Achou no cache! Retorna instantaneamente.
            resposta = cachePaginas.get(chaveCache);
        } else {
            // MISS: Não tem no cache, busca agora (bloqueia user rapidinho) e salva.
            resposta = montarRequisicaoPaginadaComDetalhes("/pokemon", pagina, 30);
            if (resposta.results() != null && !resposta.results().isEmpty()) {
                cachePaginas.put(chaveCache, resposta);
            }
        }

        // 3. PREFETCH: Dispara thread para buscar a PRÓXIMA página enquanto o usuário vê a atual
        // Assim, quando ele clicar em "Próximo", já vai estar no cache.
        CompletableFuture.runAsync(() -> carregarPaginaNoCache(pagina + 1, 30));

        return resposta;
    }

    // Método auxiliar para popular o cache em background
    private void carregarPaginaNoCache(Integer pagina, Integer tamanho) {
        String chave = pagina + "-" + tamanho;
        // Só busca se ainda não tiver no cache
        if (!cachePaginas.containsKey(chave)) {
            try {
                PokemonListaResponseDTO dto = montarRequisicaoPaginadaComDetalhes("/pokemon", pagina, tamanho);
                if (dto != null && dto.results() != null && !dto.results().isEmpty()) {
                    cachePaginas.put(chave, dto);
                    System.out.println("📦 Página " + pagina + " pré-carregada no cache.");
                }
            } catch (Exception e) {
                System.err.println("Erro no prefetch da página " + pagina + ": " + e.getMessage());
            }
        }
    }

    // --- Seus métodos originais abaixo ---

    public PokemonExternoDTO findByNameOrIdOnPokeAPI(String nomeOuId) {
        try {
            PokeApiResponse raw = restClient.get()
                    .uri("/pokemon/{id}", nomeOuId)
                    .retrieve()
                    .body(PokeApiResponse.class);
            return apiMapper.converterParaDTO(raw);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RegistroNaoEncontradoException("Pokemon não encontrado na API externa: " + nomeOuId);
        }
    }

    public PokeTypesResponse todosOsTiposDosPokemons(){
        return apiMapper.normalize(montarRequisicaoPokeTypes());
    }

    private PokemonListaResponseDTO montarRequisicaoPaginadaComDetalhes(String path, Integer pagina, Integer tamanhoPagina) {
        Integer offset = pagina * tamanhoPagina;

        PokeApiListResponse rawList = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("limit", tamanhoPagina)
                        .queryParam("offset", offset)
                        .build())
                .retrieve()
                .body(PokeApiListResponse.class);

        if (rawList == null || rawList.results() == null) {
            return new PokemonListaResponseDTO(0, null, null, List.of());
        }

        // Parallel Stream manteve a hidratação rápida
        List<PokemonExternoDTO> listaDetalhada = rawList.results().parallelStream()
                .map(resumo -> {
                    try {
                        return findByNameOrIdOnPokeAPI(resumo.name());
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        return new PokemonListaResponseDTO(
                rawList.count(),
                rawList.next(),
                rawList.previous(),
                listaDetalhada
        );
    }

    private PokeTypesResponse montarRequisicaoPokeTypes(){
        return restClient
                .get()
                .uri("/type")
                .retrieve()
                .body(PokeTypesResponse.class);
    }

    public Integer vincular(@Valid CadastrarPokemonDTO dto) {
        Treinador treinador = treinadorRepository.findById(UUID.fromString(dto.idTreinador())).orElseThrow(()-> new RegistroNaoEncontradoException("Treinador"));
        if (!verificaDono(dto.idPokemon())){
            PokemonExternoDTO pokemonRaw = findByNameOrIdOnPokeAPI(String.valueOf(dto.idPokemon()));
            Pokemon pokemon = mapper.deDtoParaPokemon(pokemonRaw);
            pokemon.setId(dto.idPokemon());
            pokemon.setTreinador(treinador);
            return repository.save(pokemon).getId();
        }
        throw DuplicadoException.createPokemonDuplicado();
    }

    public void desvincular(Integer id) {
        if (batalhaRepository.existsBatalhaByPokemonId(id)){
            throw new DeleteNaoPermitidoException("Pokemon", Map.of(
                    "Motivo", "O pokemón já possui batalhas registradas",
                    "Solução", "Não é possível desvincular esse pokémon"
            ));
        }
        repository.deleteById(id);
    }

    private record PokeApiListResponse(
            int count,
            String next,
            String previous,
            List<PokeApiResumo> results
    ) {}

    private record PokeApiResumo(String name, String url) {}

    public boolean verificaDono(Integer id){
        return repository.existsPokemonComTreinador(id);
    }
}