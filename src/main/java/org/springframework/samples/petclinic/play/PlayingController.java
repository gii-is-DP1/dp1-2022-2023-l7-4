package org.springframework.samples.petclinic.play;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/currentgame")
public class PlayingController {

	private static final String PRINCIPAL_GAME = "playing/playingGame";

	//Vista principal de la partida 
	@GetMapping("/play")
	public ModelAndView showPrincipalView(){
        ModelAndView result=new ModelAndView(PRINCIPAL_GAME);
        return result;
	}
}
