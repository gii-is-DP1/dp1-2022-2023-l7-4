INSERT INTO users(username,password,enabled,name,email,birthdate)
VALUES ('admin1','4dm1n',TRUE, 'Admin', 'admin@email.com', '2002-04-08');
-- One admin user, named admin1 with passwor 4dm1n and authority admin
-- INSERT INTO users(username,password,enabled,name,email,birthdate) VALUES ('admin1','4dm1n',TRUE, 'Admin', 'admin@email.com', '2002-04-08');
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');

INSERT INTO users(username,password,enabled,name,email,birthdate) VALUES ('manotebar','manotebar',TRUE,'Manuel', 'manotebar@gmail.com', '2000-10-20');
INSERT INTO authorities(id,username,authority) VALUES (3,'manotebar','admin');

INSERT INTO users(username,password,enabled,name,email,birthdate) VALUES ('daviddhc','player1',TRUE,'David', 'daviddhc@gmail.com', '2002-04-08');
INSERT INTO authorities(id,username,authority) VALUES (10,'daviddhc','player');

INSERT INTO users(username,password,enabled,name,email,birthdate) VALUES ('anddomrui','hola3',TRUE, 'Andres', 'andres@gmail.com', '2002-04-08');
INSERT INTO authorities(id,username,authority) VALUES (4,'anddomrui','player');

INSERT INTO users(username,password,enabled,name,email,birthdate) VALUES ('javfercas3','secret1',TRUE, 'Javier', 'javi@gmail.com', '2002-04-08');
INSERT INTO authorities(id,username,authority) VALUES (2,'javfercas3','admin');
-- CITIES
INSERT INTO city_templates(id,x,y,unaligned,capacity,name,vp_endgame_value,starting_city,zone,marker_vp,marker_influence) VALUES --ORIGINAL MAP
(1,0,1,'0,1',3,'Gauntlgrym',2,FALSE,1,1,1),
(2,0,0,'',3,'Los Retuercegusanos',3,FALSE,1,0,0),
(3,0,2,'',4,'Jhachalkhyn',4,TRUE,1,0,0),
(4,0,3,'0',3,'Buiyrandyn',3,FALSE,1,0,0),
(5,0,4,'0,1',2,'Bastion del clan stoneshaft',4,FALSE,1,0,0),
(6,0,5,'0,1',3,'Ch`chitil',2,FALSE,1,1,1),
(7,1,0,'0,1',2,'Blingdenstone',4,FALSE,2,0,0),
(8,1,1,'0,1',5,'Mantol-Delrith',4,TRUE,2,0,0),
(9,1,2,'0,1',4,'Gracklstugh',3,FALSE,2,0,0),
(10,1,3,'0',3,'El laberinto',3,FALSE,2,0,0),
(11,1,4,'0,1',5,'Skullport',4,TRUE,2,0,0),
(12,1,5,'',3,'Kanaglym',3,FALSE,2,0,0),
(13,2,1,'0,1,2',6,'Menzoberranzan',5,FALSE,2,2,1),
(14,2,2,'',1,'Puente de Chasmleap',1,FALSE,2,0,0),
(15,2,3,'0,1,2,3',4,'Aramuycos',3,FALSE,2,3,1),
(16,2,4,'',3,'Eryndlyn',3,TRUE,2,0,0),
(17,2,5,'0,1,2',3,'Tsenviilyq',4,FALSE,2,1,1),
(18,3,2,'',3,'Fuego Eterno',3,FALSE,2,0,0),
(19,3,3,'1',2,'Salones de la Legion Flagelante',3,FALSE,2,0,0),
(20,3,4,'',4,'Ched Nasad',3,TRUE,2,0,0),
(21,3,5,'',2,'Llacerellyn',2,FALSE,2,0,0),
(22,4,2,'',5,'Chaulssin',4,TRUE,3,0,0),
(23,5,2,'0,1',3,'El Phaerlin',2,FALSE,3,1,1),
(24,4,3,'0,1',2,'Yathchol',4,FALSE,3,0,0),
(25,4,4,'0,1',6,'Ruinas De Dekanter',5,FALSE,3,0,0),
(26,4,5,'0,1',3,'SS`zuraass`nee',2,FALSE,3,1,1);
INSERT INTO city_templates(capacity,name,vp_endgame_value,starting_city,zone,marker_vp,marker_influence) VALUES (6,'UNO Ini Z2',3,TRUE,2,1,2);
INSERT INTO city_templates(capacity,name,vp_endgame_value,starting_city,zone,marker_vp,marker_influence) VALUES (3,'DOS Ini Z2',3,TRUE,2,2,2);
INSERT INTO city_templates(capacity,name,vp_endgame_value,starting_city,zone,marker_vp,marker_influence) VALUES (4,'TRES blanca Z2',3,FALSE,2,1,2);
INSERT INTO city_templates(capacity,name,vp_endgame_value,starting_city,zone,marker_vp,marker_influence) VALUES (5,'CUATRO Ini Z3',6,TRUE,3,1,2);
INSERT INTO city_templates(capacity,name,vp_endgame_value,starting_city,zone,marker_vp,marker_influence) VALUES (2,'CINCO Ini Z1',6,TRUE,1,1,2);
INSERT INTO city_templates(capacity,name,vp_endgame_value,starting_city,zone,marker_vp,marker_influence) VALUES (2,'SEIS blanca Z1',6,FALSE,1,6,2);


