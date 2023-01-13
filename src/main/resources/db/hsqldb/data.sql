INSERT INTO users(username,password,enabled,name,email,birthdate) VALUES ('admin1','4dm1n',TRUE, 'Admin', 'admin@email.com', '2002-04-08');
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');

INSERT INTO users(username,password,enabled,name,email,birthdate) VALUES ('manotebar','manotebar',TRUE,'Manuel', 'manotebar@gmail.com', '2000-10-20');
INSERT INTO authorities(id,username,authority) VALUES (2,'manotebar','admin');

INSERT INTO users(username,password,enabled,name,email,birthdate) VALUES ('daviddhc','player1',TRUE,'David', 'daviddhc@gmail.com', '2002-04-08');
INSERT INTO authorities(id,username,authority) VALUES (3,'daviddhc','player');

INSERT INTO users(username,password,enabled,name,email,birthdate) VALUES ('anddomrui','hola3',TRUE, 'Andres', 'andres@gmail.com', '2002-04-08');
INSERT INTO authorities(id,username,authority) VALUES (4,'anddomrui','player');

INSERT INTO users(username,password,enabled,name,email,birthdate) VALUES ('melae','secret1',TRUE, 'Javier', 'javi@gmail.com', '2002-04-08');
INSERT INTO authorities(id,username,authority) VALUES (5,'melae','admin');

INSERT INTO users(username,password,enabled,name,email,birthdate) VALUES ('mannnnn','manotebar',TRUE,'Manuel', 'mantbar@gmail.com', '2000-10-20');
INSERT INTO authorities(id,username,authority) VALUES (6,'mannnnn','admin');

INSERT INTO users(username,password,enabled,name,email,birthdate) VALUES ('daviddhcc','player1',TRUE,'David', 'davdhc@gmail.com', '2002-04-08');
INSERT INTO authorities(id,username,authority) VALUES (7,'daviddhcc','player');

INSERT INTO users(username,password,enabled,name,email,birthdate) VALUES ('anddomruii','hola3',TRUE, 'Andres', 'ands@gmail.com', '2002-04-08');
INSERT INTO authorities(id,username,authority) VALUES (8,'anddomruii','player');

INSERT INTO users(username,password,enabled,name,email,birthdate) VALUES ('javfercas3','secret1',TRUE, 'Javier', 'jai@gm.com', '2002-04-08');
INSERT INTO authorities(id,username,authority) VALUES (9,'javfercas3','admin');

INSERT INTO users(username,password,enabled,name,email,birthdate) VALUES ('anddoii','hola3',TRUE, 'Andres', 'as@gmail.com', '2002-04-08');
INSERT INTO authorities(id,username,authority) VALUES (10,'anddoii','player');

INSERT INTO users(username,password,enabled,name,email,birthdate) VALUES ('jaas3','secret1',TRUE, 'Javier', 'ai@gm.com', '2002-04-08');
INSERT INTO authorities(id,username,authority) VALUES (11,'jaas3','admin');

-- MAPS
INSERT INTO map_templates(id,name,description) VALUES (1,'Mapa Original','Este es el mapa real del juego.<br> Tiene las 26 ciudades y los 40 caminos <br> y al rededor de 140 posiciones jugables!');


-- CITIES
INSERT INTO city_templates(id,map_template_id,x,y,unaligned,capacity,name,vp_endgame_value,starting_city,zone,marker_vp,marker_influence) VALUES --ORIGINAL MAP
(1,1,0,1,'0,1',3,'Gauntlgrym',2,FALSE,1,1,1),
(2,1,0,0,'',3,'Los Retuercegusanos',3,FALSE,1,0,0),
(3,1,0,2,'',4,'Jhachalkhyn',4,TRUE,1,0,0),
(4,1,0,3,'0',3,'Buiyrandyn',3,FALSE,1,0,0),
(5,1,0,4,'0,1',2,'Bastion del clan stoneshaft',4,FALSE,1,0,0),
(6,1,0,5,'0,1',3,'Ch`chitil',2,FALSE,1,1,1),
(7,1,1,0,'0,1',2,'Blingdenstone',4,FALSE,2,0,0),
(8,1,1,1,'0,1',5,'Mantol-Delrith',4,TRUE,2,0,0),
(9,1,1,2,'0,1',4,'Gracklstugh',3,FALSE,2,0,0),
(10,1,1,3,'0',3,'El laberinto',3,FALSE,2,0,0),
(11,1,1,4,'0,1',5,'Skullport',4,TRUE,2,0,0),
(12,1,1,5,'',3,'Kanaglym',3,FALSE,2,0,0),
(13,1,2,1,'0,1,2',6,'Menzoberranzan',5,FALSE,2,2,1),
(14,1,2,2,'',1,'Puente de Chasmleap',1,FALSE,2,0,0),
(15,1,2,3,'0,1,2,3',4,'Aramuycos',3,FALSE,2,3,1),
(16,1,2,4,'',3,'Eryndlyn',3,TRUE,2,0,0),
(17,1,2,5,'0,1,2',3,'Tsenviilyq',4,FALSE,2,1,1),
(18,1,3,2,'',3,'Fuego Eterno',3,FALSE,2,0,0),
(19,1,3,3,'1',2,'Salones de la Legion Flagelante',3,FALSE,2,0,0),
(20,1,3,4,'',4,'Ched Nasad',3,TRUE,2,0,0),
(21,1,3,5,'',2,'Llacerellyn',2,FALSE,2,0,0),
(22,1,4,2,'',5,'Chaulssin',4,TRUE,3,0,0),
(23,1,5,2,'0,1',3,'El Phaerlin',2,FALSE,3,1,1),
(24,1,4,3,'0,1',2,'Yathchol',4,FALSE,3,0,0),
(25,1,4,4,'0,1',6,'Ruinas De Dekanter',5,FALSE,3,0,0),
(26,1,4,5,'0,1',3,'SS`zuraass`nee',2,FALSE,3,1,1);



