CREATE TABLE treinador (
                           id UUID PRIMARY KEY,
                           nome VARCHAR(150) NOT NULL,
                           level_experiencia INT DEFAULT 1,
                           classe_treinador VARCHAR(100) NOT NULL
);

CREATE TABLE pokemon (
                         id UUID PRIMARY KEY,
                         nome_pokemon VARCHAR(100) NOT NULL,
                         pontos_vida INT NOT NULL,
                         pontos_ataque INT NOT NULL,
                         pontos_defesa INT NOT NULL,
                         evolucao VARCHAR(100) DEFAULT 'BASE',
                         id_treinador UUID,
                         tipo VARCHAR(100) NOT NULL,

                         CONSTRAINT id_treinador_fk FOREIGN KEY (id_treinador) REFERENCES treinador (id)
                             ON DELETE NO ACTION
                             ON UPDATE CASCADE
);

-- ALTER TABLE pokemon ADD COLUMN tipo VARCHAR(150);
-- ALTER TABLE pokemon ALTER COLUMN  tipo SET NOT NULL;

CREATE TABLE ginasio (
                         id UUID PRIMARY KEY,
                         nome VARCHAR(150) NOT NULL,
                         capacidade INT NOT NULL,
                         bairro VARCHAR(150) NOT NULL
);

CREATE TABLE batalha (
                         id UUID PRIMARY KEY,
                         id_ginasio UUID NOT NULL,
                         id_treinador_vencedor UUID NOT NULL,

                         CONSTRAINT id_ginasio_FK FOREIGN KEY (id_ginasio) REFERENCES ginasio(id)
                             ON DELETE NO ACTION
                             ON UPDATE CASCADE,

                         CONSTRAINT id_treinador_vencedor_FK FOREIGN KEY (id_treinador_vencedor) REFERENCES treinador(id)
                             ON DELETE NO ACTION
                             ON UPDATE CASCADE
);

-- DROP TABLE batalha;
-- DROP TABLE batalha_pokemons;

CREATE TABLE batalha_pokemons (
                                  id_batalha UUID NOT NULL,
                                  id_pokemon UUID NOT NULL,

                                  CONSTRAINT id_batalha_FK FOREIGN KEY (id_batalha) REFERENCES batalha(id)
                                      ON DELETE NO ACTION
                                      ON UPDATE CASCADE,

                                  CONSTRAINT id_pokemon_FK FOREIGN KEY (id_pokemon) REFERENCES pokemon(id)
                                      ON DELETE NO ACTION
                                      ON UPDATE CASCADE
);