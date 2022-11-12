package org.springframework.samples.petclinic.board.position;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.board.sector.city.CityService;
import org.springframework.samples.petclinic.board.sector.path.PathService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PositionController {

    private String POSITIONS_LISTING_VIEW="positions/positionsListing";

    private PositionService positionService;
    private CityService cityService;
    private PathService pathService;
    
    @ModelAttribute(name = "zones")
    public String zones(){
        return "1,2";
    }

    @Autowired
    public PositionController(PositionService posServ,CityService city, PathService pService){
        this.positionService=posServ;
        this.cityService=city;
        this.pathService= pService;
    }

    @GetMapping("/positions")
    public ModelAndView showPositions(){
        ModelAndView result=new ModelAndView(POSITIONS_LISTING_VIEW);
        result.addObject("positions", positionService.getPositions());
        result.addObject("cities", cityService.getCities());
        result.addObject("paths", pathService.getPaths());
        result.addObject("freePositions", positionService.getFreePositions());
        return result;
    }
    @GetMapping(value = "/positions/{id}/occupy")
    public String occupy(@PathVariable("id") Integer id) throws DataAccessException {
        Position p= this.positionService.findPositionById(id);
        if(p.getOccupied()) p.setOccupied(false);
        else p.setOccupied(true);
        this.positionService.save(p);
        return "redirect:/positions";
    }
        
    @GetMapping(value = "/positions/{id}/adjacents")
    public String adjacents(@PathVariable("id") Integer id) throws DataAccessException {
        Position position= this.positionService.findPositionById(id);
        positionService.calculateAdjacents(position);
        this.positionService.save(position);
        return "redirect:/positions";
        
    }
    //TODO choose the zones in populate method
    @GetMapping(value = "/positions/populate")
    public String populate(){
        System.out.println("llamada a /populate");
        List<Integer> zoneList=List.of(1,2,3);
        
        this.positionService.populatePositions( cityService.getCities(),pathService.getPaths(), zoneList);
        return "redirect:/positions";
    }
    
}
