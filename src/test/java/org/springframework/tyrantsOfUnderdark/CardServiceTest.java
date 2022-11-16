package org.springframework.tyrantsOfUnderdark;

import java.util.Collection;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.CardService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class CardServiceTest {

        @Autowired
    protected CardService cardService;

    @Test
    void shouldFindCardByName() {
        Collection<Card> cards = this.cardService.getCardByName("Adalid");
		assertThat(cards.size()).isEqualTo(4);

		cards = this.cardService.getCardByName("Adalidd");
		assertThat(cards.isEmpty()).isTrue();
    }
}