-- Original map paths with white troops
INSERT INTO path_templates(id,unaligned,city_id_1,city_id_2,capacity) VALUES 
(11,'0',7,8,1),(17,'1',10,15,2),(23,'0',14,15,1),(25,'0',15,16,1),(26,'0',15,20,1);
--Original map paths
INSERT INTO path_templates(id,city_id_1,city_id_2,capacity) VALUES 
(1,1,2,0),(2,1,3,1),(3,2,8,1),(4,3,9,1),(5,3,4,1),(6,4,5,1),(7,4,10,1),(8,5,6,1),(9,5,11,2),(10,6,12,1),(12,8,9,1),(13,8,13,3),(14,9,10,1),(15,9,14,2),(16,10,11,2),(18,11,12,1),
(19,12,16,2),(20,12,17,2),(21,13,14,2),(22,13,18,2),(24,14,18,1),(27,16,21,1),(28,17,21,1),(29,18,19,1),(30,18,22,1),(31,19,20,2),(32,19,24,1),(33,20,21,2),(34,20,24,1),(35,20,25,1),
(36,21,26,2),(37,22,23,1),(38,23,24,1),(39,23,25,2),(40,25,26,1);


INSERT INTO path_templates(city_id_1,city_id_2,capacity) VALUES (1,2,1);
INSERT INTO path_templates(city_id_1,city_id_2,capacity) VALUES (1,3,3);
INSERT INTO path_templates(city_id_1,city_id_2,capacity) VALUES (2,3,0);
INSERT INTO path_templates(city_id_1,city_id_2,capacity) VALUES (2,4,3);
INSERT INTO path_templates(city_id_1,city_id_2,capacity) VALUES (2,5,3);
INSERT INTO path_templates(city_id_1,city_id_2,capacity) VALUES (1,5,3);-- GIVES ERROR
INSERT INTO path_templates(city_id_1,city_id_2,capacity) VALUES (5,6,2);
INSERT INTO path_templates(city_id_1,city_id_2,capacity) VALUES (4,6,1);


-- MAPS
INSERT INTO map_templates(name) VALUES ('Original map');
INSERT INTO map_templates(name) VALUES ('small Map 2');
INSERT INTO map_templates(name) VALUES ('medium Map 3');
INSERT INTO map_templates(name) VALUES ('Bad made medium Map 3no -> 2 yes');

--Paths -->MAPS
INSERT INTO map_templates_path_templates(map_template_id,path_template_id) VALUES
    --Original map
    (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,19),(1,20),(1,21),(1,22),(1,23),(1,24),(1,25),
    (1,27),(1,28),(1,29),(1,30),(1,31),(1,32),(1,33),(1,34),(1,35),(1,36),(1,37),(1,38),(1,39),(1,40),

    (2,1),
    (2,2),
    (2,3),

    (3,1),
    (3,2),
    (3,3),
    (3,4),

    (4,1),
    (4,6);







