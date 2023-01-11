package org.springframework.samples.tyrantsOfTheUnderdark.game;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.samples.tyrantsOfTheUnderdark.board.map.MapTemplate;
import org.springframework.samples.tyrantsOfTheUnderdark.board.map.MapTemplateService;
import org.springframework.samples.tyrantsOfTheUnderdark.card.CardService;
import org.springframework.samples.tyrantsOfTheUnderdark.card.HalfDeck;
import org.springframework.samples.tyrantsOfTheUnderdark.player.Player;
import org.springframework.samples.tyrantsOfTheUnderdark.player.PlayerService;
import org.springframework.samples.tyrantsOfTheUnderdark.user.Authorities;
import org.springframework.samples.tyrantsOfTheUnderdark.user.AuthoritiesService;
import org.springframework.samples.tyrantsOfTheUnderdark.user.User;
import org.springframework.samples.tyrantsOfTheUnderdark.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GameController {
    @Autowired
    GameService gameService;
	@Autowired
	PlayerService playerService;
	@Autowired
	UserService userService;
	@Autowired
	CardService cardService;
	@Autowired
	AuthoritiesService authService;

	@Autowired
	MapTemplateService mapTemplateService;
	


    private static final String VIEWS_GAME_CREATE_FORM = "games/createGameForm";
    


	@PostMapping(value = "/games/create/plus")
	public String plus(Map<String, Object> model,Game game){
		List<HalfDeck> availableDecks = cardService.getAvailableHalfDecks(game);
		List<MapTemplate> availableMaps = mapTemplateService.getAvailableMaps(game);
		game.getPlayers().removeIf(Objects::isNull);
		List<User> availableUsers = userService.getAvailableUsers(game);
		
		model.put("availableDecks", availableDecks);
		model.put("availableMaps", availableMaps);
		model.put("availableUsers", availableUsers);
		System.out.println(game);
		return VIEWS_GAME_CREATE_FORM;
	}

	@GetMapping(value = "/games/create")
	public String initCreationGameForm(Map<String, Object> model, Principal currentUser){
		List<HalfDeck> halfDecks = cardService.getAllHalfDecks();

		HalfDeck drow = halfDecks.get(0);
		HalfDeck dragons = halfDecks.get(1);
		MapTemplate defautlMap = mapTemplateService.defaultMap();

		Game game = new Game();
		game.setFirstHalfDeck(drow);
		game.setSecondHalfDeck(dragons);
		game.setName("Mi nueva partida");
		game.setMapTemplate(defautlMap);
	
		List<User> availableUsers = userService.getUsersList();
		User loggedUser = userService.getUserByUsername(currentUser.getName());
		availableUsers.remove(loggedUser);
		
		Player player1 = Player.of(loggedUser);
		game.getPlayers().add(player1);
		
		System.out.println(game);
		
		model.put("availableUsers", availableUsers);
		model.put("game", game);
		return  VIEWS_GAME_CREATE_FORM;
	}




	//confirmar juego TODO SALVAR TODOS LOS JUGADORES AÑADIENDO EL GAME Y DESPUES SALVAR EL GAME
	@PostMapping(value = "/games/create/confirm")
	public String processCreationForm(@Valid Game game, BindingResult result, Map<String, Object> model,Principal currentUser ) {
		if (result.hasErrors()) {
            model.put("game", game);
			System.out.println(result);
			return VIEWS_GAME_CREATE_FORM;
		}
		else {
			playerService.setHouses(game);
			var players = game.getPlayers();
			game.setPlayers(null);
			gameService.saveGame(game);
			game.setPlayers(players);
			playerService.savePlayers(game);

			return "redirect:/games/" + game.getId();
		}
	}


	@GetMapping("/games/{gameId}")
		public ModelAndView showgame(@PathVariable("gameId") int gameId) {
			ModelAndView mav = new ModelAndView("games/gameDetails");
			mav.addObject(this.gameService.getGameById(gameId));

			return mav;
		}

	@GetMapping("/games/join/{gameId}")
		public String joinGame(@PathVariable("gameId") int gameId, Principal user) {
			String name= user.getName();
			Player player= this.playerService.getPlayerByUsername(name);
			Game game = this.gameService.getGameById(gameId);
			game.addPlayer(player);
			gameService.saveGame(game);
			player.setGame(game);
			playerService.savePlayer(player);
			return "welcome";
	}

	@GetMapping(value = "/games/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("game", new Game());
		return "games/findGames";
	}
	@GetMapping(value = "/games")
	public String processFindForm(Game game, BindingResult result, Map<String, Object> model) {

		
		if (game.getName()== null) {
			game.setName("");
		}
		Collection<Game> results = this.gameService.getGameByName(game.getName());
		
		if (results.isEmpty()) {
			result.rejectValue("name", "notFound", "not found");
			return "games/findGames";
		}
		else if (results.size() == 1) {
			game = results.iterator().next();
			return "redirect:/games/" + game.getId();
		}
		else {
			model.put("selections", results);
			return "games/gamesList";
		}
	}
	@GetMapping(value = "/games/list")
	public String proccesGameListing(Map<String, Object> model, Principal user){
		String name= user.getName();
		User user2= this.userService.getUserByUsername(name);
		Player player= this.playerService.getPlayerByUsername(name);
		if(authService.getAuthoritiesByName("admin").stream().filter(a->a.getUser()
		.equals(user2)).collect(Collectors.toList()).size()==1){ 
			model.put("selections", gameService.getGames());
			return "games/gameList";
		 }else{
			model.put("selections", gameService.getGamesByUser(player));
			return "games/gameList";
			}
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(List.class, "players", new CustomCollectionEditor(List.class) {
			@Override
			protected Object convertElement(Object element) {
				Player player = new Player();
				User user = userService.getUserByName((String) element);
				player.setName(user.getName());
				player.setUser(user);
				playerService.savePlayer(player);
				List<Player> players = new ArrayList<>();
				players.add(player);
				return players;
			}

		});
	}
    
}
