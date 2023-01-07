
package org.springframework.samples.tyrantsOfTheUnderdark.card;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class CardFormatter implements Formatter<Card> {

	private final CardService cardService;

	@Autowired
	public CardFormatter(CardService cardService) {
		this.cardService = cardService;
	}

	@Override
	public String print(Card card, Locale locale) {
		return card.getId().toString();
	}

	@Override
	public Card parse(String text, Locale locale) throws ParseException {
		try {
			Card selected = this.cardService.getCardById(Integer.parseInt(text));
			return selected;

		} catch (Exception e) {
			throw new ParseException("no se pudo pasar " + text, 0);
		}

	}

}
