package org.springframework.samples.petclinic.card;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cards")
public class CardController {

    private String CARDS_LISTING_VIEW="cards/cardsListing";

    private CardService cardService;

    @Autowired
    public CardController(CardService cardService){
        this.cardService = cardService;
    }

    @GetMapping("")
    public ModelAndView showCards(){
        ModelAndView result=new ModelAndView(CARDS_LISTING_VIEW);
        result.addObject("cards", cardService.getAllCards());
        return result;
    }
}