INSERT INTO house(id,name,hex_color) VALUES (0,'Unaligned','#dddddd');
INSERT INTO house(id,name,hex_color) VALUES (1,'Baerne','#81ed05');
INSERT INTO house(id,name,hex_color) VALUES (2,'Mizzrym','#ff9922');
INSERT INTO house(id,name,hex_color) VALUES (3,'Xorlarrin','#09e4e8');
INSERT INTO house(id,name,hex_color) VALUES (4,'Barrison del"armgo','#ff2222');
-- HALFDECK
INSERT INTO halfdecks(id,name,description) VALUES (1,'Drow','El mazo Drow presenta cartas optimizadas con costes de influencia más bajos.');
INSERT INTO halfdecks(id,name,description) VALUES (2,'Dragons','El mazo Dragones tiene muchas cartas de alto coste de influencia, y cuenta con 5 Dragones que te recompensarán por seguir una estrategia.');
INSERT INTO halfdecks(id,name,description) VALUES (3,'Inicial','Cartas iniciales de los jugadores');
INSERT INTO halfdecks(id,name,description) VALUES (4,'Básica','Cartas básicas disponibles en el mercado de todas las partidas');
INSERT INTO halfdecks(id,name,description) VALUES (5,'Mazo con 3 cartas','Mazo de testing, no tiene todas la cartas');
INSERT INTO halfdecks(id,name,description) VALUES (6,'Segundo mazo con 3 cartas','Mazo de testing, no tiene todas las cartas');


-- GAMES
INSERT INTO games(id,date,name,map_template_id,automatic) VALUES  (1,'2022-04-08','Partida mapa original',1,FALSE);
INSERT INTO games(id,date,name,map_template_id,automatic) VALUES (2,'2022-04-09','Partida zona media',1,FALSE);


INSERT INTO players(id,name,power,influence,house_id) VALUES (0, 'Unaligned Enemy', 10,10,0);
INSERT INTO players(id,name,username,power,influence,house_id,game_id) VALUES (1, 'P1 daviddhc', 'daviddhc',10,10,1,1);
INSERT INTO players(id,name,username,power,influence,house_id,game_id) VALUES (2, 'P2 anddomrui', 'anddomrui',10,10,2,1);
INSERT INTO players(id,name,power,influence,house_id,game_id) VALUES (3, 'P3 manotebar',10,10,3,1);
INSERT INTO players(id,name,power,influence,house_id,game_id) VALUES (4, 'P4 javiFdz',10,10,4,1);

INSERT INTO players(id,name,power,influence,house_id,game_id) VALUES (5, 'Player1 javiTopG',1000,1000,1,2);
INSERT INTO players(id,name,power,influence,house_id,game_id) VALUES (6, 'Player2 davidZ',1000,1000,2,2);