-- Original map paths with white troops
INSERT INTO path_templates(id,unaligned,city_id_1,city_id_2,capacity,map_template_id) VALUES 
(11,'0',7,8,1,1),(17,'1',10,15,2,1),(23,'0',14,15,1,1),(25,'0',15,16,1,1),(26,'0',15,20,1,1);
--Original map paths
INSERT INTO path_templates(id,city_id_1,city_id_2,capacity,map_template_id) VALUES 
(1,1,2,0,1),(2,1,3,1,1),(3,2,8,1,1),(4,3,9,1,1),(5,3,4,1,1),(6,4,5,1,1),(7,4,10,1,1),(8,5,6,1,1),(9,5,11,2,1),(10,6,12,1,1),
(12,8,9,1,1),(13,8,13,3,1),(14,9,10,1,1),(15,9,14,2,1),(16,10,11,2,1),(18,11,12,1,1),
(19,12,16,2,1),(20,12,17,2,1),(21,13,14,2,1),(22,13,18,2,1),(24,14,18,1,1),(27,16,21,1,1),(28,17,21,1,1),(29,18,19,1,1),
(30,18,22,1,1),(31,19,20,2,1),(32,19,24,1,1),(33,20,21,2,1),(34,20,24,1,1),(35,20,25,1,1),
(36,21,26,2,1),(37,22,23,1,1),(38,23,24,1,1),(39,23,25,2,1),(40,25,26,1,1);


--HOUSES (For players)
INSERT INTO house(id,name,hex_color) VALUES (0,'Unaligned','#dddddd');
INSERT INTO house(id,name,hex_color) VALUES (1,'Baerne','#81ed05');
INSERT INTO house(id,name,hex_color) VALUES (2,'Mizzrym','#ff9922');
INSERT INTO house(id,name,hex_color) VALUES (3,'Xorlarrin','#09e4e8');
INSERT INTO house(id,name,hex_color) VALUES (4,'Barrison del"armgo','#ff2222');
-- HALFDECK
INSERT INTO halfdecks(id,name,description) VALUES (1,'Drow','El mazo Drow presenta cartas optimizadas con costes de influencia mas bajos.');
INSERT INTO halfdecks(id,name,description) VALUES (2,'Dragons','El mazo Dragones tiene muchas cartas de alto coste de influencia, y cuenta con 5 Dragones que te recompensaran por seguir una estrategia.');
INSERT INTO halfdecks(id,name,description) VALUES (3,'Inicial','Cartas iniciales de los jugadores');
INSERT INTO halfdecks(id,name,description) VALUES (4,'Basica','Cartas basicas disponibles en el mercado de todas las partidas');
INSERT INTO halfdecks(id,name,description) VALUES (5,'Mazo con 3 cartas','Mazo de testing, no tiene todas la cartas');
INSERT INTO halfdecks(id,name,description) VALUES (6,'Segundo mazo con 3 cartas','Mazo de testing, no tiene todas las cartas');

--UNALIGED PLAYER (WHITE TROOPS)
INSERT INTO players(id,name,house_id) VALUES (0, 'Unaligned Enemy',0);

