
package org.springframework.samples.tyrantsOfTheUnderdark.game;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class GameFormatter implements Formatter<Game> {

	private final GameService gameService;

	@Autowired
	public GameFormatter(GameService gameService) {
		this.gameService = gameService;
	}

	@Override
	public String print(Game game, Locale locale) {
		return game.getId().toString();
	}

	@Override
	public Game parse(String text, Locale locale) throws ParseException {
		try {
			Game selected = this.gameService.getGameById(Integer.parseInt(text));
			return selected;

		} catch (Exception e) {
			throw new ParseException("no se pudo pasar " + text, 0);
		}

	}

}
