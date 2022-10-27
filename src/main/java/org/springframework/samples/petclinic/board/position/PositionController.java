package org.springframework.samples.petclinic.board.position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    
}