-- GAMES
INSERT INTO games(id,date,name,map_template_id,automatic,creator) VALUES  (1,'2022-04-08','Partida mapa original',1,FALSE,'anddomrui');
INSERT INTO players(id,name,username,house_id,game_id) VALUES (1, 'P1 daviddhc', 'daviddhc',1,1);
INSERT INTO players(id,name,username,house_id,game_id) VALUES (2, 'P2 anddomrui', 'anddomrui',2,1);
INSERT INTO players(id,name,house_id,game_id) VALUES (3, 'P3 manotebar',3,1);
INSERT INTO players(id,name,house_id,game_id) VALUES (4, 'P4 javiFdz',4,1);


INSERT INTO games(id,date,name,map_template_id,automatic,creator) VALUES (2,'2022-04-09','Partida ZONA 2 y 3',1,FALSE,'javiFdz');
INSERT INTO players(id,name,house_id,game_id) VALUES (5, 'Player1 javiTopG',1,2);
INSERT INTO players(id,name,house_id,game_id) VALUES (6, 'Player2 davidZ',2,2);

INSERT INTO games(id,date,name,map_template_id,first_half_deck_id,second_half_deck_id,automatic,creator) VALUES (3,'2022-04-09','Solo zona 2 ',1,1,2,TRUE,'admin1');
INSERT INTO players(id,name,house_id,game_id) VALUES (7, 'Player1',1,3);
INSERT INTO players(id,name,house_id,game_id) VALUES (8, 'Player2',2,3);

INSERT INTO games(id,date,name,map_template_id,first_half_deck_id,second_half_deck_id,automatic,finished,creator) VALUES (5,'2022-04-09','Partida acabada 1',1,1,2,FALSE,TRUE,'admin1');
INSERT INTO players(id,name,house_id,game_id) VALUES (11, 'Player1',1,5);
INSERT INTO players(id,name,house_id,game_id) VALUES (12, 'Player2',2,5);

INSERT INTO games(id,date,name,map_template_id,first_half_deck_id,second_half_deck_id,automatic,finished,creator) VALUES (6,'2022-04-09','Partida acabada 2',1,1,2,FALSE,TRUE,'admin1');
INSERT INTO players(id,name,house_id,game_id) VALUES (13, 'Player1',1,6);
INSERT INTO players(id,name,house_id,game_id) VALUES (14, 'Player2',2,6);

INSERT INTO games(id,date,name,map_template_id,automatic,creator, finished) VALUES  (8,'2022-04-08','Partida mapa original',1,FALSE,'anddomrui', TRUE);
INSERT INTO players(id,name,username,house_id,game_id) VALUES (15, 'P1 daviddhc', 'daviddhc',1,8);
INSERT INTO players(id,name,username,house_id,game_id) VALUES (16, 'P2 anddomrui', 'anddomrui',2,8);
INSERT INTO players(id,name,house_id,game_id) VALUES (17, 'P3 manotebar',3,8);
INSERT INTO players(id,name,house_id,game_id) VALUES (18, 'P4 javiFdz',4,8);

INSERT INTO games(id,date,name,map_template_id,automatic,creator, finished) VALUES  (9,'2022-04-08','Partida mapa original',1,FALSE,'anddomrui', TRUE);
INSERT INTO players(id,name,username,house_id,game_id) VALUES (19, 'P1 daviddhc', 'daviddhc',1,9);
INSERT INTO players(id,name,username,house_id,game_id) VALUES (20, 'P2 anddomrui', 'anddomrui',2,9);
INSERT INTO players(id,name,house_id,game_id) VALUES (21, 'P3 manotebar',3,9);
INSERT INTO players(id,name,house_id,game_id) VALUES (22, 'P4 javiFdz',4,9);

INSERT INTO games(id,date,name,map_template_id,automatic,creator, finished) VALUES  (10,'2022-04-08','Partida mapa original',1,FALSE,'anddomrui', FALSE);
INSERT INTO players(id,name,username,house_id,game_id) VALUES (23, 'P1 daviddhc', 'daviddhc',1,10);
INSERT INTO players(id,name,username,house_id,game_id) VALUES (24, 'P2 anddomrui', 'anddomrui',2,10);
INSERT INTO players(id,name,house_id,game_id) VALUES (25, 'P3 manotebar',3,10);
INSERT INTO players(id,name,house_id,game_id) VALUES (26, 'P4 javiFdz',4,10);

