package org.pokemonbattlefield.Service;

import lombok.AllArgsConstructor;
import org.pokemonbattlefield.Repository.PokemonRepository;
import org.pokemonbattlefield.Repository.TreinadorRepository;
import org.pokemonbattlefield.controller.dto.DetalhesTreinadorDTO;
import org.pokemonbattlefield.controller.dto.TreinadorDTO;
import org.pokemonbattlefield.controller.mapper.TreinadorMapper;
import org.pokemonbattlefield.controller.util.IdValidador;
import org.pokemonbattlefield.exception.DeleteNaoPermitidoException;
import org.pokemonbattlefield.exception.DuplicadoException;
import org.pokemonbattlefield.exception.IdInvalidoException;
import org.pokemonbattlefield.exception.RegistroNaoEncontradoException;
import org.pokemonbattlefield.model.Treinador;
import org.pokemonbattlefield.model.util.ClasseTreinador;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class TreinadorService implements IdValidador<UUID> {

    private static final String TIPO_ENTIDADE = "Treinador";
    private final TreinadorRepository treinadorRepository;
    private final TreinadorMapper mapper;
    private final PokemonRepository pokemonRepository;

    public Treinador salvar(TreinadorDTO dto) throws DuplicadoException {
        if(isDuplicated(dto, false)){
            throw DuplicadoException.createDuplicado(TIPO_ENTIDADE);
        }
        //NÃO PRECISAMOS PASSAR OS POKEMONS POIS NO CADASTRO ELE NÃO TERÁ POKEMONS
        Treinador treinador = mapper.deDtoParaTreinador(dto);
        return treinadorRepository.save(treinador);
    }

    public void delete(String id) {
        UUID idTreinador = validarERetornarId(id);
        if (pokemonRepository.existsPokemonByTreinadorId(idTreinador)){
            //NESSE CASO O TREINADOR QUE ESTAMOS TENTANDO EXCLUIR POSSUI POKEMONS
            throw new DeleteNaoPermitidoException(TIPO_ENTIDADE.toLowerCase(), Map.of(
                    "Motivo", "Treinador possui pokemons",
                    "Solução","Para desvincular o treinador é preciso desvincular os seus pokemons!"
                )
            );
        }
        if (!treinadorRepository.existsById(idTreinador)){
            throw new RegistroNaoEncontradoException(TIPO_ENTIDADE);
        }
        treinadorRepository.deleteById(idTreinador);
    }


    public boolean isDuplicated(TreinadorDTO dto, boolean update) {
        if (update){
            Treinador treinador = mapper.deDtoParaTreinador(dto);
            return treinadorRepository.existsTreinadorByNomeIgnoreCaseAndClasseTreinador(treinador.getNome(), treinador.getClasseTreinador());
        }
        Treinador treinador = mapper.deDtoParaTreinador(dto);
        return treinadorRepository.existsTreinadorByNomeIgnoreCase((treinador.getNome()));
    }

    public List<DetalhesTreinadorDTO> encontrarComFiltro(String nome, ClasseTreinador classe, List<String> pokemons) {
        if (nome == null && classe == null && (pokemons == null || pokemons.isEmpty())){
            //NESSE CASO RETORNAMOS TODOS OS TREINADORES CADASTRADOS
            List<Treinador> treinadores = treinadorRepository.findAll();
            if (treinadores.isEmpty()){
                throw new RegistroNaoEncontradoException(TIPO_ENTIDADE);
            }
            return treinadores
                    .stream()
                    .map(
                            mapper::toDetalhesDTO
                    ).toList();
        }
        //NESSE CASO TIVEMOS ALGUM QUERYPARAM
//        List<Treinador> treinadoresSet = treinadorRepository.findTreinadorByNomeIgnoreCaseOrClasseTreinador(nome, classe, pokemons.stream().map(String::toUpperCase).toList());
        Set<Treinador> treinadoresSet = new LinkedHashSet<>();
        if (nome != null || classe != null){
            treinadoresSet.addAll(treinadorRepository.findTreinadorByNomeIgnoreCaseOrClasseTreinador(nome, classe));
        }
        if (pokemons != null && !pokemons.isEmpty()){
            pokemons
                    .forEach(
                            nomePokemon ->{
                                treinadoresSet.addAll(treinadorRepository.findTreinadorByNomePokemon(nomePokemon));
                            }
                    );
        }
        List<Treinador> treinadores = new ArrayList<>(treinadoresSet);
        if (!treinadores.isEmpty()){
            return treinadores
                    .stream()
                    .map(
                            mapper::toDetalhesDTO
                    ).toList();
        }
        throw new RegistroNaoEncontradoException(TIPO_ENTIDADE);
    }

    public Treinador obterPorId(String id) {
        return treinadorRepository.findById(validarERetornarId(id)).orElseThrow(() -> new RegistroNaoEncontradoException(TIPO_ENTIDADE));
    }

    public void atualizar(TreinadorDTO dto, String id) {
        if(isDuplicated(dto, true)){
            throw DuplicadoException.updateDuplicado(TIPO_ENTIDADE);
        }
        UUID idTreinador = validarERetornarId(id);
        Treinador oldTreinador = treinadorRepository.findById(idTreinador).orElseThrow(() -> new RegistroNaoEncontradoException(TIPO_ENTIDADE));
        Treinador treinadorAtualizado = mapper.deDtoParaTreinador(dto, oldTreinador);
        treinadorRepository.save(treinadorAtualizado);
    }

    @Override
    public UUID validarERetornarId(String id) {
        try {
            return UUID.fromString(id);
        } catch(IllegalArgumentException e){
            throw new IdInvalidoException("UUID");
        }
    }
}
