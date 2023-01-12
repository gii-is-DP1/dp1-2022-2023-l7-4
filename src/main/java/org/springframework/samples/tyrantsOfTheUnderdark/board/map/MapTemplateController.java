package org.springframework.samples.tyrantsOfTheUnderdark.board.map;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("maps")
public class MapTemplateController {
    private final String MAP_DETAILS = "/maps/mapDetails";
    private final String MAP_LISTING = "/maps/mapListing";

    @Autowired
    MapTemplateService mapTemplateService;

    @GetMapping
    public String allMaps(Map<String,Object> model){
        List<MapTemplate> maps = mapTemplateService.findAll();
        model.put("maps", maps);
        return MAP_LISTING;
    }

    @GetMapping("{mapId}")
    public String mapDetails(Map<String,Object> model,@PathVariable("mapId") MapTemplate map){

        model.put("map", map);
        model.put("game",mapTemplateService.findGame(map));
        model.put("editable",mapTemplateService.isEditable(map));
        return MAP_DETAILS;
    }





}
