package org.springframework.samples.petclinic.game;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GameController {
    @Autowired
    GameService gService;
	@Autowired
	PlayerService pService;
	@Autowired
	UserService uService;
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();


    private static final String VIEWS_GAME_CREATE_FORM = "games/createGameForm";
    
    @GetMapping(value = "/games/create")
	public String initCreationForm(Map<String, Object> model) {
		Game game = new Game();
		model.put("game", game);
		return VIEWS_GAME_CREATE_FORM;
    } 

    @PostMapping(value = "/games/create")
	public String processCreationForm(@Valid Game game, BindingResult result, Map<String, Object> model ) {
		if (result.hasErrors()) {
            model.put("game", game);
			return VIEWS_GAME_CREATE_FORM;
		}
		else {
			this.gService.save(game);
			return "redirect:/games/" + game.getId();
		}
	}
	@GetMapping("/games/{gameId}")
		public ModelAndView showgame(@PathVariable("gameId") int gameId) {
			ModelAndView mav = new ModelAndView("games/gameDetails");
			mav.addObject(this.gService.getGameById(gameId));

			return mav;
		}

	@GetMapping("/games/join/{gameId}")
		public String joinGame(@PathVariable("gameId") int gameId, Principal user) {
			String name= user.getName();
			Player player= this.pService.getPlayerByUsername(name);
			Game game = this.gService.getGameById(gameId);
			game.addPlayer(player);
			gService.save(game);
			player.setGame(game);
			pService.savePlayer(player);
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
		Collection<Game> results = this.gService.getGameByName(game.getName());
		
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
		model.put("selections", gService.getGames());
		return "games/gameList";
	}

	/* @PostMapping(value = "/games/creating/add/{userId}")
	public String postCreatingGameAddPlayer(@PathVariable("userId") int userId, GameForm gameForm, Map<String, Object> model) {
		User addedUser = userService.findUser(userId).get();
		
		List<Integer> playerIds = gameForm.getUsers();
		List<User> players = new ArrayList<User>();
		for(Integer i:playerIds) {
			players.add(userService.findUser(i).get());
		}
		
		players.add(addedUser);
		List<User> users = userService.findAll();
		users.removeAll(players);
		
		model.put("users", users);
		model.put("players", players);
		return VIEWS_NEW_GAME;
	} */
    
}
