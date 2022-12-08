package org.springframework.samples.petclinic.card;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardServiceRepo {
    
    CardRepository cardRepository;

    @Autowired
    public CardServiceRepo(CardRepository cardRepository){
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

    
}
