CREATE TABLE treinador (
                           id UUID PRIMARY KEY,
                           nome VARCHAR(150) NOT NULL,
                           level_experiencia INT DEFAULT 1,
                           classe_treinador VARCHAR(100) NOT NULL
);

CREATE TABLE pokemon (
                         id INT PRIMARY KEY,
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
                         bairro VARCHAR(150) NOT NULL,
                         background_image VARCHAR(250) NOT NULL
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


CREATE TABLE batalha_pokemons (
                                  id_batalha UUID NOT NULL,
                                  id_pokemon INT NOT NULL,

                                  CONSTRAINT id_batalha_FK FOREIGN KEY (id_batalha) REFERENCES batalha(id)
                                      ON DELETE NO ACTION
                                      ON UPDATE CASCADE,

                                  CONSTRAINT id_pokemon_FK FOREIGN KEY (id_pokemon) REFERENCES pokemon(id)
                                      ON DELETE NO ACTION
                                      ON UPDATE CASCADE
);

CREATE TABLE acao_pokemons (
                               id_pokemon INT,
                               nome_acao VARCHAR (100) NOT NULL,
                               valor_acao INT NOT NULL,
                               tipo_acao VARCHAR(30) NOT NULL,

                               CONSTRAINT id_pokemon_acao_FK FOREIGN KEY(id_pokemon) REFERENCES pokemon(id)
                                   ON DELETE CASCADE
                                   ON UPDATE CASCADE
);

CREATE TABLE pokemon_sprites (
                                 id_pokemon INT NOT NULL,
                                 sprite_key VARCHAR(100) NOT NULL,
                                 sprite_url VARCHAR(250) NOT NULL,

                                 CONSTRAINT id_pokemon_FK_sprites FOREIGN KEY(id_pokemon) REFERENCES pokemon(id)
                                     ON DELETE CASCADE
                                     ON UPDATE CASCADE
);

INSERT INTO GINASIO (ID, NOME, CAPACIDADE, BAIRRO, BACKGROUND_IMAGE) VALUES
                                                                         ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Ginasio de Pewter', 500, 'Montanhas Rochosas', 'https://qvlgkbkuawjapvmqkiqt.supabase.co/storage/v1/object/public/ginasio_gifs/1.gif'),
                                                                         ('b1eebc99-9c0b-4ef8-bb6d-6bb9bd380b22', 'Arena Cerulean', 1200, 'Orla da Praia', 'https://qvlgkbkuawjapvmqkiqt.supabase.co/storage/v1/object/public/ginasio_gifs/2.gif'),
                                                                         ('c2eebc99-9c0b-4ef8-bb6d-6bb9bd380c33', 'Estádio de Vermilion', 3000, 'Porto Industrial', 'https://qvlgkbkuawjapvmqkiqt.supabase.co/storage/v1/object/public/ginasio_gifs/3.gif'),
                                                                         ('d3eebc99-9c0b-4ef8-bb6d-6bb9bd380d44', 'Templo de Celadon', 2200, 'Jardins Floridos', 'https://qvlgkbkuawjapvmqkiqt.supabase.co/storage/v1/object/public/ginasio_gifs/4.gif'),
                                                                         ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380e55', 'Fortaleza de Fuchsia', 1800, 'Pântano Tóxico', 'https://qvlgkbkuawjapvmqkiqt.supabase.co/storage/v1/object/public/ginasio_gifs/5.gif'),
                                                                         ('f5eebc99-9c0b-4ef8-bb6d-6bb9bd380f66', 'Arena de Cinnabar', 4000, 'Vulcão Ativo', 'https://qvlgkbkuawjapvmqkiqt.supabase.co/storage/v1/object/public/ginasio_gifs/6.gif'),

('f3a4c5c1-9e92-4b3d-a6f0-1e7e0e0a9c44', 'Arena de Suprema', 10000, 'Vulcão Ativo', 'https://qvlgkbkuawjapvmqkiqt.supabase.co/storage/v1/object/public/ginasio_gifs/Banner%20Animado.gif');