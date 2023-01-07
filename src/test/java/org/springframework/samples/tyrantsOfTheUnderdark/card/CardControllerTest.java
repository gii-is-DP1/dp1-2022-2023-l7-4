package org.springframework.samples.tyrantsOfTheUnderdark.card;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.tyrantsOfTheUnderdark.card.Card;
import org.springframework.samples.tyrantsOfTheUnderdark.card.CardController;
import org.springframework.samples.tyrantsOfTheUnderdark.card.CardService;
import org.springframework.samples.tyrantsOfTheUnderdark.configuration.SecurityConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebMvcTest(value = CardController.class, excludeAutoConfiguration = SecurityConfiguration.class)
public class CardControllerTest {
    
    private static final int TEST_CARD_ID = 7;

    @Autowired
    private CardController cardController;

    @MockBean
    private CardService cardServiceRepo;

    @Autowired
    private MockMvc mockMvc;

    private Card adalid;

    @BeforeEach
	void setup() {
		adalid = new Card();
        adalid.setId(TEST_CARD_ID);
        adalid.setName("Adalid");
        adalid.setCost(2);
        adalid.setStory("<<Codícia, avarícia y poder: tres idiomas que entiendo a la perfección>>");
        adalid.setRulesText("Elige una Opcion: + 2 Influencia | Al final del turno, asciende una carta jugada durante este turno");
        adalid.setDeckVP(1);
        adalid.setInnerCirclePV(2);
        adalid.setRarity(4);
        given(this.cardServiceRepo.getCardById(TEST_CARD_ID)).willReturn(adalid);
	}

    @WithMockUser(value = "spring")
	@Test
	public void testShowCard() throws Exception {
        mockMvc.perform(get("/cards/{cardId}", TEST_CARD_ID)).andExpect(status().isOk());
				/* .andExpect(model().attribute("card", hasProperty("name", is("Adalid"))))
				.andExpect(model().attribute("card", hasProperty("cost", is(2))))
				.andExpect(model().attribute("card", hasProperty("story", is("<<Codícia, avarícia y poder: tres idiomas que entiendo a la perfección>>"))))
				.andExpect(model().attribute("card", hasProperty("rulesText", is("Elige una Opcion: + 2 Influencia | Al final del turno, asciende una carta jugada durante este turno"))))
				.andExpect(model().attribute("card", hasProperty("deckVP", is(1))))
                .andExpect(model().attribute("card", hasProperty("innerCirclePV", is(2))))
				.andExpect(model().attribute("card", hasProperty("rarity", is(4))))
                .andExpect(view().name("cards/cardDetails")); */
	}

}
