package org.springframework.samples.petclinic.playing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.map.position.CustomListingPositionService;
import org.springframework.samples.petclinic.map.position.PlayerUsePositionService;
import org.springframework.samples.petclinic.map.position.PositionServiceRepo;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/game")
public class PlayController {

    @Autowired
    private CustomListingPositionService customListingPositionService;

    @Autowired
    private PlayerUsePositionService playerUsePositionService;

    @Autowired
    private PositionServiceRepo positionServiceRepo;

    @Autowired
    private GameService gameService;



    @GetMapping("{gameId}")
    public String showActualRound(@PathVariable Integer gameId){
        Game game=gameService.getGameById(gameId);
        String result=null;
        if(game.getRound()==0)
            result="redirect/:game/"+gameId+"/round0?player="+game.getCurrentPlayer().getId();
        else if(game.getFinished())
            result="redirect/:game/"+gameId+"/scoreboard";
        else
            result="redirect/:game/"+gameId+"/play?player="+game.getCurrentPlayer().getId();
        return result;
    }

    @GetMapping("{gameId}/round0")
    public ModelAndView showInitialRound(@PathVariable Integer gameId, @RequestParam("player") Integer playerId){
        Game game=gameService.getGameById(gameId);
        Player player=game.getCurrentPlayer();
        ModelAndView result=new ModelAndView(ROUND_ZERO);
        result.addObject("initialPositions", customListingPositionService.getInitialPositions());
        return result;
    }

    @GetMapping("{gameId}/scoreboard")
    public ModelAndView showScoreBoard(@PathVariable Integer gameId){
        Game game=gameService.getGameById(gameId);
        ModelAndView result= new ModelAndView(SCORE_BOARD);
        result.addObject("gamePlayers", game.getPlayers());
        
    }


    
}
