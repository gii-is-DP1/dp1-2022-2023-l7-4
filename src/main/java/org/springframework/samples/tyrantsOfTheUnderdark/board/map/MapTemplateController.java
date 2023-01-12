package org.springframework.samples.tyrantsOfTheUnderdark.board.map;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city.CityTemplate;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city.CityTemplateService;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.path.PathTemplateService;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
import org.springframework.samples.tyrantsOfTheUnderdark.game.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("maps")
public class MapTemplateController {
    private final String MAP_DETAILS = "/maps/mapDetails";
    private final String MAP_LISTING = "/maps/mapListing";

    @Autowired
    MapTemplateService mapTemplateService;
    @Autowired
    GameService gameService;
    @Autowired
    CityTemplateService cityTemplateService;
    @Autowired 
    PathTemplateService pathTemplateService;

    @GetMapping
    public String allMaps(Map<String,Object> model){
        List<MapTemplate> maps = mapTemplateService.findAll();
        model.put("maps", maps);
        return MAP_LISTING;
    }

    @GetMapping("{mapId}")
    public String mapDetails(Map<String,Object> model,@PathVariable("mapId") MapTemplate map){
        CityTemplate cityTemplate = new CityTemplate();
        model.put("cityTemplate",cityTemplate);
        model.put("map", map);
        model.put("game",mapTemplateService.findGame(map));
        model.put("editable",mapTemplateService.isEditable(map));
        return MAP_DETAILS;
    }
    
    @PostMapping("{mapId}/addCity")
    public String addCity(Map<String,Object> model,@PathVariable("mapId") MapTemplate map,@Valid CityTemplate cityTemplate,BindingResult result){
        if(result.hasErrors()){
            model.put("errors", result); 
            model.put("cityTemplate",cityTemplate);//with errors
            model.put("editable",mapTemplateService.isEditable(map));
            model.put("map", map);
            model.put("game",mapTemplateService.findGame(map));
            return MAP_DETAILS;
            
        }else{
            cityTemplate.setMapTemplate(map);
            map.getCityTemplates().add(cityTemplate);
            mapTemplateService.save(map);
            return "redirect:";
        }
    }

    @GetMapping("{mapId}/deleteCity/{cityId}")
    public String deleteCity(Map<String,Object> model,@PathVariable("mapId") MapTemplate map,@PathVariable("cityId") Integer cityTemplateId){
        Game game = mapTemplateService.findGame(map);
        gameService.delete(game);

        CityTemplate cityTemplate = cityTemplateService.getById(cityTemplateId);
        map.getCityTemplates().remove(cityTemplate);
        mapTemplateService.save(map);
        pathTemplateService.deleteContaining(cityTemplate);
        cityTemplateService.delete(cityTemplate);
        return "redirect:/maps/{mapId}";
    }



}