-- ACTIONS USED TO FORM COMPLEX ACTIONS
INSERT INTO actions(id,original_iterations,action_name,value,description) VALUES 
    
    (1,1,'RETURN_PLAYER_PIECE',null,'Devuelve una tropa o espia enemigo'),
    (2,1,'PROMOTE_OWN_PLAYED_CARD',null,'Asciende una carta jugada'),
    (3,1,'INFLUENCE',2,'+2 de influencia'),
    (4,1,'KILL_ENEMY_TROOP',null,'Asesina una tropa enemiga'),
    (5,1,'POWER',2,'+2 de poder'),
    (6,2,'PLACE_OWN_SPY',null,'Pon 2 espias'),
    (7,1,'RETURN_OWN_SPY',null,'Devuelve uno de tus espias'),
    (8,4,'DEPLOY_OWN_TROOP',null,'Despliega 4 tropas'),
    (9,1,'SUPPLANT_WHITE_TROOP',null,'Suplanta una tropa blanca'),
    (10,1,'DEPLOY_OWN_TROOP',null,'Despliega una tropa'),
    (11,1,'KILL_WHITE_TROOP',null,'Asesina una tropa blanca'),
    (12,2,'MOVE_ENEMY_TROOP',null,'Mueve hasta 2 tropas enemigas'),
    (13,1,'PLACE_OWN_SPY',null,'Pon un espia'),
    (14,1,'POWER',4,'+4 de poder'),
    (15,1,'THEN',null,'Entonces'),
    (16,1,'MOVE_OWN_DECK_CARDS_TO_DISCARDED',null,'Pon tu mazo en la pila de descartes.'),
    (17,1,'PROMOTE_OWN_DISCARDED_CARD',null,'Asciende una carta de tu pila de descartes.'),
    (18,1,'THEN',null,'Entonces'),
    (19,1,'DRAW_CARD',3,'Roba 3 cartas.'),
    (20,1,'THEN',null,'Entonces'),
    (21,1,'SUPPLANT_ENEMY_TROOP_IN_SITE',null,'Suplanta una tropa en la misma ubicacion.'),
    (22,1,'THEN',null,'Entonces'),
    (23,1,'CHECK_INNER_CARDS_GREATER_THAN',3,'Si hay 4 o mas cartas en tu ciculo interno,'),
    (24,1,'INFLUENCE',3,'+3 de influencia.'),
    (25,1,'THEN',null,'Entonces'),
    (26,1,'THEN',null,'Entonces'),
    (27,1,'THEN',null,'Entonces'),
    (28,1,'VP_FOR_EVERY_SITE_MARKER',null,'Obten 1 punto de victoria por cada marcador de control.'),
    (29,1,'MOVE_ENEMY_TROOP',null,'Mueve 1 tropa enemiga.'),
    (30,1,'DRAW_CARD',2,'Roba 2 cartas.'),
    (31,1,'INFLUENCE',1,'+1 de influencia'),
    (32,2,'PROMOTE_OWN_PLAYED_CARD',null,'Asciende 2 cartas jugadas en este turno.'),
    (33,1,'VP_FOR_EVERY_3_CARDS_IN_INNER',null,'Obten 1 punto de victoria por cada 3 cartas ascendidas que esten en tu circulo interno.'),
    (34,1,'THEN',null,'Entonces'),
    (35,1,'CHECK_KILLED_PLAYER_TROOPS_GREATER_THAN',4,'Si tienes 5 o mas tropas asesinadas en tu sala de trofeos,'),
    (36,1,'THEN',null,'Entonces'),
    (37,1,'CHECK_PLAYER_ANY_TROOP_IN_SITE',1,'Si hay alguna tropa de otro jugador en esta ubicacion.'),
    (38,1,'SUPPLANT_WHITE_TROOP_ANYWHERE',null,'Suplanta una tropa blanca que este en cualquier lugar del mapa.'),
    (39,1,'VP_FOR_EVERY_3_WHITE_KILLED_TROOPS',null,'Obten 1 punto de victoria por cada 3 tropas blancas que haya en tu sala de trofeos.'),
    (40,1,'DEVORE_MARKET_CARD',null,'Devora una carta del mercado.'),
    (41,2,'DEPLOY_OWN_TROOP',null,'Despliega 2 tropas.'),
    (42,1,'THEN',null,'Entonces'),
    (43,1,'THEN',null,'Entonces'),
    (44,3,'DEPLOY_OWN_TROOP',null,'Despliega 3 tropas.'),
    (45,1,'VP_FOR_EVERY_2_CONTROLED_SITES',null,'Obten 1 punto de victoria por cada 2 ubicaciones que controles.'),
    (46,1,'SUPPLANT_ENEMY_TROOP',null,'Suplanta una tropa enemiga.'),
    (47,1,'VP_FOR_EVERY_TOTAL_CONTROLLED_SITE',null,'Obten un punto de victoria por cada ubicacion bajo tu control total.'),
    (48,1,'POWER',1,'+1 de poder'),
    (49,1,'AT_END_TURN',null,'Al final del turno, '),
    (50,1,'THEN',null,'Entonces '),
    (51,1,'AT_END_TURN',null,'Al final del turno, '),
    (52,1,'AT_END_TURN',null,'Al final del turno, '),
    (53,1,'AT_END_TURN',null,'Al final del turno, '),
    (54,1,'AT_END_TURN',null,'Al final del turno, '),
    (55,1,'AT_END_TURN',null,'Al final del turno, '),
    (56,1,'AT_END_TURN',null,'Al final del turno, '),
    (57,1,'RETURN_PLAYER_SPY',null,'Devuelve 1 espia enemigo.')
    ;

