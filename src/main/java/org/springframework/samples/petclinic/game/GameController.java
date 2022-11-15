package org.springframework.samples.petclinic.game;

import java.security.Key;
import java.security.Principal;
import java.sql.PseudoColumnUsage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
			this.gService.saveGame(game);
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
    
}
