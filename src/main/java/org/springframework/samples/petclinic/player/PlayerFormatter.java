
package org.springframework.samples.petclinic.player;

import java.text.ParseException;
import java.util.Locale;


import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.stereotype.Component;

@Component
public class PlayerFormatter implements Formatter<Player> {

	private final UserService userService;

	
	public PlayerFormatter(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String print(Player player, Locale locale) {
		return player.getName();
	}

	@Override
	public Player parse(String text, Locale locale) throws ParseException {
		try {

				User user = userService.getUserByUsername(text);
				return Player.ofUser(user);
			

		} catch (Exception e) {
			throw new ParseException("no se pudo pasar " + text, 0);
		}

	}

}
