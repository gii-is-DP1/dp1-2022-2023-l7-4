package org.springframework.samples.petclinic.card;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
		return "redirect:/cards/filter?name=&deck=&page=1";
    }

    @GetMapping("/filter")
    public ModelAndView showFilterdCards(Card card,@RequestParam("name") String name,
	@RequestParam("page") Integer page, @RequestParam("deck") String deck){
        ModelAndView mav=new ModelAndView(CARDS_LISTING_VIEW);
		
		Integer cardsByPage = 9;
		Integer cardsCount = cardService.getCardsFilteredBy(name, deck).size();
		// Integer numOfPages=(cardsByPage>cardsCount) ? 1 : (cardsCount/cardsByPage);
		Integer numOfPages=(int)Math.ceil((double) cardsCount/cardsByPage);
		numOfPages = numOfPages==0?1:numOfPages;
		page = ((page<1) ? 1 :(page>numOfPages?numOfPages:page)); //inside range [1,numOfPages]
		//o
		List<Card> cardsInPage = cardService.getCardsByNameAndByHalfDeckPageable(name,deck,page,cardsByPage);
		List<HalfDeck> HalfDecks = cardService.getAllHalfDecks();
		List<Integer> pages=IntStream.rangeClosed(1, numOfPages).boxed().collect(Collectors.toList());
		
		if(cardsInPage.isEmpty()) 
			mav.addObject("notFound", true);
		else 
			mav.addObject("cards", cardsInPage);
		mav.addObject("halfDecks", HalfDecks);
		mav.addObject("pages",pages);
		mav.addObject("numberOfPages",numOfPages);
		mav.addObject("currentPage",page);
		return mav;
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


		List<HalfDeck> filteredHalfDecks = cardService.getHalfDeckByCard(name);

		if(filteredHalfDecks.isEmpty()){
			result.rejectValue("name", "notFound", "not found");
		}else{
			result2.addObject("halfDecks", filteredHalfDecks);
		}
		return result2;
    }

	
}
