package org.springframework.samples.tyrantsOfTheUnderdark.board.map;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city.CityTemplate;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city.CityTemplateService;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.path.PathTemplate;
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

    @GetMapping("/new")
    public String newMap(Map<String,Object> model ){
        model.put("cityTemplate",new CityTemplate());
        model.put("pathTemplate",new PathTemplate());
        MapTemplate map = new MapTemplate();
        mapTemplateService.save(map);
        Game game = mapTemplateService.findGame(map);
        model.put("game",game);
        model.put("map",game.getMapTemplate());
        model.put("editable",true);

        return MAP_DETAILS;
    }

    @GetMapping("{mapId}")
    public String mapDetails(Map<String,Object> model,@PathVariable("mapId") MapTemplate map){
        model.put("cityTemplate",new CityTemplate());
        model.put("pathTemplate",new PathTemplate());
        model.put("map", map);
        model.put("game",mapTemplateService.findGame(map));
        model.put("editable",mapTemplateService.isEditable(map));
        return MAP_DETAILS;
    }
    @PostMapping("{mapId}")
    public String mapDetailsSaveName(Map<String,Object> model,@PathVariable("mapId") MapTemplate mapBeforeChanges,@Valid MapTemplate map,BindingResult result){
        if(result.hasErrors()){
            model.put("errorMessage", "Nombre y descripcion no pueden estar vacios");
        }else{
            mapBeforeChanges.setName(map.getName());
            mapBeforeChanges.setDescription(map.getDescription());
            mapTemplateService.save(mapBeforeChanges);
        }

        model.put("errors",result);
        model.put("cityTemplate",new CityTemplate());
        model.put("pathTemplate",new PathTemplate());
        model.put("map", mapBeforeChanges);
        model.put("game",mapTemplateService.findGame(mapBeforeChanges));
        model.put("editable",mapTemplateService.isEditable(mapBeforeChanges));
        return MAP_DETAILS;
    }
    
    @PostMapping("{mapId}/addCity")
    public String addCity(Map<String,Object> model,@PathVariable("mapId") MapTemplate map,@Valid CityTemplate cityTemplate,BindingResult result){
        if(result.hasErrors()){
            model.put("errors", result); 
            model.put("cityTemplate",cityTemplate);//with errors
            model.put("pathTemplate",new PathTemplate());
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

    @PostMapping("{mapId}/addPath")
    public String addPath(Map<String,Object> model,@PathVariable("mapId") MapTemplate map,@Valid PathTemplate pathTemplate,BindingResult result){
        if(result.hasErrors()){
            model.put("errors", result);
            model.put("cityTemplate",new CityTemplate());
            model.put("pathTemplate",pathTemplate);//with errors
            model.put("editable",mapTemplateService.isEditable(map));
            model.put("map", map);
            model.put("game",mapTemplateService.findGame(map));
            return MAP_DETAILS;
            
        }else{
            Game game = mapTemplateService.findGame(map);
            gameService.delete(game);
            pathTemplate.setMapTemplate(map);
            pathTemplateService.save(pathTemplate);
            map.getPathTemplates().add(pathTemplate);
            System.out.println("path "+map.getPathTemplates().get(map.getPathTemplates().size()-1));
            mapTemplateService.save(map);
            return "redirect:";
        }
    }

    @GetMapping("{mapId}/deletePath/{pathId}")
    public String deletePath(Map<String,Object> model,@PathVariable("mapId") MapTemplate map,@PathVariable("pathId") Integer pathTemplateId ){
        Game game = mapTemplateService.findGame(map);
        gameService.delete(game);
        PathTemplate pathTemplate= pathTemplateService.getById(pathTemplateId);
        map.getPathTemplates().remove(pathTemplate);
        mapTemplateService.save(map);
        pathTemplateService.delete(pathTemplate);
        return "redirect:/maps/{mapId}";
    }

}
