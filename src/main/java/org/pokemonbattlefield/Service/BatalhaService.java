package org.pokemonbattlefield.Service;

import lombok.RequiredArgsConstructor;
import org.pokemonbattlefield.Repository.BatalhaRepository;
import org.pokemonbattlefield.Repository.TreinadorRepository;
import org.pokemonbattlefield.controller.dto.BatalhaDTO;
import org.pokemonbattlefield.controller.mapper.BatalhaMapper;
import org.pokemonbattlefield.exception.IdInvalidoException;
import org.pokemonbattlefield.exception.RegistroNaoEncontradoException;
import org.pokemonbattlefield.exception.RequisicaoMalFeitaException;
import org.pokemonbattlefield.model.Batalha;
import org.pokemonbattlefield.model.util.EvolucaoPokemon;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BatalhaService {

    private final BatalhaRepository repository;
    private final TreinadorRepository treinadorRepository;
    private final PokemonService pokemonService;
    private final GinasioService ginasioService;
    private final TreinadorService treinadorService;
    private final BatalhaMapper mapper;

    public UUID registrarBatalha(BatalhaDTO dto){
        Batalha batalha = mapper.deDtoParaBatalha(dto, pokemonService, treinadorService, ginasioService);
        if (batalha.getPokemonsBatalha().stream().noneMatch(pokemon-> pokemon.getTreinador().equals(batalha.getGanhador()))){
            throw new RequisicaoMalFeitaException("O treinador informado como ganhador não consta na batalha");
        }
        batalha.getPokemonsBatalha().forEach(pokemon -> {
            pokemon.setEvolucao(switch(pokemon.getQtdVitorias()) {
                case 0 -> EvolucaoPokemon.BASE;
                case 5 -> EvolucaoPokemon.EVO_I;
                case 10 -> EvolucaoPokemon.EVO_II;
                case 15 -> EvolucaoPokemon.EVO_III;
                case 20 -> EvolucaoPokemon.SHINE;
                default -> pokemon.getEvolucao();
            });
            double multiplicador = pokemon.getEvolucao().getMultiplicador();

            int ataque = pokemon.getPontosAtaque();
            int defesa = pokemon.getPontosDefesa();
            int vida = pokemon.getPontosVida();

            pokemon.setPontosAtaque((int)(ataque + (ataque*multiplicador)));
            pokemon.setPontosDefesa((int)(defesa + (defesa*multiplicador)));
            pokemon.setPontosVida((int)(vida + (vida*multiplicador)));

            pokemonService.atualizarPokemon(pokemon);
        });
        return repository.save(batalha).getId();
    }

    public List<Batalha> obterTodas(String idTreinador){
        if (idTreinador != null){
            return obterPorIdTreinador(idTreinador);
        }
        return repository.findAll();
    }

    public Batalha obterPorId(String id){
        return repository.findById(validaERetornaId(id)).orElseThrow(()-> new RegistroNaoEncontradoException("Batalha"));
    }

    public List<Batalha> obterPorIdTreinador (String idTreinador){
        UUID idTreinadorSanitizado = validaERetornaId(idTreinador);
        if (!treinadorRepository.existsTreinadorById(idTreinadorSanitizado)) throw new RegistroNaoEncontradoException("Treinador");
        return repository.findBatalhaByPokemonsBatalha_Treinador_Id(idTreinadorSanitizado);
    }

    public Map<String, Integer> obterDetalhesTreinador(String idTreinador){
        UUID idTreinadorSanitizado = validaERetornaId(idTreinador);
        if (!treinadorRepository.existsTreinadorById(idTreinadorSanitizado)) throw new RegistroNaoEncontradoException("Treinador");
        return detalhesTreinador(idTreinadorSanitizado);
    }

    private Map<String, Integer> detalhesTreinador(UUID idTreinador){
        int batalhas = repository.countDistinctByPokemonsBatalha_Treinador_Id(idTreinador);
        int vitorias = repository.countBatalhaByGanhador_Id(idTreinador);
        return Map.of(
                "Batalhas do treinador", batalhas,
                "Vitorias do treinador", vitorias,
                "Derrotas do treinador", batalhas - vitorias
        );
    }

    private UUID validaERetornaId(String id){
        try{
            return UUID.fromString(id);
        }catch (IllegalArgumentException e){
            throw new IdInvalidoException("UUID");
        }
    }
}
