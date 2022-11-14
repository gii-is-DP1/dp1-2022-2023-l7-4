package org.springframework.samples.petclinic.card;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


@Repository
public interface CardRepository extends CrudRepository<Card, Integer> {
    //Card
    List<Card> findAll();

    @Query("SELECT c FROM Card c WHERE c.name LIKE concat('%',:name,'%') AND c.halfDeck.name LIKE concat('%',:deck,'%')")
    List<Card> findCardsByNameAndByHalfDeck(String name, String deck);

    @Query("SELECT c FROM Card c WHERE c.id = ?1")
    Card findCardById(Integer id);

    @Query("SELECT c FROM Card c WHERE c.halfDeck.name LIKE concat('%',?1,'%')")
    List<Card> findAllCardsByHalfDeck(String name);

    //Aspect
    @Query("SELECT a FROM Aspect a")
	List <Aspect> findAllAspects();

    @Query("SELECT c FROM Aspect a JOIN Card c WHERE c.name = ?1")
    Aspect findAspectFromCard(String name);

    //HalfDeck
    @Query("SELECT h FROM HalfDeck h")
    List<HalfDeck> findAllHalfDecks();

    @Query("SELECT c FROM HalfDeck h JOIN Card c WHERE c.name = ?1")
    HalfDeck findHalfDeckFromCard(String name);

    //Modulo de creación de cartas: Card save(Card p);
}