-- ACTIONS USED TO FORM COMPLEX ACTIONS
INSERT INTO actions(id,original_iterations,action_name,value,description) VALUES 
    
    (1,1,'RETURN_PLAYER_PIECE',null,'Devuelve una tropa o espía enemigo'),
    (2,1,'PROMOTE_OWN_PLAYED_CARD',null,'Asciende una carta jugada'),
    (3,1,'INFLUENCE',2,'+2 de influencia'),
    (4,1,'KILL_ENEMY_TROOP',null,'Asesina una tropa enemiga'),
    (5,1,'POWER',2,'+2 de poder'),
    (6,2,'PLACE_OWN_SPY',null,'Pon 2 espías'),
    (7,1,'RETURN_OWN_SPY',null,'Devuelve uno de tus espías'),
    (8,4,'DEPLOY_OWN_TROOP',null,'Despliega 4 tropas'),
    (9,1,'SUPPLANT_WHITE_TROOP',null,'Suplanta una tropa blanca'),
    (10,1,'DEPLOY_OWN_TROOP',null,'Despliega una tropa'),
    (11,1,'KILL_WHITE_TROOP',null,'Asesina una tropa blanca'),
    (12,2,'MOVE_ENEMY_TROOP',null,'Mueve hasta 2 tropas enemigas'),
    (13,1,'PLACE_OWN_SPY',null,'Pon un espía'),
    (14,1,'POWER',4,'+4 de poder'),
    (15,1,'THEN',null,'Entonces'),
    (16,1,'MOVE_OWN_DECK_CARDS_TO_DISCARDED',null,'description'),
    (17,1,'PROMOTE_OWN_DISCARDED_CARD',null,'description'),
    (18,1,'THEN',null,'description'),
    (19,1,'DRAW_CARD',3,'description'),
    (20,1,'THEN',null,'description'),
    (21,1,'SUPPLANT_ENEMY_TROOP_IN_SITE',null,'description'),
    (22,1,'THEN',null,'description'),
    (23,1,'CHECK_INNER_CARDS_GREATER_THAN',3,'description'),
    (24,1,'INFLUENCE',3,'description'),
    (25,1,'THEN',null,'description'),
    (26,1,'THEN',null,'description'),
    (27,1,'THEN',null,'description'),
    (28,1,'VP_FOR_EVERY_SITE_MARKER',null,'description'),
    (29,1,'MOVE_ENEMY_TROOP',null,'description'),
    (30,1,'DRAW_CARD',2,'description'),
    (31,1,'INFLUENCE',1,'description'),
    (32,2,'PROMOTE_OWN_PLAYED_CARD',null,'description'),
    (33,1,'VP_FOR_EVERY_3_CARDS_IN_INNER',null,'description'),
    (34,1,'THEN',null,'description'),
    (35,1,'CHECK_KILLED_PLAYER_TROOPS_GREATER_THAN',4,'description'),
    (36,1,'THEN',null,'description'),
    (37,1,'CHECK_PLAYER_ANY_TROOP_IN_SITE',1,'description'),
    (38,1,'SUPPLANT_WHITE_TROOP_ANYWHERE',null,'description'),
    (39,1,'VP_FOR_EVERY_3_WHITE_KILLED_TROOPS',null,'description'),
    (40,1,'DEVORE_MARKET_CARD',null,'description'),
    (41,2,'DEPLOY_OWN_TROOP',null,'description'),
    (42,1,'THEN',null,'description'),
    (43,1,'THEN',null,'description'),
    (44,3,'DEPLOY_OWN_TROOP',null,'description'),
    (45,1,'VP_FOR_EVERY_2_CONTROLED_SITES',null,'description'),
    (46,1,'SUPPLANT_ENEMY_TROOP',null,'description'),
    (47,1,'VP_FOR_EVERY_TOTAL_CONTROLLED_SITE',null,'description'),
    (48,1,'POWER',1,'description'),
    (49,1,'AT_END_TURN',null,'description'),
    (50,1,'THEN',null,'description'),
    (51,1,'AT_END_TURN',null,'Al final del turno'),
    (52,1,'AT_END_TURN',null,'description'),
    (53,1,'AT_END_TURN',null,'description'),
    (54,1,'AT_END_TURN',null,'description'),
    (55,1,'AT_END_TURN',null,'description'),
    (56,1,'AT_END_TURN',null,'description'),
    (57,1,'RETURN_PLAYER_SPY',null,'description')
    ;

