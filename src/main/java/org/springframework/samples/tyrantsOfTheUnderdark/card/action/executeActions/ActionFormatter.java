
package org.springframework.samples.tyrantsOfTheUnderdark.card.action.executeActions;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.tyrantsOfTheUnderdark.card.Card;
import org.springframework.samples.tyrantsOfTheUnderdark.card.CardService;
import org.springframework.samples.tyrantsOfTheUnderdark.card.action.Action;
import org.springframework.stereotype.Component;

@Component
public class ActionFormatter implements Formatter<Action> {

	private final CardService cardService;

	@Autowired
	public ActionFormatter(CardService cardService) {
		this.cardService = cardService;
	}

	@Override
	public String print(Action action, Locale locale) {
		return action.toString();
	}

	@Override
	public Action parse(String text, Locale locale) throws ParseException {
		try {
			Card card = this.cardService.getCardById(Integer.parseInt(text));
			return card.getAction();

		} catch (Exception e) {
			throw new ParseException("no se pudo pasar " + text, 0);
		}

	}

}
