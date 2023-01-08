package org.springframework.samples.tyrantsOfTheUnderdark.card;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.tyrantsOfTheUnderdark.card.Card;
import org.springframework.samples.tyrantsOfTheUnderdark.card.CardService;
import org.springframework.samples.tyrantsOfTheUnderdark.card.HalfDeck;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class CardServiceTest {

    @Autowired
    protected CardService cardService;

    @Test
    public void shouldFindCardById() {
        Card card =this.cardService.getCardById(7);
        assertThat(card.getName()).startsWith("Adalid");
        assertThat(card.getCost()).isEqualTo(2);
        assertThat(card.getStory()).startsWith("<<");
        assertThat(card.getRulesText()).startsWith("Elige");
        assertThat(card.getDeckVP()).isEqualTo(1);
        assertThat(card.getInnerCirclePV()).isEqualTo(2);
        assertThat(card.getRarity()).isEqualTo(4);
        assertThat(card.getHalfDeck()).isNotNull();
    }

    @Test
    public void shouldFindHalfDeckByName() {
        List<HalfDeck> halfDeck = this.cardService.getHalfDeckByCard("Drow");
        assertThat(halfDeck.size()).isEqualTo(1);        
    }

    @Test
    public void shouldntFindAnyHalfDeckByAInexistentName(){
        List<HalfDeck> halfDeck = this.cardService.getHalfDeckByCard("hola");
        assertThat(halfDeck.size()).isEqualTo(0);  
    }

    @Test
    public void shouldFindCardByNameAndByHalfDeck() {
        List<Card> cards = this.cardService.getCardsFilteredBy("Adalid", "Drow");
        assertThat(cards.size()).isEqualTo(1);
    }

    @Test
    public void shouldFindCardByHalfDeck(){
        Integer DROW_ID=1;
        List<Card> cards=this.cardService.getCardsByHalfdeck(this.cardService.getHalfDeckById(DROW_ID));
        assertThat(cards.size()).isEqualTo(20);
    }

    @Test
    public void shouldntFindCardByInexistentNameAndByInexistentHalfDeck() {
        List<Card> cards = this.cardService.getCardsFilteredBy("Caballero Artorias", "ntoTo");
        assertThat(cards.size()).isEqualTo(0);
    }
}
