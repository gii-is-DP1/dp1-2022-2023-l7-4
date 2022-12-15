package org.springframework.samples.petclinic.card;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CardRepository extends CrudRepository<Card, Integer> {
    //Card
    @Query("SELECT c FROM Card c WHERE LOWER(c.name) LIKE LOWER(concat('%',:name,'%')) AND c.halfDeck.name LIKE concat('%',:deck,'%')")
    List<Card> findCardsByNameAndByHalfDeck(String name, String deck);

    @Query("SELECT c FROM Card c WHERE LOWER(c.name) LIKE LOWER(concat('%',:name,'%')) AND c.halfDeck.name LIKE concat('%',:deck,'%')")
    List<Card> findCardsByNameAndByHalfDeckPageable(String name, String deck,Pageable pageable);

    @Query("SELECT c FROM Card c WHERE c.id = ?1")
    Card findCardById(Integer id);

    //HalfDeck
    @Query("SELECT h FROM HalfDeck h")
    List<HalfDeck> findAllDecks();

    @Query("SELECT h FROM HalfDeck h WHERE LOWER(h.name) LIKE LOWER(concat('%',?1,'%'))")
    List<HalfDeck> findHalfDecksByName(String name);

     @Query("SELECT h FROM HalfDeck h WHERE h.id = ?1")
    HalfDeck findHalfDeckById(Integer id);

    //Modulo de creación de cartas: Card save(Card p);
}
