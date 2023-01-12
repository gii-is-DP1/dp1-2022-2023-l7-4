package org.springframework.samples.tyrantsOfTheUnderdark.card.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.tyrantsOfTheUnderdark.card.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/actions")
public class ActionController {
    
    private final String ACTION_LISTING="actions/allActionsView";

    @Autowired
    private CardService cardService;


    @GetMapping("")
    public ModelAndView showAllActions(){
        ModelAndView result=new ModelAndView(ACTION_LISTING);
        result.addObject("cards", cardService.getAllCards());
        return result;
    }


}