--CARD ACTIONS
INSERT INTO actions(id,original_iterations,action_name,value,description) VALUES 
    
    (100,1,'POWER',10,'+1 de poder'),--Soldado HECHA Y COMPROBADA
    (101,1,'INFLUENCE',10,'+1 de influencia'),--Noble HECHA Y COMPROBADA
    (102,1,'INFLUENCE',2,'+2 de influencia'),--Lolth HECHA Y COMPROBADA
    (103,1,'POWER',2,'+2 de poder'),--Guardia HECHA Y COMPROBADA
    (104,3,'DEPLOY_OWN_TROOP',null,'Despliega 3 tropas'),--Cuadrilla de mercenarios HECHA Y COMPROBADA
    (105,1,'PLACE_OWN_SPY',null,'Pon un espía'),--Maestra de espías HECHA Y COMPROBADA
    (106,1,'CHOOSE',null,'Elige una opción: '),--Adalid HECHA Y COMPROBADA
    (107,1,'CHOOSE',null,'description'),--Inquisidora HECHA Y COMPROBADA
    (108,1,'CHOOSE',null,'description'),--Guardia Negro HECHA Y COMPROBADA
    (109,1,'SUPPLANT_WHITE_TROOP',null,'description'),--Rastreadora de avanzadilla HECHA Y COMPROBADA
    (110,2,'KILL_WHITE_TROOP',null,'description'),--Explorador del UnderDark HECHA Y COMPROBADA
    (111,1,'ALL',null,'description'),--Elegida de Lolth HECHA Y COMPROBADA
    (112,1,'POWER',3,'description'),--Cazarrecompensas HECHA Y COMPROBADA
    (113,1,'SUPPLANT_ENEMY_TROOP',null,'description'),--Doppelganger HECHA Y COMPROBADA
    (114,1,'CHOOSE',null,'description'),--Maestros de Sorcere HECHA Y COMPROBADA
    (115,1,'CHOOSE',null,'description'),--Maestro de Melee-Magthere HECHA Y COMPROBADA
    (116,1,'THEN',null,'description'),--Infiltrador HECHA Y COMPROBADA
    (117,3,'CHOOSE',null,'description'),--Maestro de armas HECHA Y COMPROBADA
    (118,2,'KILL_ENEMY_TROOP',null,'description'),--Filo letal HECHA Y COMPROBADA
    (119,1,'ALL',null,'description'),--Miembro del consejo HECHA Y COMPROBADA
    (120,1,'CHOOSE',null,'description'),--Hilador de conjuros HECHA Y COMPROBADA
    (121,1,'THEN',null,'description'),--Matrona HECHA Y COMPROBADA
    (122,1,'CHOOSE',null,'description'),--Traficante de información HECHA Y COMPROBADA
    (123,1,'ALL',null,'description'),--Negociadora Drow HECHA Y COMPROBADA
    (124,1,'CHOOSE',null,'description'),--Kobold HECHA Y COMPROBADA
    (125,1,'CHOOSE',null,'description'),--Sectario del Culto del Dragón HECHA Y COMPROBADA
    (126,1,'CHOOSE',null,'description'),--Dragón verde
    (127,1,'ALL',null,'description'),--Clérigo de Laogzed HECHA Y COMPROBADA
    (128,1,'ALL',null,'description'),--Rath Modar HECHA Y COMPROBADA
    (129,1,'ALL',null,'description'),--Cría de dragón negro HECHA Y COMPROBADA
    (130,1,'ALL',null,'description'),--Voz de dragón HECHA Y COMPROBADA
    (131,1,'THEN',null,'description'),--Dragón azul HECHA Y COMPROBADA
    (132,1,'THEN',null,'description'),--Garra de dragón HECHA Y COMPROBADA
    (133,1,'ALL',null,'description'),--Cría de dragón verde HECHA Y COMPROBADA
    (134,1,'ALL',null,'description'),--Dragón negro HECHA Y COMPROBADA
    (135,1,'ALL',null,'description'),--Cría de dragón rojo HECHA Y COMPROBADA
    (136,1,'ALL',null,'description'),--Sectario fanático HECHA Y COMPROBADA
    (137,1,'ALL',null,'description'),--Cría de dragón blanco
    (138,1,'CHOOSE',null,'description'),--Vigilante de Thav
    (139,1,'ALL',null,'description'),--Cría de dragón azul HECHA Y COMPROBADA
    (140,1,'POWER',5,'description'),--Severin Silrajin HECHA Y COMPROBADA
    (141,1,'CHOOSE',null,'description'),--Encantador de Thav
    (142,1,'ALL',null,'description'),--Dragón blanco
    (143,1,'ALL',null,'description')--Dragón rojo
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
INSERT INTO aspects(id,name,description,image) VALUES (1,'Ambición','Los siervos de ambición son los mejores para reclutar a otros siervos y crear un círculo interno fuerte.','');
INSERT INTO aspects(id,name,description,image) VALUES (2,'Conquista','Los siervos de conquista son los mejores para apoderarse de la Infraoscuridad.','');
INSERT INTO aspects(id,name,description,image) VALUES (3,'Malicia','Los siervos de malicia son flexibles y son los mejores en el asesinato.','');
INSERT INTO aspects(id,name,description,image) VALUES (4,'Astucia','Los siervos de astucia son los mejores para espiar e interrumpir el control.','');
INSERT INTO aspects(id,name,description,image) VALUES (5,'Obediencia','Los siervos de obediencia realizan las tareas del día a día.','');





INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES      
        (0,'Espaciador',0,'','',0,0,0,3,5);

-- CARDS
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id,action_id) VALUES      
            ('Soldado',0,'Los soldados defienden a sus superiores contra todos los enemigos','+1 de poder',0,1,0,3,5,100),
            ('Noble',0,'La voz de un drow de una casa noble tiene el peso de la de mil plebeyos','+1 de influencia',0,1,0,3,5,101),
            ('Sacerdotisa de Lolth',2,'Elegidas por su devoción a Lolth, ahora hacen valer su voluntad','+2 de influencia',1,2,0,4,5,102),
            ('Guardia de la casa',3,'El cuerpo de guardia de una casa se nutre de los pocos drow que sobreviven a la instrucción','+2 de poder',1,3,0,4,5,103),
            ('Cuadrilla de mercenarios',3,'La única otra opción que les queda a quienes carecen de casa es el exilio.','Despliega 3 tropas.',1,4,2,1,2,104),
            ('Maestra de espías',2,'<<Puedo ayudarte a sortear las puertas y los guardias. Lo que hagas despues es cosa tuya>>','Pon un espía',1,2,2,1,4,105),
            ('Adalid',2,'<<Codícia, avarícia y poder: tres idiomas que entiendo a la perfección>>','Elige una Opcion: + 2 Influencia | Al final del turno, asciende otra carta jugada durante este turno',1,2,4,1,1,106), 
            ('Inquisidora',3,'<<Si con mi servicio puedo complacer a la Diosa y traer honor a mi casa, me doy por satisfecha>>','Elige una Opcion: + 2 Influencia | Asesina tropa',2,4,1,1,3,107),
            ('Guardia Negro',3,'Patrullan el Underdark, exterminando a intrusos perdidos, inconscientes y temerarios.','Elige una Opcion: + 2 poder| Asesina tropa',1,3,4,1,3,108),
            ('Rastreadora de avanzadilla',3,'Se infiltran en el entramado de la sociedad drow sin perturbar ni una sola hierba','Suplanta 1 tropa blanca',1,3,3,1,2,109), 
            ('Explorador del UnderDark',3,'Su conocimiento del Underdark no tiene parangón; ningún rincón esta a salvo de sus espadas','Asesina 2 tropas blancas',2,4,2,1,2,110), 
            ('Elegida de Lolth',4,'<<No pienses siquiera en desobedecer. ¡Es la voluntad de la Reina Araña!>>','Devuelve 1 tropa enemíga o espía enemigo. Al final del turno, asciende otra carta jugada durante este turno',2,4,2,1,2,111), 
            ('Cazarrecompensas',4,'Toda vida tiene un precio','+3 Poder',2,4,2,1,3,112),
            ('Doppelganger',5,'<<Bonita vida la tuya. Creo que me la voy a quedar>>','Suplanta 1 tropa',3,5,2,1,3,113),
            ('Maestros de Sorcere',5,'Las maestras de Sorcere cumplen dos propósitos; enseñar a los estudiantes y garantizar la lealtad a Lolth','Elige una opción:Pon 2 espías| Devuelve 1 de tus espías=> +4 poder',2,5,1,1,4,114), 
            ('Maestro de Melee-Magthere',5,'Las instrucciones de Melee-Magthere se cuentan entre los mejores guerreros de Menzoberranzan','Elige una opción:Despliega 4 tropas|Suplanta 1 tropa blanca',2,5,2,1,2,115), 
            ('Infiltrador',3,'<<La perfección solo puede alcanzarse cuando el precio del fracaso es la muerte>>','Pon un espía. Si hay alguna tropa de otro jugador en esa ubicación, obten +1 poder',1,2,2,1,4,116),
            ('Maestro de armas',6,'<<O resultas ser digno o perecerás. Tu destino depende de ti. >> -- Shoor Vandree','Elige 3 veces: Despliega 1 tropa| Asesina 1 tropa blanca',3,6,1,1,2,117),
            ('Filo letal',5,'<<Ojalá te señalen los Filos letales.>>--Maldición drow','Asesina 2 tropas',3,6,1,1,3,118),
            ('Miembro del consejo',6,'En las cámaras del consejo drow, la intriga es una forma de arte.','Mueve hasta 2 tropas enemigas. Al final del turno, asciende otra carta jugada',3,6,1,1,1,119),
            ('Hilador de conjuros',3,'Nunca sabrás que tu mente no te pertenece','Elige una opción:Pon 1 espía| Devuelve 1 de tus espías=> Suplanta 1 tropa que esté en la misma ubicación que ese espía',1,3,3,1,4,120),
            ('Matrona',6,'La voluntad de la matrona es absoluta','Pon tu mazo en tu pila de descartes=>Asciende 1 carta de tu pila de descartes',3,6,1,1,1,121),
            ('Traficante de información',5,'<<¿Por qué malgastar hechizos y acero cuando unas simples palabras son capaces de derribar una casa?>>','Elige una opción:Pon 1 espía| Devuelve 1 de tus espías=> Roba 3 cartas',2,5,2,1,4,122), 
            ('Negociadora Drow',3,'<<Puedo abrir puertas que están cerradas para todos excepto para las matronas.>>','Si hay 4 cartas o más en tu círculo interno => +3 influencia. Al final del turno, asciende otra carta jugada durante este turno',1,2,2,1,1,123), 
            ('Kobold',1,'Aunque pueden llegar a vivir más de un siglo, la mayoría parece antes de cumplir un puñado de décadas','Elige una opción:Despliega 1 tropa| Asesina 1 tropa blanca',1,2,3,2,2,124), 
            ('Sectario del Culto del Dragón',3,'Su misión en la vida es alumbrar una era de dominación','Elige una opción:+2 influencia| +2 poder',1,4,4,2,3,125),
            ('Dragón verde',7,'Los dragones verdes miden sus planes en siglos','Elige una opción:Pon un espía y, a continuación, suplanta 1 tropa que esté en la misma ubicación que ese espía | Devuelve 1 de tus espías=>Suplanta una tropa que esté en la misma ubicación que ese espía y, a continuación, obtén 1 PV por cada marcador de control de ubicación que tengas',3,7,1,2,4,126), --FALTA HACER LOS MARCADORES
            ('Clérigo de Laogzed',4,'Lo único que supera su hedor es su hambre de carne fresca','Mueve 1 tropa enemiga. Al final del turno, asciende otra carta jugada durante este turno',2,4,2,2,1,127),
            ('Rath Modar',6,'<<Los dragones son herramientas, y con ellas construiré imperios>>','Roba 2 cartas. Pon 1 espía',2,5,1,2,4,128), 
            ('Cría de dragón negro',3,'Tantos bocaditos apetitosos y tan poco tiempo','+1 influencia. Asesina 1 tropa blanca',1,4,2,2,2,129), 
            ('Voz de dragón',3,'Fanáticos dotados de la capacidad de convencer a los dragones para que se unan a su causa','.+1 influencia. Al final del turno, asciende otra carta jugada durante este turno',1,3,3,2,1,130), 
            ('Dragón azul',8,'<<En la superficie o en el subsuelo, da igual. Los dragones serán los amos de todo>>','Al final del turno, asciende hasta otras 2 cartas jugadas durante este turno y, a continuación, obtén 1 PV por cada 3 cartas ascendidas que tengas en tu círculo interno',4,8,1,2,1,131), 
            ('Garra de dragón',4,'Los colores de sus tatuajes se corresponde con los dragones a los que veneran','Asesina 1 tropa. A continuación, si tienes 5 o más tropas de jugador en tu sala de trofeos, obtén +2 poder',1,3,2,2,3,132), 
            ('Cría de dragón verde',4,'Aún recién salida del huevo, el veneno de una cría de dragón es capaz de disolver huesos y corroer el acero','Pon un espía. Si hay alguna tropa de otro jugador en esa ubicación, obten +2 influencia',2,4,2,2,4,133), 
            ('Dragón negro',7,'Quiénes presencian su oscura majestad no pueden evitar postrarse en señal de adoración','Suplanta 1 tropa blanca que esté en cualquier lugar del mapa. Obtén 1 PV por cada 3 tropas blancas que haya en tu sala de trofeos',3,7,1,2,2,134), 
            ('Cría de dragón rojo',5,'Lo primero que desarrolla una cría de dragón rojo es su inquebrantable arrogancia','+2 poder. +2 influencia',3,5,2,2,3,135),
            ('Sectario fanático',3,'<<¡Sentir cómo corre la sangre de los dragones por tus venas es sentirse invencible!>>','+2 influencia. Puedes devorar 1 carta del mercado',1,4,2,2,1,136), 
            ('Cría de dragón blanco',2,'Las crías de dragón blanco nacen con dos impulsos básicos: el hambre y la codicia','Despliega 2 tropas. Puedes devorar 1 carta del mercado',1,3,3,2,2,137), 
            ('Vigilante de Thav',3,'<<Lo que buscas únicamente se puede comprar con sangre>>','Elige una opción:Pon 1 espía| Devuelve 1 de tus espías => +3 influencia',2,3,3,2,4,138), 
            ('Cría de dragón azul',5,'Incluso las crías de dragón azul consideran introlerable la insubordinación a los de su especie','+3 influencia. Devuelve 1 tropa o espía de otro jugador',2,4,2,2,1,139), --DEVOLVER SOLO INCLUYE TROPAS DE JUGADORES (MOVER INCLUYE TROPAS DE JUGADORES Y TROPAS BLANCAS)
            ('Severin Silrajin',7,'<<Se me ha concedido la visión de un destino mucho más grande de lo que jamás habría imaginado>>','+5 poder',4,8,1,2,3,140),
            ('Encantador de Thav',4,'<<Se necesitan tus servicios, estés dispuesto a prestarlos o no>>','Elige una opción:Pon 1 espía| devuelve 1 de tus espias => +4 poder',1,3,3,2,4,141), --Sin hacer
            ('Dragón blanco',6,'<<No son los más espabilados, pero si huelen tu sangre, estarán cazándote hasta el fin de los tiempos>> --Caldoum Truespear, cazador de dragones','Despliega 3 tropas. Obtén 1 PV por cada 2 ubicaciones que controles',2,5,1,2,2,142),
            ('Dragón rojo',8,'<<Si alguna vez ves uno de estos, corre>> --Blacksoot, el Abrasador','Suplanta 1 tropa. Devuelve 1 espía enemigo. Obtén 1 PV por cada ubicación bajo tu control total',4,8,1,2,3,143); 
            

-- PARA TEST--
INSERT INTO games(id,date,name,map_template_id,first_half_deck_id,second_half_deck_id,automatic,round) VALUES (3,'2022-04-09','Partida de prueba',1,1,2,TRUE,2);
INSERT INTO players(id,name,power,influence,house_id,game_id) VALUES (7, 'Player1',1000,1000,1,3);
INSERT INTO players(id,name,power,influence,house_id,game_id) VALUES (8, 'Player2',1000,1000,2,3);