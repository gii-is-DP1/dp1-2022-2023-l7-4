package org.springframework.samples.petclinic.player;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PlayerController {

    private static final String VIEWS_PLAYER_CREATE_OR_UPDATE_FORM = "players/createOrUpdatePlayerForm";

    private static final String VIEWS_PLAYER_CREATE_FORM = "users/createPlayerForm";

    @Autowired
    private PlayerService playerService;

    @GetMapping(value = "/player/new")
	public String initCreationForm(Map<String, Object> model) {
		Player player = new Player();
		model.put( "player", player);
		return VIEWS_PLAYER_CREATE_FORM;
	}

    @PostMapping(value = "/player/new")
	public String processCreationForm(@Valid Player player, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_PLAYER_CREATE_FORM;
		}
		else {
			//creating player, user and authorities
			this.playerService.savePlayer(player);
			
			return "redirect:/players/" + player.getId();
		}
	}

    @GetMapping(value = "/players/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("player", new Player());
		return "players/findPlayers";
	}

	@GetMapping(value = "/players")
	public String processFindForm(Player player, BindingResult result, Map<String, Object> model) {

		
		if (player.getName()== null) {
			player.setName("");
		}
		Collection<Player> results = playerService.getPlayerByName(player.getName());
		if (results.isEmpty()) {
			result.rejectValue("name", "notFound", "not found");
			return "players/findPlayers";
		}
		else if (results.size() == 1) {
			player = results.iterator().next();
			return "redirect:/players/" + player.getId();
		}
		else {
			model.put("selections", results);
			return "players/playersList";
		}
	}

	@GetMapping(value = "/players/list")
	public String proccesPlayersListing(Map<String, Object> model){
		model.put("selections", playerService.getPlayers());
		return "players/playersList";
	}

	@GetMapping(value = "/players/{playerId}/edit")
	public String initUpdateplayerForm(@PathVariable("playerId") int playerId, Model model) {
		Player player = playerService.getPlayerById(playerId);
		model.addAttribute(player);
		return VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/players/{playerId}/edit")
	public String processUpdateplayerForm(@Valid Player player, BindingResult result,
			@PathVariable("playerId") int playerId) {
		if (result.hasErrors()) {
			return VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
		}
		else {
			player.setId(playerId);
			playerService.savePlayer(player);
			return "redirect:/players/{playerId}";
		}
	}
	@GetMapping("/players/{playerId}")
	public ModelAndView showplayer(@PathVariable("playerId") int playerId) {
		ModelAndView mav = new ModelAndView("players/playerDetails");
		mav.addObject(playerService.getPlayerById(playerId));
		return mav;
	}
    
}