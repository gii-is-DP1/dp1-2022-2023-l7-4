
package org.springframework.samples.tyrantsOfTheUnderdark.board.map;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class MapTemplateFormatter implements Formatter<MapTemplate> {

	private final MapTemplateService mapTemplateService;

	@Autowired
	public MapTemplateFormatter(MapTemplateService mapTemplateService) {
		this.mapTemplateService = mapTemplateService;
	}

	@Override
	public String print(MapTemplate MapTemplate, Locale locale) {
		return MapTemplate.getId().toString();
	}

	@Override
	public MapTemplate parse(String text, Locale locale) throws ParseException {
		try {
			MapTemplate selected = this.mapTemplateService.findById(Integer.parseInt(text));
			return selected;

		} catch (Exception e) {
			throw new ParseException("no se pudo pasar " + text, 0);
		}

	}

}
