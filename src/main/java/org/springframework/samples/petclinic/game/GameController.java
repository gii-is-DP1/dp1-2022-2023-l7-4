package org.springframework.samples.petclinic.game;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.samples.petclinic.card.CardServiceRepo;
import org.springframework.samples.petclinic.card.HalfDeck;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	CardServiceRepo cardServiceRepo;

	Authentication auth = SecurityContextHolder.getContext().getAuthentication();


    private static final String VIEWS_GAME_CREATE_FORM = "games/createGameForm";
	private static final String VIEWS_NEWGAME_CREATE_FORM = "games/newGame";
    
	@GetMapping(value = "/games/create")
	public String initCreationGameForm(Map<String, Object> model){
		List<HalfDeck> halfDecks = cardServiceRepo.getAllHalfDecks();
		Game game = new Game();
		model.put("game", game);
		model.put("halfDecks", halfDecks);
		return VIEWS_GAME_CREATE_FORM;
	}
    @GetMapping(value = "/games/create2")
	public String initCreationForm(Map<String, Object> model, Principal currentUser) {
		List<User> users = (List<User>) userService.getUsers();
		User creatorsUser = userService.getUserByUsername(currentUser.getName());
		users.remove(creatorsUser);

		List<User> players = new ArrayList<>();
		players.add(creatorsUser);

		model.put("users", users);
		model.put("players", players);
		return VIEWS_NEWGAME_CREATE_FORM;
    }

	@PostMapping(value = "/games/create/add/{userId}")
	public String postCreatingGameAddPlayer(@PathVariable("userId") String userId, GameForm gameForm, Map<String, Object> model) {
		User addedUser = userService.getUserByUsername(userId);
		
		List<String> playerIds = gameForm.getUsers();
		List<User> players = new ArrayList<>();
		for(String i:playerIds) {
			players.add(userService.getUserByUsername(i));
		}
		
		players.add(addedUser);
		List<User> users = (List<User>) userService.getUsers();
		users.removeAll(players);
		
		model.put("users", users);
		model.put("players", players);
		return VIEWS_NEWGAME_CREATE_FORM;
	}
	@PostMapping(value = "/games/create/remove/{playerNum}")
	public String creatingGameRemovePlayer(@PathVariable("playerNum") int playerNum, GameForm gameForm, Map<String, Object> model) {
		List<String> playerIds = gameForm.getUsers();
		List<User> players = new ArrayList<User>();
		for(String i:playerIds) {
			players.add(userService.getUserByUsername(i));
		}
		
		players.remove(playerNum);
		
		List<User> users = (List<User>) userService.getUsers();
		users.removeAll(players);
		
		model.put("users", users);
		model.put("players", players);
		return VIEWS_NEWGAME_CREATE_FORM;
	}

    @PostMapping(value = "/games/create")
	public String processCreationForm(@Valid Game game, BindingResult result, Map<String, Object> model ) {
		if (result.hasErrors()) {
            model.put("game", game);
			System.out.println(result);
			return VIEWS_GAME_CREATE_FORM;
		}
		else {
			List<HalfDeck> gHalfDecks = game.getHalfdecks();
			List<String> gameHalfDecksname = new ArrayList<>();
			for(HalfDeck h:gHalfDecks){
				gameHalfDecksname.add(h.getName());
			}
			System.out.println(gameHalfDecksname);
			this.gameService.saveGame(game);
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
	public String proccesGameListing(Map<String, Object> model){
		model.put("selections", gameService.getGames());
		return "games/gameList";
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(List.class, "halfdecks", new CustomCollectionEditor(List.class) {
			@Override
			protected Object convertElement(Object element) {
				List<HalfDeck> halfDeck = cardServiceRepo.getHalfDeckByCard((String) element);
				return halfDeck;
			}
		});
	}
    
}
