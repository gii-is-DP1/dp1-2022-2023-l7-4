package org.springframework.samples.petclinic.card;

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
@RequestMapping("/cards")
public class CardController {

    private String CARDS_LISTING_VIEW="cards/cardsListing";
    private String CARD_DETAILS="cards/cardDetails";
	private String CARDS_MENU="cards/cardMenu";
	private String HALFDECK_LISTING_VIEW="cards/halfDecksListing";


    private CardService cardService;

    @Autowired
    public CardController(CardService cardService){
        this.cardService = cardService;
    }

	//Card menu
	@GetMapping("/menu")
    public ModelAndView cardMenu(Card card){
        ModelAndView result=new ModelAndView(CARDS_MENU);
        return result;
	}

	//Listing card
    @GetMapping("/all")
    public String showCards(){
		return "redirect:/cards/filter?name=&deck=";
    }

    @GetMapping("/filter")
    public ModelAndView showFilterdCards(Card card,@RequestParam("name") String name, @RequestParam("deck") String deck, BindingResult result){
        ModelAndView result2=new ModelAndView(CARDS_LISTING_VIEW);
		System.out.println(name);
		System.out.println(deck);
		List<Card> filteredCards = cardService.getCardsByNameAndByHalfDeck(name,deck);
		List<HalfDeck> HalfDecks = cardService.getAllHalfDecks();

		result2.addObject("halfDecks", HalfDecks);

		if(filteredCards.isEmpty()){
			result.rejectValue("name", "notFound", "not found");
		}else{
			result2.addObject("cards", filteredCards);
		}
		return result2;
    }
	
	 @GetMapping("/{cardId}")
	 public ModelAndView showCard(@PathVariable("cardId") Integer cardId) {
	 	ModelAndView mav = new ModelAndView(CARD_DETAILS);
	 	mav.addObject("card",this.cardService.getCardById(cardId));
	 	return mav;
	 }

	//Listing by HalfDecks
	@GetMapping("/decks")
	public String showDecks(){
		return "redirect:/cards/decks/filter?name=";
	}

	@GetMapping("/decks/filter")
    public ModelAndView showFilteredDecks(HalfDeck halfDeck,@RequestParam("name") String name, BindingResult result){
        ModelAndView result2=new ModelAndView(HALFDECK_LISTING_VIEW);
		System.out.println(name);

		List<HalfDeck> filteredHalfDecks = cardService.getHalfDeckFromCard(name);

		if(filteredHalfDecks.isEmpty()){
			result.rejectValue("name", "notFound", "not found");
		}else{
			result2.addObject("halfDecks", filteredHalfDecks);
		}
		return result2;
    }
}
