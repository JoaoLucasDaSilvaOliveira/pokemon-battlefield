INSERT INTO treinador (id, nome, level_experiencia, classe_treinador) VALUES
('a1b2c3d4-e5f6-7890-1234-567890abcdef', 'Ash Ketchum', 10, 'ASSASSINO'),
('f0e9d8c7-b6a5-4321-fedc-ba9876543210', 'Misty Waterflower', 8, 'GUERREIRO'),
('11223344-5566-7788-99aa-bbccddeeff00', 'Brock Rockstone', 9, 'ESTRATEGISTA');

INSERT INTO pokemon (id, nome_pokemon, pontos_vida, pontos_ataque, pontos_defesa, evolucao, tipo, id_treinador) VALUES
('00010001-0001-0001-0001-000100010001', 'Pikachu', 35, 55, 40, 'BASE', 'ELETRICO' , 'a1b2c3d4-e5f6-7890-1234-567890abcdef'),
('00020002-0002-0002-0002-000200020002', 'Charizard', 78, 84, 78, 'BASE', 'FOGO',  'a1b2c3d4-e5f6-7890-1234-567890abcdef'),
('00030003-0003-0003-0003-000300030003', 'Staryu', 30, 45, 55, 'EVO_I', 'TERRA',  'f0e9d8c7-b6a5-4321-fedc-ba9876543210'),
('00040004-0004-0004-0004-000400040004', 'Onix', 35, 45, 160, 'EVO_I', 'AGUA', '11223344-5566-7788-99aa-bbccddeeff00'),
('00050005-0005-0005-0005-000500050005', 'Bulbasaur', 45, 49, 49, 'EVO_II', 'AR', 'a1b2c3d4-e5f6-7890-1234-567890abcdef'),
('00060006-0006-0006-0006-000600060006', 'Gyarados', 95, 125, 79, 'EVO_III', 'AGUA', 'f0e9d8c7-b6a5-4321-fedc-ba9876543210'),
('00070007-0007-0007-0007-000700070007', 'Vulpix', 38, 41, 40, 'SHINE', 'TERRA','11223344-5566-7788-99aa-bbccddeeff00');

INSERT INTO GINASIO (ID, NOME, CAPACIDADE, BAIRRO) VALUES
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Ginasio de Pewter', 500, 'Montanhas Rochosas'),
('b1eebc99-9c0b-4ef8-bb6d-6bb9bd380b22', 'Arena Cerulean', 1200, 'Orla da Praia'),
('c2eebc99-9c0b-4ef8-bb6d-6bb9bd380c33', 'Estádio de Vermilion', 3000, 'Porto Industrial');

INSERT INTO BATALHA (ID, ID_GINASIO, ID_TREINADOR_VENCEDOR) VALUES
('d3eebc99-9c0b-4ef8-bb6d-6bb9bd380d44', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'a1b2c3d4-e5f6-7890-1234-567890abcdef'),
('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380e55', 'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380b22', 'f0e9d8c7-b6a5-4321-fedc-ba9876543210');

INSERT INTO BATALHA_POKEMONS (ID_BATALHA, ID_POKEMON) VALUES
('d3eebc99-9c0b-4ef8-bb6d-6bb9bd380d44', '00010001-0001-0001-0001-000100010001'),
('d3eebc99-9c0b-4ef8-bb6d-6bb9bd380d44', '00030003-0003-0003-0003-000300030003'),
('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380e55', '00010001-0001-0001-0001-000100010001'),
('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380e55', '00030003-0003-0003-0003-000300030003');