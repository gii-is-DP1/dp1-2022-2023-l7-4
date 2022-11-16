package org.springframework.samples.petclinic.card;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class CardServiceTest {

    @Autowired
    protected CardService cardService;

    @Test
    public void shouldFindCardById() {
        Card card =this.cardService.getCardById(3);
        assertThat(card.getName()).startsWith("Adalid");
        assertThat(card.getCost()).isEqualTo(2);
        assertThat(card.getStory()).startsWith("<<Codícia, avarícia y poder: tres idiomas que entiendo a la perfección>>");
        assertThat(card.getRulesText()).startsWith("Elige una Opcion: + 2 Influencia | Al final del turno, asciende una carta jugada durante este turno");
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
    public void shouldFindCardByNameAndByHalfDeck() {
        List<Card> cards = this.cardService.getCardsByNameAndByHalfDeck("Adalid", "Drow");
        assertThat(cards.size()).isEqualTo(1);
    }
}
