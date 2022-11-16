package org.springframework.samples.petclinic.sandbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.position.PositionService;
import org.springframework.samples.petclinic.board.sector.city.CityService;
import org.springframework.samples.petclinic.board.sector.path.PathService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SandboxController {
    
    private String SANDBOX_LISTING_VIEW="sandbox/sandbox";

    private PositionService positionService;
    private CityService cityService;
    private PathService pathService;
    
    @ModelAttribute(name = "zones")
    public String zones(){
        return "1,2";
    }

    @Autowired
    public SandboxController(PositionService posServ,CityService city, PathService pService){
        this.positionService=posServ;
        this.cityService=city;
        this.pathService= pService;
    }

    @GetMapping("/sandbox")
    public ModelAndView showPositions(){
        ModelAndView result=new ModelAndView(SANDBOX_LISTING_VIEW);
        result.addObject("positions", positionService.getPositions());
        result.addObject("cities", cityService.getCities());
        result.addObject("paths", pathService.getPaths());
        result.addObject("freePositions", positionService.getFreePositions());
        return result;
    }
    // @GetMapping(value = "/sandbox/{id}/occupy")
    // public String occupy(@PathVariable("id") Integer id) throws DataAccessException {
    //     Position p= this.positionService.findPositionById(id);
    //     if(p.getOccupied()) p.setOccupied(false);
    //     else p.setOccupied(true);
    //     this.positionService.save(p);
    //     return "redirect:/sandbox";
    // }
        
    // @GetMapping(value = "/positions/{id}/adjacents")
    // public String adjacents(@PathVariable("id") Integer id) throws DataAccessException {
    //     Position position= this.positionService.findPositionById(id);
    //     positionService.calculateAdjacents(position);
    //     this.positionService.save(position);
    //     return "redirect:/positions";
        
    // }
    //TODO choose the zones in populate method
    @GetMapping(value = "/sandbox/populate")
    public String populate(){

        // List<Integer> zoneList=List.of(1,2,3);
        
        // this.positionService.populatePositions( cityService.getCities(),pathService.getPaths(), zoneList);
        return "redirect:/sandbox";
    }

    @GetMapping(value = "/sandbox/2")
    public String dos(){

        return "redirect:/welcome";
    }
}