--CARD ACTIONS
INSERT INTO actions(id,original_iterations,action_name,value,description) VALUES 
    
    (100,1,'POWER',1,'+1 de poder'),--Soldado HECHA Y COMPROBADA
    (101,1,'INFLUENCE',1,'+1 de influencia'),--Noble HECHA Y COMPROBADA
    (102,1,'INFLUENCE',2,'+2 de influencia'),--Lolth HECHA Y COMPROBADA
    (103,1,'POWER',2,'+2 de poder'),--Guardia HECHA Y COMPROBADA
    (104,3,'DEPLOY_OWN_TROOP',null,'Despliega 3 tropas'),--Cuadrilla de mercenarios HECHA Y COMPROBADA
    (105,1,'PLACE_OWN_SPY',null,'Pon un espia'),--Maestra de espias HECHA Y COMPROBADA
    (106,1,'CHOOSE',null,'Elige una opcion: '),--Adalid HECHA Y COMPROBADA
    (107,1,'CHOOSE',null,'Elige una opcion: '),--Inquisidora HECHA Y COMPROBADA
    (108,1,'CHOOSE',null,'Elige una opcion'),--Guardia Negro HECHA Y COMPROBADA
    (109,1,'SUPPLANT_WHITE_TROOP',null,'Suplanta 1 tropa blanca.'),--Rastreadora de avanzadilla HECHA Y COMPROBADA
    (110,2,'KILL_WHITE_TROOP',null,'Asesina 1 tropa blanca.'),--Explorador del UnderDark HECHA Y COMPROBADA
    (111,1,'ALL',null,''),--Elegida de Lolth HECHA Y COMPROBADA
    (112,1,'POWER',3,'+3 de poder.'),--Cazarrecompensas HECHA Y COMPROBADA
    (113,1,'SUPPLANT_ENEMY_TROOP',null,'Suplanta 1 tropa enemiga.'),--Doppelganger HECHA Y COMPROBADA
    (114,1,'CHOOSE',null,'Eliga una opcion: '),--Maestros de Sorcere HECHA Y COMPROBADA
    (115,1,'CHOOSE',null,'Elige una opcion: '),--Maestro de Melee-Magthere HECHA Y COMPROBADA
    (116,1,'THEN',null,'Entonces '),--Infiltrador HECHA Y COMPROBADA
    (117,3,'CHOOSE',null,'Elige 3 veces: '),--Maestro de armas HECHA Y COMPROBADA
    (118,2,'KILL_ENEMY_TROOP',null,'Asesina 2 tropas enemigas.'),--Filo letal HECHA Y COMPROBADA
    (119,1,'ALL',null,''),--Miembro del consejo HECHA Y COMPROBADA
    (120,1,'CHOOSE',null,'Elige una opcion: '),--Hilador de conjuros HECHA Y COMPROBADA
    (121,1,'THEN',null,'Entonces'),--Matrona HECHA Y COMPROBADA
    (122,1,'CHOOSE',null,'Elige una opcion: '),--Traficante de informacion HECHA Y COMPROBADA
    (123,1,'ALL',null,''),--Negociadora Drow HECHA Y COMPROBADA
    (124,1,'CHOOSE',null,'Elige una opcion: '),--Kobold HECHA Y COMPROBADA
    (125,1,'CHOOSE',null,'Elige una opcion: '),--Sectario del Culto del Dragon HECHA Y COMPROBADA
    (126,1,'CHOOSE',null,'Elige una opcion: '),--Dragon verde
    (127,1,'ALL',null,''),--Clerigo de Laogzed HECHA Y COMPROBADA
    (128,1,'ALL',null,''),--Rath Modar HECHA Y COMPROBADA
    (129,1,'ALL',null,''),--Cria de dragon negro HECHA Y COMPROBADA
    (130,1,'ALL',null,''),--Voz de dragon HECHA Y COMPROBADA
    (131,1,'THEN',null,'Entonces'),--Dragon azul HECHA Y COMPROBADA
    (132,1,'THEN',null,'Entonces'),--Garra de dragon HECHA Y COMPROBADA
    (133,1,'ALL',null,''),--Cria de dragon verde HECHA Y COMPROBADA
    (134,1,'ALL',null,''),--Dragon negro HECHA Y COMPROBADA
    (135,1,'ALL',null,''),--Cria de dragon rojo HECHA Y COMPROBADA
    (136,1,'ALL',null,''),--Sectario fanatico HECHA Y COMPROBADA
    (137,1,'ALL',null,''),--Cria de dragon blanco
    (138,1,'CHOOSE',null,'Elige una opcion: '),--Vigilante de Thav
    (139,1,'ALL',null,''),--Cria de dragon azul HECHA Y COMPROBADA
    (140,1,'POWER',5,'+5 de poder'),--Severin Silrajin HECHA Y COMPROBADA
    (141,1,'CHOOSE',null,'Elige una opcion: '),--Encantador de Thav
    (142,1,'ALL',null,''),--Dragon blanco
    (143,1,'ALL',null,'')--Dragon rojo
    ;

