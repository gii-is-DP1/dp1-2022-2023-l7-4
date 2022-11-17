package org.springframework.samples.petclinic.card;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Card> getCardsByNameAndByHalfDeck(String name, String deck) {
        return cardRepository.findCardsByNameAndByHalfDeck(name, deck);
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

    
}
