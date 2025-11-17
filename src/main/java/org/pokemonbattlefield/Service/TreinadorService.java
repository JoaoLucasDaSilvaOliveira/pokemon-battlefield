package org.pokemonbattlefield.Service;

import lombok.AllArgsConstructor;
import org.pokemonbattlefield.Repository.PokemonRepository;
import org.pokemonbattlefield.Repository.TreinadorRepository;
import org.pokemonbattlefield.mapper.TreinadorMapper;
import org.pokemonbattlefield.model.Treinador;
import org.pokemonbattlefield.model.util.ClasseTreinador;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TreinadorService {

    private final TreinadorRepository treinadorRepository;
    private final TreinadorMapper mapper;
    private final PokemonRepository pokemonRepository;

    public void salvar(TreinadorDTO dto) {
        Treinador treinador = mapper.deDtoParaTreinador(dto);

        if(isDuplicated(treinador)){
            //todo Throw new Error
        }

        treinadorRepository.save(treinador);
    }

    public void delete(TreinadorDTO dto) {
        Treinador treinador = mapper.deDtoParaTreinador(dto);


    }


    public boolean isDuplicated(Treinador treinador) {
        return treinadorRepository.existsTreinadorByNomeIgnoreCaseAndClasseTreinadorIgnoreCase(treinador.getNome(), treinador.getClasseTreinador());
    }

    public boolean hasPokemon(Treinador treinador) {

    }
}
