package org.springframework.samples.petclinic.board.map;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.stereotype.Service;

@Service
public class MapTemplateService {
    @Autowired
    MapTemplateRepository mapTemplateRepository;

    public MapTemplate defaultMap(){
        List<MapTemplate> maps = findAll();
        return maps.get(0);
    }

    public List<MapTemplate> findAll(){
        List<MapTemplate> maps = (List<MapTemplate>) mapTemplateRepository.findAll();
        return maps;
    }

    public MapTemplate findById(Integer id){
        return mapTemplateRepository.findMapTemplateById(id);
    }



    public List<MapTemplate> getAvailableMaps(Game game) {
        return findAll().stream()
        .filter(map-> map != game.getMapTemplate() && game.getPlayers().size() <= map.startingCityCount(game.getSize()))
        .collect(Collectors.toList());
    }
}
