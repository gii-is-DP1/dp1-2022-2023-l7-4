package org.springframework.samples.petclinic.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {
	
		private final String HOMEMENU="home/homeMenu";

		@GetMapping("")
		public ModelAndView showHomeMenu(){
			ModelAndView result=new ModelAndView(HOMEMENU);
			return result;
		}
}
