package org.pokemonbattlefield.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.pokemonbattlefield.Service.PokemonService;
import org.pokemonbattlefield.controller.dto.ApiPokemon.PokeTypesResponse;
import org.pokemonbattlefield.controller.dto.ApiPokemon.PokemonListaResponseDTO;
import org.pokemonbattlefield.controller.dto.CadastrarPokemonDTO;
import org.pokemonbattlefield.controller.dto.PokemonExternoDTO;
import org.pokemonbattlefield.controller.util.URIConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pokemon")
@RequiredArgsConstructor
public class PokemonController {

    private static final String VALOR_DEFAULT_PAGINA = "0";
    private final PokemonService service;

    @GetMapping
    public ResponseEntity<PokemonListaResponseDTO> obterPokemonsComFiltroEPaginavel (
            //FILTROS QUE PODEM VIR DO FRONT-END
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "tipo", required = false) String tipo,
            //PAGINAÇÃO
            @RequestParam(value = "pagina", defaultValue = VALOR_DEFAULT_PAGINA) Integer pagina
    ){
        PokemonListaResponseDTO pokemonListaResponseDTO = service.findPokemonsOnPokeAPI(nome, tipo, pagina);
        return new ResponseEntity<>(pokemonListaResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/tipo")
    public ResponseEntity<PokeTypesResponse> obterTiposDosPokemons(){
        return new ResponseEntity<>(service.todosOsTiposDosPokemons(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PokemonExternoDTO> obterPokemonPorId(
            @PathVariable @Positive(message = "O id deve ser positivo") String id
    ){
        return new ResponseEntity<>(service.findByNameOrIdOnPokeAPI(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> salvarPokemon (@RequestBody @Valid CadastrarPokemonDTO dto){
        Integer idPokemonSalvo = service.vincular(dto);
        return ResponseEntity.created(URIConfigurer.createLocation(idPokemonSalvo)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPokemon(@PathVariable Integer id){
        service.desvincular(id);
        return ResponseEntity.noContent().build();
    }

}
