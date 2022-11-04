package org.springframework.samples.petclinic.board.position;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/positions")
public class PositionController {

    private String POSITIONS_LISTING_VIEW="positions/positionsListing";

    private PositionService positionService;

    @Autowired
    public PositionController(PositionService posServ){
        this.positionService=posServ;
    }

    @GetMapping("")
    public ModelAndView showPositions(){
        ModelAndView result=new ModelAndView(POSITIONS_LISTING_VIEW);
        result.addObject("positions", positionService.getPositions());
        result.addObject("freePositions", positionService.getFreePositions());
        return result;
    }
    @GetMapping(value = "/{id}/occupy")
    public String occupy(@PathVariable("id") Integer id) throws DataAccessException {
        Position p= this.positionService.findPositionById(id);
        if(p.getOccupied()) p.setOccupied(false);
        else p.setOccupied(true);
        this.positionService.saveAndFlush(p);
        return "redirect:/positions";

    }
    
}
