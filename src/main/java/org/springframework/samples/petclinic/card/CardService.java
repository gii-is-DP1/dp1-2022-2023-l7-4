package org.springframework.samples.petclinic.card;

import java.util.Collection;
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

    //Aspect
    @Transactional(readOnly = true)
    public List<Aspect> getAspectFromCard(String name) {
        return cardRepository.findAspectsByName(name);
    }

    //HalfDeck
    @Transactional(readOnly = true)
    public List<HalfDeck> getAllHalfDecks() {
        return cardRepository.findAllDecks();
    }

    @Transactional(readOnly = true)
    public List<HalfDeck> getHalfDeckFromCard(String name) {
        return cardRepository.findHalfDecksByName(name);
    }

    
}
