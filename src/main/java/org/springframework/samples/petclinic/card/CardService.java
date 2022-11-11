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
    public List<Card> getAllCards(){
        return this.cardRepository.findAll();    
    }

    @Transactional(readOnly = true)
    public List<Card> getCardsByName(String name) {
        return cardRepository.findCardsByName(name);
    }

    @Transactional(readOnly = true)
    public Collection<Card> getCardById(Integer id) {
        return cardRepository.findCardById(id);
    }

    @Transactional(readOnly = true)
    public List<Card> getCarsdByHalfDeck(String name) {
        return cardRepository.findAllCardsByHalfDeck(name);
    }

    //Aspect
    @Transactional(readOnly = true)
    public List<Aspect> getAllAspects() {
        return cardRepository.findAllAspects();
    }

    @Transactional(readOnly = true)
    public Aspect getAspectFromCard(String name) {
        return cardRepository.findAspectFromCard(name);
    }

    //HaldDeck
    @Transactional(readOnly = true)
    public List<HalfDeck> getAllHalfDecks() {
        return cardRepository.findAllHalfDecks();
    }

    @Transactional(readOnly = true)
    public HalfDeck getHalfDeckFromCard(String name) {
        return cardRepository.findHalfDeckFromCard(name);
    }
}
