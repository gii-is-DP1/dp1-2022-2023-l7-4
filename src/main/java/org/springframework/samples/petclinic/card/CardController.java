package org.springframework.samples.petclinic.card;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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

	//Listing card
    @GetMapping("/cards")
    public ModelAndView showCards(Card card,@RequestParam String name,BindingResult result ){
        ModelAndView result3=new ModelAndView(CARDS_LISTING_VIEW);
		List<Card> filteredCards = cardService.getCardsByName(name);
		if(filteredCards.isEmpty()){
		result.rejectValue("name", "notFound", "not found");
		}else{
			result3.addObject("cards", filteredCards);
		}
		return result3;
    }



	
	// @GetMapping("/searchingCard/{cardId}")
	// public ModelAndView showCard(@PathVariable("cardId") Integer cardId) {
	// 	ModelAndView mav = new ModelAndView("cards/cardDetails");
	// 	mav.addObject("id",this.cardService.getCardById(cardId));
	// 	return mav;
	// }

	//find card by deck
	@GetMapping(value = "/searchingDeck")
	public String showCardsByDeck(Card card, HalfDeck halfDeck, BindingResult result, Map<String, Object> model) {
		Collection<Card> results = this.cardService.getCarsdByHalfDeck(halfDeck.getName());
		if (results.size() != 0) {
			// 1 owner found
			card = results.iterator().next();
			return "redirect:/card/deck/" + halfDeck.getName();
		}
		else {
			// no owners found
			result.rejectValue("name", "notFound", "not found");
			return CARDS_BY_DECK;
		}
	}

	@GetMapping("/deck/{halfDeck}")
	public ModelAndView searchCardByDeck(@PathVariable("halfDeck") String halfDeck) {
		ModelAndView mav = new ModelAndView("cards/deckDetails");
		mav.addObject("id",this.cardService.getCarsdByHalfDeck(halfDeck));
		return mav;
	}
	/*@GetMapping("/searchingDeck")
	public String showCardsByDeck(Model model){
		model.addAttribute("card", new Card());
		return CARDS_BY_DECK;
	}

	@GetMapping("/deck")
	public String searchCardByDeck(@RequestParam String halfdeck, Model model, @ModelAttribute("card") Card card){
		model.addAttribute("cardByDeck", cardService.getCarsdByHalfDeck(halfdeck));
		return CARDS_BY_DECK;
	}
*/

}
