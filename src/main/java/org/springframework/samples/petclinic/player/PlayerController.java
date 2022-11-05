package org.springframework.samples.petclinic.player;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PlayerController {

    private static final String VIEWS_PLAYER_CREATE_OR_UPDATE_FORM = "players/createPlayerForm";

    @Autowired
    private PlayerService playerService;

    @GetMapping(value = "/players/new")
	public String initCreationForm(Map<String, Object> model) {
		Player player = new Player();
		model.put( "player", player);
		return VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
	}

    @PostMapping(value = "/players/new")
	public String processCreationForm(@Valid Player player, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
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
		return "players/findplayers";
	}

	@GetMapping(value = "/players")
	public String processFindForm(Player player, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /players to return all records
		if (player.getUser().getUsername() == null) {
			player.setUser(new User());// empty string signifies broadest possible search
		}

		// find players by last name
		Collection<Player> results = playerService.getPlayerByUsername(player.getUser().getUsername());
		if (results.isEmpty()) {
			// no players found
			result.rejectValue("username", "notFound", "not found");
			return "players/findplayers";
		}
		else if (results.size() == 1) {
			// 1 player found
			player = results.iterator().next();
			return "redirect:/players/" + player.getId();
		}
		else {
			// multiple players found
			model.put("selections", results);
			return "players/playersList";
		}
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

	/**
	 * Custom handler for displaying an player.
	 * @param playerId the ID of the player to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/players/{playerId}")
	public ModelAndView showplayer(@PathVariable("playerId") int playerId) {
		ModelAndView mav = new ModelAndView("players/playerDetails");
		mav.addObject(playerService.getPlayerById(playerId));
		return mav;
	}
    
}