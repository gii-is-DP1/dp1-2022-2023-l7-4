package org.springframework.samples.petclinic.card;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardService {
    
    CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository){
        this.cardRepository = cardRepository;
    }

    //Card
    @Transactional(readOnly = true)
    public List<Card> getCardsFilteredBy(String name, String deck) {
        return cardRepository.findCardsByNameAndByHalfDeck(name, deck);
    }

    @Transactional(readOnly = true)
    public List<Card> getCardsByNameAndByHalfDeckPageable(String name,String deck,Integer actualPage, Integer cardsByPage){
        Pageable pageable = PageRequest.of(actualPage-1,cardsByPage);
        return cardRepository.findCardsByNameAndByHalfDeckPageable(name, deck, pageable);
    }

    @Transactional(readOnly = true)
    public Card getCardById(Integer id) {
        return cardRepository.findCardById(id);
    }

    //HalfDeck
    @Transactional(readOnly = true)
    public List<HalfDeck> getAllHalfDecks() {
        return cardRepository.findAllDecks();
    }

    @Transactional(readOnly = true)
    public List<HalfDeck> getHalfDeckByCard(String name) {
        return cardRepository.findHalfDecksByName(name);
    }
    @Transactional(readOnly = true)
    public HalfDeck getHalfDeckById(Integer id){
        return cardRepository.findHalfDeckById(id);
    }

    public List<HalfDeck> getAvailableHalfDecks(Game game) {
        return getAllHalfDecks().stream()
        .filter(deck-> deck != game.getFirstHalfDeck() && deck != game.getSecondHalfDeck()
        && deck.getId()!= 3 && deck.getId()!= 4 )
        .collect(Collectors.toList());
    }

    public List<Card> getCardsByHalfdeck(HalfDeck halfDeck) {
        return cardRepository.findCardByHalfDeck(halfDeck);
    }

    public List<Card> getAllCards() {
        return (List<Card>) cardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Card> getPromotableCardForPlayerByGame(Game game,String typeOfCard,Boolean endOfTurn){
        Player actualPlayer=game.getCurrentPlayer();
        List<Card> promotableCards=new ArrayList<>();
        if(typeOfCard.toLowerCase().trim().equals("played")){
            promotableCards.addAll(actualPlayer.getPlayed());
            if(!endOfTurn)
                promotableCards.remove(actualPlayer.getLastPlayedCard());
        }
        else if(typeOfCard.toLowerCase().trim().equals("discarded")){
            promotableCards.addAll(actualPlayer.getDiscarded());
        }else{
            promotableCards.addAll(actualPlayer.getDeck());
        }
        
        return promotableCards;
            
    }

    
}
