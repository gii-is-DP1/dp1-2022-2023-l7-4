package org.springframework.samples.tyrantsOfTheUnderdark.web;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {

	
	
		private final String HOMEMENU="welcome";

		@GetMapping("")
		public ModelAndView showHomeMenu(Principal userPrincipal){
			ModelAndView result=new ModelAndView(HOMEMENU);
			return result;
		}
}
