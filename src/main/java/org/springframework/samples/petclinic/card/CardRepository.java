package org.springframework.samples.petclinic.card;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


@Repository
public interface CardRepository extends CrudRepository<Card, Integer> {
    //Card
    @Query("SELECT c FROM Card c WHERE c.name LIKE concat('%',:name,'%') AND c.halfDeck.name LIKE concat('%',:deck,'%')")
    List<Card> findCardsByNameAndByHalfDeck(String name, String deck);

    @Query("SELECT c FROM Card c WHERE c.id = ?1")
    Card findCardById(Integer id);

    @Query("SELECT c FROM Card c WHERE c.halfDeck.name LIKE concat('%',?1,'%')")
    List<Card> findAllCardsByHalfDeck(String name);

    //Aspect
    @Query("SELECT a FROM Aspect a WHERE a.name LIKE concat('%',?1,'%')")
    List<Aspect> findAspectsByName(String name);

    //HalfDeck
    @Query("SELECT h FROM HalfDeck h WHERE h.name LIKE concat('%',?1,'%')")
    List<HalfDeck> findHalfDecksByName(String name);

    //Modulo de creación de cartas: Card save(Card p);
}
