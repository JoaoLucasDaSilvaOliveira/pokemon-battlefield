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
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class PokemonService {

    // CACHE EM MEMÓRIA (Guarda as páginas já visitadas para acesso instantâneo)
    private final Map<String, PokemonListaResponseDTO> cachePaginas = new ConcurrentHashMap<>();

    private final PokemonRepository repository;
    private final TreinadorRepository treinadorRepository;
    private final PokeApiMapper apiMapper;
    private final PokemonMapper mapper;
    private final BatalhaRepository batalhaRepository;

    @Qualifier(value = "restClientPokeAPI")
    private final RestClient restClient;

    // --- INICIALIZAÇÃO INTELIGENTE (Executa ao subir a aplicação) ---
    // Tenta carregar as primeiras páginas. Se falhar (rede/deploy), tenta de novo algumas vezes.
    @EventListener(ApplicationReadyEvent.class)
    public void carregarCacheInicial() {
        System.out.println("🚀 Iniciando pré-carregamento da PokeAPI...");

        CompletableFuture.runAsync(() -> {
            int tentativas = 0;
            boolean sucesso = false;

            while (tentativas < 3 && !sucesso) {
                try {
                    // Delay inicial de 5s para garantir que o container e a rede estejam 100%
                    if (tentativas == 0) Thread.sleep(5000);

                    // Tenta carregar as páginas iniciais
                    carregarPaginaNoCache(0, 30);
                    Thread.sleep(2000); // Delay entre chamadas para não estourar rate limit
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
                System.err.println("❌ Falha crítica no prefetch após 3 tentativas. O cache inicial não foi carregado.");
            }
        });
    }

    public PokemonListaResponseDTO findPokemonsOnPokeAPI(String nome, String tipo, Integer pagina) {

        // 1. Validação de Filtros
        if ((nome != null && !nome.isBlank()) && (tipo != null && !tipo.isBlank())){
            throw new RequisicaoMalFeitaException("Deve passar apenas um dos filtros: nome ou tipo");
        }

        // 2. Busca por Nome (Busca direta, sem cache de lista)
        if (nome != null && !nome.isBlank()){
            try {
                PokemonExternoDTO pokemonEncontrado = findByNameOrIdOnPokeAPI(nome);
                return new PokemonListaResponseDTO(1, null, null, List.of(pokemonEncontrado));
            } catch (RegistroNaoEncontradoException e) {
                return new PokemonListaResponseDTO(0, null, null, List.of());
            }
        }

        if (tipo != null && !tipo.isBlank()){
            return buscarPokemonsPorTipoPaginado(tipo, pagina);
        }

        // 3. Listagem Geral (COM CACHE E PREFETCH)
        String chaveCache = pagina + "-30";
        PokemonListaResponseDTO resposta;

        if (cachePaginas.containsKey(chaveCache)) {
            // HIT: Retorna do cache instantaneamente
            resposta = cachePaginas.get(chaveCache);
        } else {
            // MISS: Busca agora (bloqueante)
            resposta = montarRequisicaoPaginadaComDetalhes("/pokemon", pagina, 30);
            if (resposta != null && resposta.results() != null && !resposta.results().isEmpty()) {
                cachePaginas.put(chaveCache, resposta);
            }
        }

        // Dispara prefetch da PRÓXIMA página em background
        CompletableFuture.runAsync(() -> carregarPaginaNoCache(pagina + 1, 30));

        return resposta;
    }

    // Método auxiliar seguro para carregar cache em background
    private void carregarPaginaNoCache(Integer pagina, Integer tamanho) {
        String chave = pagina + "-" + tamanho;

        // Só busca se ainda não tiver no cache
        if (!cachePaginas.containsKey(chave)) {
            try {
                PokemonListaResponseDTO dto = montarRequisicaoPaginadaComDetalhes("/pokemon", pagina, tamanho);

                if (dto != null && dto.results() != null && !dto.results().isEmpty()) {
                    cachePaginas.put(chave, dto);
                    System.out.println("📦 Página " + pagina + " adicionada ao cache.");
                }
            } catch (Exception e) {
                // Loga o erro mas não derruba a thread principal
                System.err.println("Erro silencioso no prefetch da página " + pagina + ": " + e.getMessage());
                // e.printStackTrace(); // Descomente se quiser ver o stacktrace completo no log
            }
        }
    }

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

    private PokemonListaResponseDTO buscarPokemonsPorTipoPaginado(String tipo, Integer pagina) {
        try {
            // 1. Busca o tipo completo na PokeAPI
            PokeApiTypeResponse typeResponse = restClient.get()
                    .uri("/type/{tipo}", tipo.toLowerCase())
                    .retrieve()
                    .body(PokeApiTypeResponse.class);

            if (typeResponse == null || typeResponse.pokemon() == null) {
                return new PokemonListaResponseDTO(0, null, null, List.of());
            }

            // 2. Paginação em Memória (A API retorna TODOS os pokémons do tipo de uma vez)
            List<PokeApiResumo> todosOsPokemons = typeResponse.pokemon().stream()
                    .map(TypePokemonEntry::pokemon)
                    .toList();

            int total = todosOsPokemons.size();
            int pageSize = 30;
            int fromIndex = pagina * pageSize;

            // Se a página solicitada estiver fora do alcance, retorna lista vazia
            if (fromIndex >= total) {
                return new PokemonListaResponseDTO(total, null, null, List.of());
            }

            int toIndex = Math.min(fromIndex + pageSize, total);
            List<PokeApiResumo> paginaResumos = todosOsPokemons.subList(fromIndex, toIndex);

            // 3. Hidratação Paralela (Busca detalhes de cada Pokémon da página)
            List<PokemonExternoDTO> listaDetalhada = paginaResumos.parallelStream()
                    .map(resumo -> {
                        try {
                            return findByNameOrIdOnPokeAPI(resumo.name());
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();

            // Gera strings de next/previous simuladas (opcional, apenas para manter padrão)
            String next = (toIndex < total) ? String.valueOf(pagina + 1) : null;
            String previous = (pagina > 0) ? String.valueOf(pagina - 1) : null;

            return new PokemonListaResponseDTO(total, next, previous, listaDetalhada);

        } catch (HttpClientErrorException.NotFound e) {
            throw new RegistroNaoEncontradoException("Tipo não encontrado: " + tipo);
        }
    }

    private record PokeApiTypeResponse(List<TypePokemonEntry> pokemon) {}

    private record TypePokemonEntry(PokeApiResumo pokemon) {}


    private PokemonListaResponseDTO montarRequisicaoPaginadaComDetalhes(String path, Integer pagina, Integer tamanhoPagina) {
        Integer offset = pagina * tamanhoPagina;

        try {
            // 1. Busca a lista "crua" (só nomes)
            PokeApiListResponse rawList = restClient.get()
                    .uri(uriBuilder -> {
                        uriBuilder.path(path).queryParam("limit", tamanhoPagina);

                        // ALTERAÇÃO: Só envia offset se for maior que 0
                        if (offset > 0) {
                            uriBuilder.queryParam("offset", offset);
                        }

                        return uriBuilder.build();
                    })
                    .retrieve()
                    .body(PokeApiListResponse.class);

            if (rawList == null || rawList.results() == null) {
                return new PokemonListaResponseDTO(0, null, null, List.of());
            }

            // 2. Hidratação paralela resiliente
            List<PokemonExternoDTO> listaDetalhada = rawList.results().parallelStream()
                    .map(resumo -> {
                        try {
                            return findByNameOrIdOnPokeAPI(resumo.name());
                        } catch (Exception e) {
                            return null; // Se um falhar, ignora e continua
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
        } catch (HttpServerErrorException e) {
            System.err.println("🚨 PokeAPI retornou erro 500: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("🚨 Erro ao buscar lista paginada: " + e.getMessage());
            // Retorna lista vazia em vez de quebrar tudo, se preferir
            return new PokemonListaResponseDTO(0, null, null, List.of());
        }
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
        if (!repository.existsById(id)) throw new RegistroNaoEncontradoException("Pokemon");
        if (batalhaRepository.existsBatalhaByPokemonId(id)){
            throw new DeleteNaoPermitidoException("Pokemon", Map.of(
                    "Motivo", "O pokemón já possui batalhas registradas",
                    "Solução", "Não é possível desvincular esse pokémon"
            ));
        }
        repository.deleteById(id);
    }

    // Records internos auxiliares para ler JSON parcial
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

    public List<Pokemon> findPokemonsById(List<Integer> id){
        List<Pokemon> pokemonsById = repository.findAllById(id);
        if (pokemonsById.isEmpty() || pokemonsById.size() < 2){
            throw new RequisicaoMalFeitaException("Devem ser informados no mínimo 2 pokemons");
        }
        return pokemonsById;
    }

    public void atualizarPokemon(Pokemon pokemon){
        Pokemon oldPokemon = repository.findById(pokemon.getId()).orElseThrow(()-> new RegistroNaoEncontradoException("Pokemon"));
        if (!oldPokemon.equals(pokemon)){
            repository.save(pokemon);
        }
    }
}