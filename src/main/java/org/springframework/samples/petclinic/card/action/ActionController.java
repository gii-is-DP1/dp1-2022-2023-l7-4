package org.springframework.samples.petclinic.card.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.map.position.PositionController;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/actions")
public class ActionController {
    
    private final String ACTION_LISTING="actions/allActionsView";
    private final String ACTION_LISTING_OF_CARD="actions/actionsOfCardView";

    private PlayerService playerService;
    private ActionService actionService;


    @Autowired
    public ActionController(PlayerService playerService,ActionService actionService){
        this.actionService=actionService;
        this.playerService=playerService;
    }

    @GetMapping("")
    public ModelAndView showAllActions(){
        ModelAndView result=new ModelAndView(ACTION_LISTING);
        result.addObject("actions", actionService.getAllActions());
        return result;
    }

    //esto cuando llegue el momento, habría que cambiarlo a {playerId}/{actionId}
    @GetMapping("{actionId}")
    public String initBasicAction(@PathVariable Integer actionId){
        //comprobar que es básica
        Action basicAction=actionService.getActionById(actionId);
        return null;
        



    }

}
