package org.springframework.samples.petclinic.play;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.position.CustomListingPositionService;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("games/play")
public class PlayController {

    private static final String ROUND_ZERO = null;

    private static final String SCORE_BOARD = null;

    @Autowired
    private CustomListingPositionService customListingPositionService;

    @Autowired
    private PlayService playService;
    @Autowired
    private GameService gameService;



    @GetMapping("{gameId}")
    public String showActualRound(@PathVariable Integer gameId){
        Game game=gameService.getGameById(gameId);
        System.out.println(game);
        String result=null;
        if(!game.isLoaded()) {
            playService.loadGame(game);
            game=gameService.getGameById(gameId);
        }
        if(game.getRound()==0){

            result="redirect:/games/play/"+gameId+"/round0?player="+game.getCurrentPlayer().getId();
            result="welcome";
        }
        else if(game.getFinished())
            result="redirect:/games/play/"+gameId+"/scoreboard";
        else
            result="redirect:/games/play/"+gameId+"/play?player="+game.getCurrentPlayer().getId();
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
        return result;
    }


    
}