INSERT INTO subactions(action_id,subaction_id) VALUES 
    (15,7),
    (15,14),
    (18,7),
    (18,19),
    (20,7),
    (20,21),
    (22,23),
    (22,24),
    (25,13),
    (25,21),
    (26,7),
    (26,27),
    (27,21),
    (27,28),
    (34,35),
    (34,5),
    (36,37),
    (36,3),
    (42,7),
    (42,24),
    (43,7),
    (43,14),
    (49,2),
    (50,37),
    (50,48),
    (51,2),
    (52,2),
    (53,32),
    (53,33),
    (54,2),
    (55,2),
    (56,2),
    (106,3),
    (106,51),
    (107,3),
    (107,4),
    (108,5),
    (108,4),
    (111,1), --Name of Card if is the top of the tree
    (111,52),
    (114,6),
    (114,15),
    (115,8),
    (115,9),
    (116,13),
    (116,50),
    (117,10),
    (117,11),
    (119,12),
    (119,49),
    (120,13),
    (120,20),
    (121,16),
    (121,17),
    (122,13),
    (122,18),
    (123,22),
    (123,56),
    (124,10),
    (124,11),
    (125,3),
    (125,5),
    (126,25),
    (126,26),
    (127,29),
    (127,55),
    (128,30),
    (128,13),
    (129,31),
    (129,11),
    (130,31),
    (130,54),
    (131,53),
    (132,4),
    (132,34),
    (133,13),
    (133,36),
    (134,38),
    (134,39),
    (135,5),
    (135,3),
    (136,3),
    (136,40),
    (137,41),
    (137,40),
    (138,13),
    (138,42),
    (139,24),
    (139,1),
    (141,13),
    (141,43),
    (142,44),
    (142,45),
    (143,46),
    (143,57),
    (143,47)
    ;


-- ASPECTS
INSERT INTO aspects(id,name,description,image) VALUES (1,'Ambicion','Los siervos de ambicion son los mejores para reclutar a otros siervos y crear un circulo interno fuerte.','');
INSERT INTO aspects(id,name,description,image) VALUES (2,'Conquista','Los siervos de conquista son los mejores para apoderarse de la Infraoscuridad.','');
INSERT INTO aspects(id,name,description,image) VALUES (3,'Malicia','Los siervos de malicia son flexibles y son los mejores en el asesinato.','');
INSERT INTO aspects(id,name,description,image) VALUES (4,'Astucia','Los siervos de astucia son los mejores para espiar e interrumpir el control.','');
INSERT INTO aspects(id,name,description,image) VALUES (5,'Obediencia','Los siervos de obediencia realizan las tareas del dia a dia.','');





INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES      
        (0,'Espaciador',0,'','',0,0,0,3,5);

