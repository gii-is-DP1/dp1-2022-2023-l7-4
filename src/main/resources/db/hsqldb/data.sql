-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');

--Exercise 8.1,2,3,4:
INSERT INTO users(username,password,enabled) VALUES ('anddomrui','hola3',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'anddomrui','owner');

INSERT INTO users(username,password,enabled) VALUES ('javfercas3','secret1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (5,'javfercas3','owner');

INSERT INTO users(username,password,enabled) VALUES ('davdelcar','owner2',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (6,'davdelcar','owner');

INSERT INTO users(username,password,enabled) VALUES ('manotebar','manotebar',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (7,'manotebar','owner');

INSERT INTO users(username,password,enabled) VALUES ('javgaragu1','secret2',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (8,'javgaragu1','owner');

INSERT INTO users(username,password,enabled) VALUES ('davzarort','anuel',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (9,'davzarort','owner');

INSERT INTO users(username,password,enabled) VALUES ('daviddhc','player1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (10,'daviddhc','player');

INSERT INTO vets(id, first_name,last_name) VALUES (1, 'James', 'Carter');
INSERT INTO vets(id, first_name,last_name) VALUES (2, 'Helen', 'Leary');
INSERT INTO vets(id, first_name,last_name) VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets(id, first_name,last_name) VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets(id, first_name,last_name) VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets(id, first_name,last_name) VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');
INSERT INTO types VALUES (7, 'turtle');

INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');
-- 8.5
INSERT INTO owners VALUES (11, 'Andres', 'Dominguez', '2335 Independence La.', 'Waunakee', '6085555487', 'anddomrui');

INSERT INTO owners VALUES (12, 'David', 'Del Hoyo', '345 Maple St.', 'Madison', '6085557683', 'davdelcar');
INSERT INTO owners VALUES (13, 'Javier', 'Fernández', '1450 Oak Blvd.', 'Monona', '6085557683', 'javfercas3');
INSERT INTO owners VALUES (14, 'Manuel', 'Otero', '2335 Independence La.', 'Waunakee', '6085555487', 'manotebar');
INSERT INTO owners VALUES (15, 'Javier', 'García', '105 N. Lake St.', 'Monona', '6085552654', 'javgaragu1');
INSERT INTO owners VALUES (16, 'David', 'Zarandieta', '105 N. Lake St.', 'Monona', '6085552654', 'davzarort');


INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);
-- 8.6
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (14, 'Ramon', '2012-06-08', 1, 11);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (15, 'Harry', '2013-06-07', 2, 12);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (16, 'Ramon', '2010-04-09', 6, 13);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (17, 'huesitos', '2012-06-08', 1, 14);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (18, 'Broker', '2012-09-18', 2, 15);

INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

INSERT INTO cities(capacity,name,vp_endgame_value,starting_city,zone) VALUES (1,'EL LABERINTO',3,TRUE,1);
INSERT INTO cities(capacity,name,vp_endgame_value,starting_city,zone) VALUES (1,'BUIYRANDYN',3,FALSE,2);
INSERT INTO cities(capacity,name,vp_endgame_value,starting_city,zone) VALUES (2,'GRACKLSTUGH',6,FALSE,3);

INSERT INTO paths(city_id_1,city_id_2,capacity) VALUES (1,2,0);
INSERT INTO paths(city_id_1,city_id_2,capacity) VALUES (1,3,2);
INSERT INTO paths(city_id_1,city_id_2,capacity) VALUES (2,3,2);

INSERT INTO piece_types(name) VALUES('troop');
INSERT INTO piece_types(name) VALUES('spy');

INSERT INTO pieces(piece_type_id,player_id) VALUES(1,1);
INSERT INTO pieces(piece_type_id,player_id) VALUES(1,1);
INSERT INTO pieces(piece_type_id,player_id) VALUES(1,1);
INSERT INTO pieces(piece_type_id,player_id) VALUES(2,1);
INSERT INTO pieces(piece_type_id,player_id) VALUES(2,1);

-- INSERT INTO positions(id,zone,occupied,city_id) VALUES (1,2,TRUE,1);
-- INSERT INTO positions(id,zone,occupied,city_id) VALUES (2,2,FALSE,2);
-- INSERT INTO positions(id,zone,occupied,city_id) VALUES (3,1,TRUE,1);
-- positions se autogenera
-- INSERT INTO positions(id,zone,occupied,path_id) VALUES (4,1,TRUE,1);

INSERT INTO game(id,date,name,size,is_finished) VALUES (1,'2002-04-08','Partida 1', 3,false);

INSERT INTO players(id,name,email,birthdate,privilege,username) VALUES (1, 'David', 'daviddhc@gmail.com', '2002-04-08',true, 'daviddhc');

INSERT INTO house(id,name,description,photo,hex_color) VALUES (1,'Targarian','None', 'none','#0000');

INSERT INTO halfdecks(id,name,description) VALUES (1,'Drow','El mazo Drow presenta cartas optimizadas con costes de influencia más bajos.');
INSERT INTO halfdecks(id,name,description) VALUES (2,'Dragons','El mazo Dragones tiene muchas cartas de alto coste de influencia, y cuenta con 5 Dragones que te recompensarán por seguir una estrategia.');

INSERT INTO aspects(id,name,description,image) VALUES (1,'Ambición','Los siervos de ambición son los mejores para reclutar a otros siervos y crear un círculo interno fuerte.','');
INSERT INTO aspects(id,name,description,image) VALUES (2,'Conquista','Los siervos de conquista son los mejores para apoderarse de la Infraoscuridad.','');
INSERT INTO aspects(id,name,description,image) VALUES (3,'Malicia','Los siervos de malicia son flexibles y son los mejores en el asesinato.','');
INSERT INTO aspects(id,name,description,image) VALUES (4,'Astucia','Los siervos de astucia son los mejores para espiar e interrumpir el control.','');
INSERT INTO aspects(id,name,description,image) VALUES (5,'Obediencia','Los siervos de obediencia realizan las tareas del día a día.','');

INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (1,'Cuadrilla de mercenarios',3,'La única otra opción que les queda a quienes carecen de casa es el exilio.','Despliega 3 tropas.',1,4,2,1,2);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (2,'Maestra de espías',2,'<<Puedo ayudarte a sortear las puertas y los guardias. Lo que hagas despues es cosa tuya>>','Pon un espía',1,2,2,1,4);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (3,'Adalid',2,'<<Codícia, avarícia y poder: tres idiomas que entiendo a la perfección>>','Elige una Opcion: + 2 Influencia | Al final del turno, asciende una carta jugada durante este turno',1,2,4,1,1);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (4,'Inquisidora',3,'<<Si con mi servicio puedo complacer a la Diosa y traer honor a mi casa, me doy por satisfecha>>','Elige una Opcion: + 2 Influencia | Asesina tropa',2,4,1,1,3);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (5,'Guardia Negro',3,'Patrullan el Underdark, exterminando a intrusos perdidos, inconscientes y temerarios.','Elige una Opcion: + 2 poder| Asesina tropa',1,3,4,1,3);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (6,'Rastreadora de avanzadilla',3,'Se infiltran en el entramado de la sociedad drow sin perturbar ni una sola hierba','Suplanta 1 tropa blanca',1,3,3,1,2);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (7,'Explorador del UnderDark',3,'Su conocimiento del Underdark no tiene parangón; ningún rincón esta a salvo de sus espadas','Asesina 2 tropas blancas',2,4,2,1,2);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (8,'Elegida de Lolth',4,'<<No pienses siquiera en desobedecer. ¡Es la voluntad de la Reina Araña!>>','Devuelve 1 tropa enemíga o espía enemigo.Al final del turno, asciende carta jugada durante este turno',2,4,2,1,2);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (9,'Cazarrecompensas',4,'Toda vida tiene un precio','.+3 Poder',2,4,2,1,3);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (10,'Doppelganger',5,'<<Bonita vida la tuya. Creo que me la voy a quedar>>','Suplanta 1 tropa blanca',3,5,2,1,3);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (11,'Maestros de Sorcere',5,'Las maestras de Sorcere cumplen dos propósitos; enseñar a los estudiantes y garantizar la lealtad a Lolth','Elige una opción:Pon 2 espías| Devuelve 1 de tus espías=> +4 poder',2,5,1,1,4);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (12,'Maestro de Melee-Magthere',5,'Las instrucciones de Melee-Magthere se cuentan entre los mejores guerreros de Menzoberranzan','Elige una opción:Despliega 4 tropas|Suplanta 1 tropa blanca',2,5,2,1,2);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (13,'Infiltrador',3,'<<La perfección solo puede alcanzarse cuando el precio del fracaso es la muerte>>','Pon un espía. Si hay alguna tropa de otro jugador en esa obicación, obten +1 poder',1,2,2,1,4);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (14,'Maestro de armas',6,'<<O resultas ser digno o perecerás. Tu destino depende de ti. >> -- Shoor Vandree','Elige 3 veces: Despliega 1 tropa| Asesina 1 tropa blanca',3,6,1,1,2);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (15,'Filo letal',5,'<<Ojalá te señalen los Filos letales.>>--Maldición drow','Asesina 2 tropas',3,6,1,1,3);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (16,'Miembro del consejo',6,'En las cámaras del consejo drow, la intriga es una forma de arte.','Mueve hasta 2 tropas enemigas. Al final del turno, asciende otra carta jugada',3,6,1,1,1);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (17,'Hilador de conjuros',3,'Nunca sabrás que tu mente no te pertenece','Elige una opción:Pon 1 espía| Devuelve 1 de tus espías=> Suplanta 1 tropa que esté en la misma ubicación que ese espía',1,3,3,1,4);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (18,'Matrona',6,'La voluntad de la matrona es absoluta','Pon tu mazo en tu pila de descartes=>Asciende 1 carta de tu pila de descartes',3,6,1,1,1);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (19,'Traficante de información',5,'<<¿Por qué malgastar hechizos y acero cuando unas simples palabras son capaces de derribar una casa?>>','Elige una opción:Pon 1 espía| Devuelve 1 de tus espías=> Roba 3 cartas',2,5,2,1,4);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (20,'Negociadora Drow',3,'<<Puedo abrir puertas que están cerradas para todos excepto para las matronas.>>','Si hay 4 cartas o más en tu círculo interno, +3 influencia. Al final del turno, asciende otra carta jugada durante este turno',1,2,2,1,1);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (21,'Kobold',1,'Aunque pueden llegar a vivir más de un siglo, la mayoría parece antes de cumplir un puñado de décadas','Elige una opción:Despliega 1 tropa| Asesina 1 tropa blanca',1,2,3,2,2);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (22,'Sectario del Culto del Dragón',3,'Su misión en la vida es alumbrar una era de dominación','Elige una opción:+2 influencia| +2 poder',1,4,4,2,3);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (23,'Dragón verde',7,'Los dragones verdes miden sus planes en siglos','Elige una opción:Pon un espía y, a continuación, suplanta 1 tropa que esté en la misma ubicación que ese espía | Devuelve 1 de tus espías=>Suplanta una tropa que esté en la misma ubicación que ese espía y, a continuación, obtén 1 PV por cada marcador de control de ubicación que tengas',3,7,1,2,4);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (24,'Clérigo de Laogzed',4,'Lo único que supera su hedor es su hambre de carne fresca','Mueve 1 tropa enemiga. Al final del turno, asciende otra carta jugada durante este turno',2,4,2,2,1);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (25,'Rath Modar',6,'<<Los dragones son herramientas, y con ellas construiré imperios>>','Roba 2 cartas. Pon 1 espía',2,5,1,2,4);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (26,'Cría de dragón negro',3,'Tantos bocaditos apetitosos y tan poco tiempo','.+1 influencia. Asesina 1 tropa blanca',1,4,2,2,2);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (27,'Voz de dragón',3,'Fanáticos dotados de la capacidad de convencer a los dragones para que se unan a su causa','.+1 influencia. Al final del turno, asciende otra carta jugada durante este turno',1,3,3,2,1);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (28,'Dragón azul',8,'<<En la superficie o en el subsuelo, da igual. Los dragones serán los amos de todo>>','Al final del turno, asciende hasta otras 2 cartas jugadas durante este turno y, a continuación, obtén 1 PV por cada 3 cartas ascendidas que tengas en tu círculo interno',4,8,1,2,1);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (29,'Garra de dragón',4,'Los colores de sus tatuajes se corresponde con los dragones a los que veneran','Asesina 1 tropa. A continuación, si tienes 5 o más tropas de jugador en tu sala de trofeos, obtén +2 poder',1,3,2,2,3);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (30,'Cría de dragón verde',4,'Aún recién salida del huevo, el veneno de una cría de dragón es capaz de disolver huesos y corroer el acero','Pon un espía. Si hay alguna tropa de otro jugador en esa obicación, obten +2 influencia',2,4,2,2,4);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (31,'Dragón negro',7,'Quiénes presencian su oscura majestad no pueden evitar postrarse en señal de adoración','Suplanta 1 tropa blanca que esté en cualquier lugar del mapa. Obtén 1 PV por cada 3 tropas blancas que haya en tu sala de trofeos',3,7,1,2,2);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (32,'Cría de dragón rojo',5,'Lo primero que desarrolla una cría de dragón rojo es su inquebrantable arrogancia','.+2 poder. +2 influencia',3,5,2,2,3);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (33,'Sectario fanático',3,'<<¡Sentir cómo corre la sangre de los dragones por tus venas es sentirse invencible!>>','.+2 influencia. Puedes devorar 1 carta del mercado',1,4,2,2,1);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (34,'Cría de dragón blanco',2,'Las crías de dragón blanco nacen con dos impulsos básicos: el hambre y la codicia','Despliega 2 tropas. Puedes devorar 1 carta del mercado',1,3,3,2,2);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (35,'Vigilante de Thav',3,'<<Lo que buscas únicamente se puede comprar con sangre>>','Elige una opción:Pon 1 espía| Devuelve 1 de tus espías => +3 influencia',2,3,3,2,4);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (36,'Cría de dragón azul',5,'Incluso las crías de dragón azul consideran introlerable la insubordinación a los de su especie','.+3 influencia. Devuelve 1 tropa o espía de otro jugador',2,4,2,2,1);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (37,'Severin Silrajin',7,'<<Se me ha concedido la visión de un destino mucho más grande de lo que jamás habría imaginado>>','.+5 poder',4,8,1,2,3);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (38,'Encantador de Thav',4,'<<Se necesitan tus servicios, estés dispuesto a prestarlos o no>>','Elige una opción:Pon 1 espía| devuelve 1 de tus espias => +4 poder',1,3,3,2,4);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (39,'Dragón blanco',6,'<<No son los más espabilados, pero si huelen tu sangre, estarán cazándote hasta el fin de los tiempos>> --Caldoum Truespear, cazador de dragones','Despliega 3 tropas. Obtén 1 PV por cada 2 ubicaciones que controles',2,5,1,2,2);
INSERT INTO cards(id,name,cost,story,rules_text,deck_pv,inner_circle_pv,rarity,half_deck_id,aspect_id) VALUES (40,'Dragón rojo',8,'<<Si alguna vez ves uno de estos, corre>> --Blacksoot, el Abrasador','Suplanta 1 tropa. Devuelve 1 espía enemigo. Obtén 1 PV por cada ubicación bajo tu control total',4,8,1,2,3);





