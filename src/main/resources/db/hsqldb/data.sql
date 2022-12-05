-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled,name,email,birthdate) VALUES ('admin1','4dm1n',TRUE, 'Admin', 'admin@email.com', '2002-04-08');
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');

INSERT INTO users(username,password,enabled,name,email,birthdate) VALUES ('daviddhc','player1',TRUE,'David', 'daviddhc@gmail.com', '2002-04-08');
INSERT INTO authorities(id,username,authority) VALUES (10,'daviddhc','player');

INSERT INTO users(username,password,enabled,name,email,birthdate) VALUES ('anddomrui','hola3',TRUE, 'Andres', 'andres@gmail.com', '2002-04-08');
INSERT INTO authorities(id,username,authority) VALUES (4,'anddomrui','player');

INSERT INTO users(username,password,enabled,name,email,birthdate) VALUES ('javfercas3','secret1',TRUE, 'Javier', 'javi@gmail.com', '2002-04-08');
INSERT INTO authorities(id,username,authority) VALUES (2,'javfercas3','admin');
-- CITIES
INSERT INTO city_templates(capacity,name,vp_endgame_value,starting_city,zone) VALUES (4,'EL LABERINTO',3,TRUE,1);
INSERT INTO city_templates(capacity,name,vp_endgame_value,starting_city,zone) VALUES (1,'BUIYRANDYN',3,FALSE,2);
INSERT INTO city_templates(capacity,name,vp_endgame_value,starting_city,zone) VALUES (2,'GRACKLSTUGH',6,FALSE,3);

-- PATHS
INSERT INTO path_templates(city_id_1,city_id_2,capacity) VALUES (1,2,0);
INSERT INTO path_templates(city_id_1,city_id_2,capacity) VALUES (1,3,4);
INSERT INTO path_templates(city_id_1,city_id_2,capacity) VALUES (2,3,2);

-- MAPS
INSERT INTO map_templates(name) VALUES ('default Map');

--POPULATE MAPS
INSERT INTO map_templates_city_templates(map_id,city_id) VALUES
    (1,1),
    (1,2),
    (1,3);

INSERT INTO map_templates_path_templates(map_id,path_id) VALUES
    (1,1),
    (1,2),
    (1,3);





INSERT INTO house(id,name,hex_color) VALUES (0,'Unaligned','#dddddd');
INSERT INTO house(id,name,hex_color) VALUES (1,'Baerne','#5f605b');
INSERT INTO house(id,name,hex_color) VALUES (2,'Mizzrym','#ff7133');
INSERT INTO house(id,name,hex_color) VALUES (3,'Xorlarrin','#2f717f');
INSERT INTO house(id,name,hex_color) VALUES (4,'Barrison del"armgo','#fc2e3c');
-- HALFDECK
INSERT INTO halfdecks(id,name,description) VALUES (1,'Drow','El mazo Drow presenta cartas optimizadas con costes de influencia más bajos.');
INSERT INTO halfdecks(id,name,description) VALUES (2,'Dragons','El mazo Dragones tiene muchas cartas de alto coste de influencia, y cuenta con 5 Dragones que te recompensarán por seguir una estrategia.');
INSERT INTO halfdecks(id,name,description) VALUES (3,'Inicial','Cartas iniciales de los jugadores');
INSERT INTO halfdecks(id,name,description) VALUES (4,'Básica','Cartas básicas disponibles en el mercado de todas las partidas');

-- GAMES
INSERT INTO games(id,first_half_deck_id,second_half_deck_id,date,name,size,map_template_id) 
VALUES (1,1,2,'2002-04-08','Partida 1', 3,1);

INSERT INTO players(id,name,power,influence,house_id) VALUES (0, 'Unaligned Enemy', 1000,1000,0);
INSERT INTO players(id,name,username,power,influence,house_id,game_id) VALUES (1, 'David', 'daviddhc',1000,1000,1,1);
INSERT INTO players(id,name,username,power,influence,house_id,game_id) VALUES (2, 'Andres', 'anddomrui',1000,1000,2,1);




-- ASPECTS
INSERT INTO aspects(id,name,description,image) VALUES (1,'Ambición','Los siervos de ambición son los mejores para reclutar a otros siervos y crear un círculo interno fuerte.','');
INSERT INTO aspects(id,name,description,image) VALUES (2,'Conquista','Los siervos de conquista son los mejores para apoderarse de la Infraoscuridad.','');
INSERT INTO aspects(id,name,description,image) VALUES (3,'Malicia','Los siervos de malicia son flexibles y son los mejores en el asesinato.','');
INSERT INTO aspects(id,name,description,image) VALUES (4,'Astucia','Los siervos de astucia son los mejores para espiar e interrumpir el control.','');
INSERT INTO aspects(id,name,description,image) VALUES (5,'Obediencia','Los siervos de obediencia realizan las tareas del día a día.','');

