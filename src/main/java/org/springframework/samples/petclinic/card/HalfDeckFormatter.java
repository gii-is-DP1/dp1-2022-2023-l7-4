
package org.springframework.samples.petclinic.card;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class HalfDeckFormatter implements Formatter<HalfDeck> {

	private final CardServiceRepo cardService;

	@Autowired
	public HalfDeckFormatter(CardServiceRepo cardService) {
		this.cardService = cardService;
	}

	@Override
	public String print(HalfDeck halfDeck, Locale locale) {
		return halfDeck.getId().toString();
	}

	@Override
	public HalfDeck parse(String text, Locale locale) throws ParseException {
		try {
			HalfDeck selected = this.cardService.getHalfDeckById(Integer.parseInt(text));
			return selected;

		} catch (Exception e) {
			throw new ParseException("no se pudo pasar " + text, 0);
		}

	}

}
