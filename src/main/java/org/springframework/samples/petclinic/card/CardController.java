package org.springframework.samples.petclinic.card;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/card")
public class CardController {

    private String CARDS_LISTING_VIEW="cards/cardsListing";
    private String CARDS_SEARCHING="cards/findCards";
	private String CARDS_MENU="cards/cardMenu";
	private String CARDS_BY_DECK="cards/cardsDeck";



    private CardService cardService;

    @Autowired
    public CardController(CardService cardService){
        this.cardService = cardService;
    }

	//Card menu
	@GetMapping("/cardmenu")
    public ModelAndView cardMenu(){
        ModelAndView result=new ModelAndView(CARDS_MENU);
        return result;
	}

    @GetMapping("/cards")
    public ModelAndView showCards(){
        ModelAndView result=new ModelAndView(CARDS_LISTING_VIEW);
        result.addObject("cards", cardService.getAllCards());
        return result;
    }

	//find card by name
    @GetMapping(value = "/cards/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("card", new Card());
		return CARDS_SEARCHING;
	}

	@GetMapping(value = "/searchingCard")
	public String processFindForm(Card card, BindingResult result, Map<String, Object> model) {
		Collection<Card> results = this.cardService.getCardByName(card.getName());
		if (results.size() == 1) {
			// 1 owner found
			card = results.iterator().next();
			return "redirect:/card/searchingCard/" + card.getId();
		}
		else {
			// no owners found
			result.rejectValue("name", "notFound", "not found");
			return "cards/findCards";
		}
	}
	
	@GetMapping("/searchingCard/{cardId}")
	public ModelAndView showCard(@PathVariable("cardId") Integer cardId) {
		ModelAndView mav = new ModelAndView("cards/cardDetails");
		mav.addObject("id",this.cardService.getCardById(cardId));
		return mav;
	}

	//Listing card by Deck
    @GetMapping(value = "/cards/findDeck")
	public String initFindDeckForm(Map<String, Object> model) {
		model.put("card", new Card());
		return CARDS_BY_DECK;
	}

	@GetMapping(value = "/searchingDeck")
	public String processFindDeckForm(Card card, BindingResult result, Map<String, Object> model) {
		// allow parameterless GET request for /owners to return all records
		if (card.getName() == null) {
			card.setName(""); // empty string signifies broadest possible search
		}

		Collection<Card> results = this.cardService.getCardByName(card.getName());
		if (results.size() == 1) {
			// 1 owner found
			card = results.iterator().next();
			return "redirect:/searching/" + card.getId();
		}
		else {
			// no owners found
			result.rejectValue("name", "notFound", "not found");
			return "cards/findCards";
		}
	}

}
