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
import org.springframework.web.bind.annotation.RequestParam;
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
	private static final String GAME_LISTING_VIEW="games/gameList";
	private static final String TO_PLAY="redirect:/play/{gameId}";

    


	@PostMapping(value = "/games/create/plus")
	public String plus(Map<String, Object> model,Game game){
		List<HalfDeck> availableDecks = cardService.getAvailableHalfDecks(game);
		List<MapTemplate> availableMaps = mapTemplateService.getAvailableMaps(game);
		game.getPlayers().removeIf(Objects::isNull);
		List<User> availableUsers = userService.getAvailableUsers(game);
		model.put("availableDecks", availableDecks);
		model.put("availableMaps", availableMaps);
		model.put("availableUsers", availableUsers);
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
		
		model.put("availableUsers", availableUsers);
		model.put("game", game);
		return  VIEWS_GAME_CREATE_FORM;
	}




	@PostMapping(value = "/games/create/confirm")
	public String processCreationForm(@Valid Game game, BindingResult result, Map<String, Object> model,Principal currentUser ) {
		if (result.hasErrors()) {
            model.put("game", game);
			model.put("status","Entre  1 y 30 caracteres");
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

	@GetMapping("/games/{gameId}/choose-mode")
	public ModelAndView chooseMode(@PathVariable("gameId") Integer gameId){
		ModelAndView result=null;
		Game game=this.gameService.getGameById(gameId);
		if(!game.getMapTemplate().hasPreloadedTroops() || !game.isNotLoaded()){
			result=new ModelAndView(TO_PLAY);
		}else{
			result=new ModelAndView("games/chooseMode");
			result.addObject("gameId",gameId);
			result.addObject("modes",List.of("Automatico","Manual"));
		}
		return result;
	}

	@GetMapping("/games/{gameId}/process-mode")
	public ModelAndView processMode(@PathVariable("gameId") Integer gameId
	,@RequestParam("mode") String mode){
		Game game=this.gameService.getGameById(gameId);
		System.out.println(mode);
		System.out.println(game.isNotLoaded());
		if(game.isNotLoaded()){
			game.setRound(-1);
			if(mode.toLowerCase().trim().equals("manual")){
				game.setAutomaticWhiteTroops(false);
			}else{
				game.setAutomaticWhiteTroops(true);
			}
		}
		this.gameService.save(game);
		System.out.println(this.gameService.getGameById(gameId).getAutomaticWhiteTroops());
		ModelAndView result=new ModelAndView(TO_PLAY);
		return result;

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
	@GetMapping("/games")
	public ModelAndView showGamesByFilteredName(@RequestParam("name") String name,
	@RequestParam("state") String state,Principal user){
		if(name==null) name="";
		if(state==null) state="";
		ModelAndView res=new ModelAndView(GAME_LISTING_VIEW);
		User loggedUser=this.userService.getUserByUsername(user.getName());
		List<Game> filteredGames=null;
		if(!loggedUser.isAdmin()){
			filteredGames=this.gameService.getMyGamesByNameAndState(loggedUser.getUsername(), name, state);
			res.addObject("text","Mis partidas");
		}else{
			filteredGames=this.gameService.getGamesByNameAndState(name, state);
			res.addObject("text","Lista de partidas del sistema");
		}
		res.addObject("selections",filteredGames);
		res.addObject("states", List.of("Acabados","Pendientes","Todos"));
		res.addObject("result",loggedUser.isAdmin());
		res.addObject("search",filteredGames.isEmpty());
		return res;
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