-- CARDS
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Soldado',0,'Los soldados defienden a sus superiores contra todos los enemigos','+1 de poder',0,1,0,3,5);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Noble',0,'La voz de un drow de una casa noble tiene el peso de la de mil plebeyos','+1 de influencia',0,1,0,3,5);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Sacerdotisa de Lolth',2,'Elegidas por si devoración a Lolth, ahora hacen valer su voluntad','+2 de influencia',1,2,0,4,5);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Guardia de la casa',3,'El cuerpo de guardia de una casa se nutre de los pocos drow que sobreviven a la instrucción','+2 de poder',1,3,0,4,5);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Cuadrilla de mercenarios',3,'La única otra opción que les queda a quienes carecen de casa es el exilio.','Despliega 3 tropas.',1,4,2,1,2);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Maestra de espías',2,'<<Puedo ayudarte a sortear las puertas y los guardias. Lo que hagas despues es cosa tuya>>','Pon un espía',1,2,2,1,4);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Adalid',2,'<<Codícia, avarícia y poder: tres idiomas que entiendo a la perfección>>','Elige una Opcion: + 2 Influencia | Al final del turno, asciende una carta jugada durante este turno',1,2,4,1,1);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Inquisidora',3,'<<Si con mi servicio puedo complacer a la Diosa y traer honor a mi casa, me doy por satisfecha>>','Elige una Opcion: + 2 Influencia | Asesina tropa',2,4,1,1,3);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Guardia Negro',3,'Patrullan el Underdark, exterminando a intrusos perdidos, inconscientes y temerarios.','Elige una Opcion: + 2 poder| Asesina tropa',1,3,4,1,3);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Rastreadora de avanzadilla',3,'Se infiltran en el entramado de la sociedad drow sin perturbar ni una sola hierba','Suplanta 1 tropa blanca',1,3,3,1,2);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Explorador del UnderDark',3,'Su conocimiento del Underdark no tiene parangón; ningún rincón esta a salvo de sus espadas','Asesina 2 tropas blancas',2,4,2,1,2);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Elegida de Lolth',4,'<<No pienses siquiera en desobedecer. ¡Es la voluntad de la Reina Araña!>>','Devuelve 1 tropa enemíga o espía enemigo.Al final del turno, asciende carta jugada durante este turno',2,4,2,1,2);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Cazarrecompensas',4,'Toda vida tiene un precio','.+3 Poder',2,4,2,1,3);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Doppelganger',5,'<<Bonita vida la tuya. Creo que me la voy a quedar>>','Suplanta 1 tropa blanca',3,5,2,1,3);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Maestros de Sorcere',5,'Las maestras de Sorcere cumplen dos propósitos; enseñar a los estudiantes y garantizar la lealtad a Lolth','Elige una opción:Pon 2 espías| Devuelve 1 de tus espías=> +4 poder',2,5,1,1,4);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Maestro de Melee-Magthere',5,'Las instrucciones de Melee-Magthere se cuentan entre los mejores guerreros de Menzoberranzan','Elige una opción:Despliega 4 tropas|Suplanta 1 tropa blanca',2,5,2,1,2);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Infiltrador',3,'<<La perfección solo puede alcanzarse cuando el precio del fracaso es la muerte>>','Pon un espía. Si hay alguna tropa de otro jugador en esa obicación, obten +1 poder',1,2,2,1,4);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Maestro de armas',6,'<<O resultas ser digno o perecerás. Tu destino depende de ti. >> -- Shoor Vandree','Elige 3 veces: Despliega 1 tropa| Asesina 1 tropa blanca',3,6,1,1,2);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Filo letal',5,'<<Ojalá te señalen los Filos letales.>>--Maldición drow','Asesina 2 tropas',3,6,1,1,3);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Miembro del consejo',6,'En las cámaras del consejo drow, la intriga es una forma de arte.','Mueve hasta 2 tropas enemigas. Al final del turno, asciende otra carta jugada',3,6,1,1,1);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Hilador de conjuros',3,'Nunca sabrás que tu mente no te pertenece','Elige una opción:Pon 1 espía| Devuelve 1 de tus espías=> Suplanta 1 tropa que esté en la misma ubicación que ese espía',1,3,3,1,4);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Matrona',6,'La voluntad de la matrona es absoluta','Pon tu mazo en tu pila de descartes=>Asciende 1 carta de tu pila de descartes',3,6,1,1,1);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Traficante de información',5,'<<¿Por qué malgastar hechizos y acero cuando unas simples palabras son capaces de derribar una casa?>>','Elige una opción:Pon 1 espía| Devuelve 1 de tus espías=> Roba 3 cartas',2,5,2,1,4);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Negociadora Drow',3,'<<Puedo abrir puertas que están cerradas para todos excepto para las matronas.>>','Si hay 4 cartas o más en tu círculo interno, +3 influencia. Al final del turno, asciende otra carta jugada durante este turno',1,2,2,1,1);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Kobold',1,'Aunque pueden llegar a vivir más de un siglo, la mayoría parece antes de cumplir un puñado de décadas','Elige una opción:Despliega 1 tropa| Asesina 1 tropa blanca',1,2,3,2,2);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Sectario del Culto del Dragón',3,'Su misión en la vida es alumbrar una era de dominación','Elige una opción:+2 influencia| +2 poder',1,4,4,2,3);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Dragón verde',7,'Los dragones verdes miden sus planes en siglos','Elige una opción:Pon un espía y, a continuación, suplanta 1 tropa que esté en la misma ubicación que ese espía | Devuelve 1 de tus espías=>Suplanta una tropa que esté en la misma ubicación que ese espía y, a continuación, obtén 1 PV por cada marcador de control de ubicación que tengas',3,7,1,2,4);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Clérigo de Laogzed',4,'Lo único que supera su hedor es su hambre de carne fresca','Mueve 1 tropa enemiga. Al final del turno, asciende otra carta jugada durante este turno',2,4,2,2,1);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Rath Modar',6,'<<Los dragones son herramientas, y con ellas construiré imperios>>','Roba 2 cartas. Pon 1 espía',2,5,1,2,4);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Cría de dragón negro',3,'Tantos bocaditos apetitosos y tan poco tiempo','.+1 influencia. Asesina 1 tropa blanca',1,4,2,2,2);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Voz de dragón',3,'Fanáticos dotados de la capacidad de convencer a los dragones para que se unan a su causa','.+1 influencia. Al final del turno, asciende otra carta jugada durante este turno',1,3,3,2,1);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Dragón azul',8,'<<En la superficie o en el subsuelo, da igual. Los dragones serán los amos de todo>>','Al final del turno, asciende hasta otras 2 cartas jugadas durante este turno y, a continuación, obtén 1 PV por cada 3 cartas ascendidas que tengas en tu círculo interno',4,8,1,2,1);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Garra de dragón',4,'Los colores de sus tatuajes se corresponde con los dragones a los que veneran','Asesina 1 tropa. A continuación, si tienes 5 o más tropas de jugador en tu sala de trofeos, obtén +2 poder',1,3,2,2,3);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Cría de dragón verde',4,'Aún recién salida del huevo, el veneno de una cría de dragón es capaz de disolver huesos y corroer el acero','Pon un espía. Si hay alguna tropa de otro jugador en esa obicación, obten +2 influencia',2,4,2,2,4);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Dragón negro',7,'Quiénes presencian su oscura majestad no pueden evitar postrarse en señal de adoración','Suplanta 1 tropa blanca que esté en cualquier lugar del mapa. Obtén 1 PV por cada 3 tropas blancas que haya en tu sala de trofeos',3,7,1,2,2);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Cría de dragón rojo',5,'Lo primero que desarrolla una cría de dragón rojo es su inquebrantable arrogancia','.+2 poder. +2 influencia',3,5,2,2,3);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Sectario fanático',3,'<<¡Sentir cómo corre la sangre de los dragones por tus venas es sentirse invencible!>>','.+2 influencia. Puedes devorar 1 carta del mercado',1,4,2,2,1);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Cría de dragón blanco',2,'Las crías de dragón blanco nacen con dos impulsos básicos: el hambre y la codicia','Despliega 2 tropas. Puedes devorar 1 carta del mercado',1,3,3,2,2);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Vigilante de Thav',3,'<<Lo que buscas únicamente se puede comprar con sangre>>','Elige una opción:Pon 1 espía| Devuelve 1 de tus espías => +3 influencia',2,3,3,2,4);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Cría de dragón azul',5,'Incluso las crías de dragón azul consideran introlerable la insubordinación a los de su especie','.+3 influencia. Devuelve 1 tropa o espía de otro jugador',2,4,2,2,1);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Severin Silrajin',7,'<<Se me ha concedido la visión de un destino mucho más grande de lo que jamás habría imaginado>>','.+5 poder',4,8,1,2,3);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Encantador de Thav',4,'<<Se necesitan tus servicios, estés dispuesto a prestarlos o no>>','Elige una opción:Pon 1 espía| devuelve 1 de tus espias => +4 poder',1,3,3,2,4);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Dragón blanco',6,'<<No son los más espabilados, pero si huelen tu sangre, estarán cazándote hasta el fin de los tiempos>> --Caldoum Truespear, cazador de dragones','Despliega 3 tropas. Obtén 1 PV por cada 2 ubicaciones que controles',2,5,1,2,2);
INSERT INTO cards(name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES ('Dragón rojo',8,'<<Si alguna vez ves uno de estos, corre>> --Blacksoot, el Abrasador','Suplanta 1 tropa. Devuelve 1 espía enemigo. Obtén 1 PV por cada ubicación bajo tu control total',4,8,1,2,3);


INSERT INTO actions(id,iterations,action_name,value) VALUES 
    (1,1,'INFLUENCE',2),
    (2,1,'PROMOTE_OWN_PLAYED_CARD',1),
    (3,1,'SUPPLANT_WHITE_TROOP',null),
    (4,1,'POWER',3),
    (5,1,'XOR',null);

INSERT INTO subactions(action_id,subaction_id) VALUES (5,1),(5,2);


-- DATOS PARA PRUEBA, OJO, PARA LOS TEST, TENEIS QUE UTILIZAR LOS DATOS DE AQUI, NO VALEN LOS AUTOGENERADOS
