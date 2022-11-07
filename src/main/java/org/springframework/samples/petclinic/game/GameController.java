package org.springframework.samples.petclinic.game;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GameController {
    @Autowired
    GameService gService;

    private static final String VIEWS_GAME_CREATE_FORM = "";
    
    @GetMapping(value = "/game/create")
	public String initCreationForm(Map<String, Object> model) {
		Game game = new Game();
		model.put("game", game);
		return VIEWS_GAME_CREATE_FORM;

        
    
    } 
    @PostMapping(value = "/game/create")
	public String processCreationForm(@Valid Game game, BindingResult result, Map<String, Object> model) {
        
		if (result.hasErrors()) {
            model.put("game", game);
			return VIEWS_GAME_CREATE_FORM;
		}
		else {
			//creating owner, user, and authority
			this.gService.saveHouse(game);
			return "welcome";
		}
	}
    }