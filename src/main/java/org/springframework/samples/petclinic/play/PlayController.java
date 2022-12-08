package org.springframework.samples.petclinic.play;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.position.CustomListingPositionService;
import org.springframework.samples.petclinic.board.position.PlayerUsePositionService;
import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.board.position.PositionServiceRepo;
import org.springframework.samples.petclinic.board.position.auxiliarEntitys.Idposition;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("games/play")
public class PlayController {

    private static final String ROUND_ZERO = "playing/roundZero";

    private static final String SCORE_BOARD = null;

    @Autowired
    private CustomListingPositionService customListingPositionService;

    @Autowired
    private PlayService playService;
    @Autowired
    private GameService gameService;



    @Autowired
    private PlayerUsePositionService playerUsePositionService;

    @Autowired
    private PositionServiceRepo positionServiceRepo;



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
            result="redirect:/games/play/"+gameId+"/round0";
        }
        else if(game.getFinished())
            result="redirect:/games/play/"+gameId+"/scoreboard";
        else
            result="redirect:/games/play/"+gameId+"/play";
        return result;
    }

    @GetMapping("{gameId}/round0")
    public ModelAndView showInitialRound(@PathVariable Integer gameId){
        ModelAndView result=new ModelAndView(ROUND_ZERO);
        List<Position> initialPositions=customListingPositionService.getInitialPositions();
        Game game=gameService.getGameById(gameId);
        System.out.println(gameId);
        System.out.println(game.getGameMap());
        result.addObject("initialPositions", initialPositions);
        result.addObject("i", initialPositions.size());
        result.addObject("map", game.getGameMap());
        return result;
    }

    @PostMapping("{gameId}/round0")
    public ModelAndView processChoosedPositionInRoundZero(@Valid Idposition idpos,BindingResult br
    ,@PathVariable Integer gameId){
        Game game=gameService.getGameById(gameId);
        Player player=game.getCurrentPlayer();
        ModelAndView result=null;
        if(br.hasErrors()){
            return new ModelAndView(ROUND_ZERO,br.getModel());
        }
        try{
            Position position= positionServiceRepo.findPositionById(idpos.getId());
            this.playerUsePositionService.occupyTroopPosition(position, player,false);
            game.setNextPlayer();
            result=new ModelAndView("redirect:/games/play/"+gameId);
        }catch(Exception e){
            br.rejectValue("position","occupied","already occupy");
            result=new ModelAndView(ROUND_ZERO,br.getModel());
        }
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