-- CARDS
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id,action_id) VALUES      
            ('Soldado',0,'Los soldados defienden a sus superiores contra todos los enemigos','+1 de poder',0,1,0,3,5,100),
            ('Noble',0,'La voz de un drow de una casa noble tiene el peso de la de mil plebeyos','+1 de influencia',0,1,0,3,5,101),
            ('Sacerdotisa de Lolth',2,'Elegidas por su devocion a Lolth, ahora hacen valer su voluntad','+2 de influencia',1,2,0,4,5,102),
            ('Guardia de la casa',3,'El cuerpo de guardia de una casa se nutre de los pocos drow que sobreviven a la instruccion','+2 de poder',1,3,0,4,5,103),
            ('Cuadrilla de mercenarios',3,'La unica otra opcion que les queda a quienes carecen de casa es el exilio.','Despliega 3 tropas.',1,4,2,1,2,104),
            ('Maestra de espias',2,'<<Puedo ayudarte a sortear las puertas y los guardias. Lo que hagas despues es cosa tuya>>','Pon un espia',1,2,2,1,4,105),
            ('Adalid',2,'<<Codicia, avaricia y poder: tres idiomas que entiendo a la perfeccion>>','Elige una Opcion: + 2 Influencia | Al final del turno, asciende otra carta jugada durante este turno',1,2,4,1,1,106), 
            ('Inquisidora',3,'<<Si con mi servicio puedo complacer a la Diosa y traer honor a mi casa, me doy por satisfecha>>','Elige una Opcion: + 2 Influencia | Asesina tropa',2,4,1,1,3,107),
            ('Guardia Negro',3,'Patrullan el Underdark, exterminando a intrusos perdidos, inconscientes y temerarios.','Elige una Opcion: + 2 poder| Asesina tropa',1,3,4,1,3,108),
            ('Rastreadora de avanzadilla',3,'Se infiltran en el entramado de la sociedad drow sin perturbar ni una sola hierba','Suplanta 1 tropa blanca',1,3,3,1,2,109), 
            ('Explorador del UnderDark',3,'Su conocimiento del Underdark no tiene parangon; ningun rincon esta a salvo de sus espadas','Asesina 2 tropas blancas',2,4,2,1,2,110), 
            ('Elegida de Lolth',4,'<<No pienses siquiera en desobedecer. ¡Es la voluntad de la Reina Arana!>>','Devuelve 1 tropa enemiga o espia enemigo. Al final del turno, asciende otra carta jugada durante este turno',2,4,2,1,2,111), 
            ('Cazarrecompensas',4,'Toda vida tiene un precio','+3 Poder',2,4,2,1,3,112),
            ('Doppelganger',5,'<<Bonita vida la tuya. Creo que me la voy a quedar>>','Suplanta 1 tropa',3,5,2,1,3,113),
            ('Maestros de Sorcere',5,'Las maestras de Sorcere cumplen dos propositos; ensenar a los estudiantes y garantizar la lealtad a Lolth','Elige una opcion:Pon 2 espias| Devuelve 1 de tus espias=> +4 poder',2,5,1,1,4,114), 
            ('Maestro de Melee-Magthere',5,'Las instrucciones de Melee-Magthere se cuentan entre los mejores guerreros de Menzoberranzan','Elige una opcion:Despliega 4 tropas|Suplanta 1 tropa blanca',2,5,2,1,2,115), 
            ('Infiltrador',3,'<<La perfeccion solo puede alcanzarse cuando el precio del fracaso es la muerte>>','Pon un espia. Si hay alguna tropa de otro jugador en esa ubicacion, obten +1 poder',1,2,2,1,4,116),
            ('Maestro de armas',6,'<<O resultas ser digno o pereceras. Tu destino depende de ti. >> -- Shoor Vandree','Elige 3 veces: Despliega 1 tropa| Asesina 1 tropa blanca',3,6,1,1,2,117),
            ('Filo letal',5,'<<Ojala te senalen los Filos letales.>>--Maldicion drow','Asesina 2 tropas',3,6,1,1,3,118),
            ('Miembro del consejo',6,'En las camaras del consejo drow, la intriga es una forma de arte.','Mueve hasta 2 tropas enemigas. Al final del turno, asciende otra carta jugada',3,6,1,1,1,119),
            ('Hilador de conjuros',3,'Nunca sabras que tu mente no te pertenece','Elige una opcion:Pon 1 espia| Devuelve 1 de tus espias=> Suplanta 1 tropa que este en la misma ubicacion que ese espia',1,3,3,1,4,120),
            ('Matrona',6,'La voluntad de la matrona es absoluta','Pon tu mazo en tu pila de descartes=>Asciende 1 carta de tu pila de descartes',3,6,1,1,1,121),
            ('Traficante de informacion',5,'<<¿Por que malgastar hechizos y acero cuando unas simples palabras son capaces de derribar una casa?>>','Elige una opcion:Pon 1 espia| Devuelve 1 de tus espias=> Roba 3 cartas',2,5,2,1,4,122), 
            ('Negociadora Drow',3,'<<Puedo abrir puertas que estan cerradas para todos excepto para las matronas.>>','Si hay 4 cartas o mas en tu circulo interno => +3 influencia. Al final del turno, asciende otra carta jugada durante este turno',1,2,2,1,1,123), 
            ('Kobold',1,'Aunque pueden llegar a vivir mas de un siglo, la mayoria parece antes de cumplir un punado de decadas','Elige una opcion:Despliega 1 tropa| Asesina 1 tropa blanca',1,2,3,2,2,124), 
            ('Sectario del Culto del Dragon',3,'Su mision en la vida es alumbrar una era de dominacion','Elige una opcion:+2 influencia| +2 poder',1,4,4,2,3,125),
            ('Dragon verde',7,'Los dragones verdes miden sus planes en siglos','Elige una opcion:Pon un espia y, a continuacion, suplanta 1 tropa que este en la misma ubicacion que ese espia | Devuelve 1 de tus espias=>Suplanta una tropa que este en la misma ubicacion que ese espia y, a continuacion, obten 1 PV por cada marcador de control de ubicacion que tengas',3,7,1,2,4,126),
            ('Clerigo de Laogzed',4,'Lo unico que supera su hedor es su hambre de carne fresca','Mueve 1 tropa enemiga. Al final del turno, asciende otra carta jugada durante este turno',2,4,2,2,1,127),
            ('Rath Modar',6,'<<Los dragones son herramientas, y con ellas construire imperios>>','Roba 2 cartas. Pon 1 espia',2,5,1,2,4,128), 
            ('Cria de dragon negro',3,'Tantos bocaditos apetitosos y tan poco tiempo','+1 influencia. Asesina 1 tropa blanca',1,4,2,2,2,129), 
            ('Voz de dragon',3,'Fanaticos dotados de la capacidad de convencer a los dragones para que se unan a su causa','.+1 influencia. Al final del turno, asciende otra carta jugada durante este turno',1,3,3,2,1,130), 
            ('Dragon azul',8,'<<En la superficie o en el subsuelo, da igual. Los dragones seran los amos de todo>>','Al final del turno, asciende hasta otras 2 cartas jugadas durante este turno y, a continuacion, obten 1 PV por cada 3 cartas ascendidas que tengas en tu circulo interno',4,8,1,2,1,131), 
            ('Garra de dragon',4,'Los colores de sus tatuajes se corresponde con los dragones a los que veneran','Asesina 1 tropa. A continuacion, si tienes 5 o mas tropas de jugador en tu sala de trofeos, obten +2 poder',1,3,2,2,3,132), 
            ('Cria de dragon verde',4,'Aun recien salida del huevo, el veneno de una cria de dragon es capaz de disolver huesos y corroer el acero','Pon un espia. Si hay alguna tropa de otro jugador en esa ubicacion, obten +2 influencia',2,4,2,2,4,133), 
            ('Dragon negro',7,'Quienes presencian su oscura majestad no pueden evitar postrarse en senal de adoracion','Suplanta 1 tropa blanca que este en cualquier lugar del mapa. Obten 1 PV por cada 3 tropas blancas que haya en tu sala de trofeos',3,7,1,2,2,134), 
            ('Cria de dragon rojo',5,'Lo primero que desarrolla una cria de dragon rojo es su inquebrantable arrogancia','+2 poder. +2 influencia',3,5,2,2,3,135),
            ('Sectario fanatico',3,'<<¡Sentir como corre la sangre de los dragones por tus venas es sentirse invencible!>>','+2 influencia. Puedes devorar 1 carta del mercado',1,4,2,2,1,136), 
            ('Cria de dragon blanco',2,'Las crias de dragon blanco nacen con dos impulsos basicos: el hambre y la codicia','Despliega 2 tropas. Puedes devorar 1 carta del mercado',1,3,3,2,2,137), 
            ('Vigilante de Thav',3,'<<Lo que buscas unicamente se puede comprar con sangre>>','Elige una opcion:Pon 1 espia| Devuelve 1 de tus espias => +3 influencia',2,3,3,2,4,138), 
            ('Cria de dragon azul',5,'Incluso las crias de dragon azul consideran introlerable la insubordinacion a los de su especie','+3 influencia. Devuelve 1 tropa o espia de otro jugador',2,4,2,2,1,139), --DEVOLVER SOLO INCLUYE TROPAS DE JUGADORES (MOVER INCLUYE TROPAS DE JUGADORES Y TROPAS BLANCAS)
            ('Severin Silrajin',7,'<<Se me ha concedido la vision de un destino mucho mas grande de lo que jamas habria imaginado>>','+5 poder',4,8,1,2,3,140),
            ('Encantador de Thav',4,'<<Se necesitan tus servicios, estes dispuesto a prestarlos o no>>','Elige una opcion:Pon 1 espia| devuelve 1 de tus espias => +4 poder',1,3,3,2,4,141), --Sin hacer
            ('Dragon blanco',6,'<<No son los mas espabilados, pero si huelen tu sangre, estaran cazandote hasta el fin de los tiempos>> --Caldoum Truespear, cazador de dragones','Despliega 3 tropas. Obten 1 PV por cada 2 ubicaciones que controles',2,5,1,2,2,142),
            ('Dragon rojo',8,'<<Si alguna vez ves uno de estos, corre>> --Blacksoot, el Abrasador','Suplanta 1 tropa. Devuelve 1 espia enemigo. Obten 1 PV por cada ubicacion bajo tu control total',4,8,1,2,3,143); 
            

