package org.springframework.samples.petclinic.play;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.position.PlayerUsePositionService;
import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.board.position.PositionServiceRepo;
import org.springframework.samples.petclinic.board.position.auxiliarEntitys.Idposition;
import org.springframework.samples.petclinic.board.sector.city.CityService;
import org.springframework.samples.petclinic.board.sector.path.PathService;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.initializer.InitializePositionService;
import org.springframework.samples.petclinic.initializer.IntializeGame;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("play")
public class PlayController {
    
    private static final String ROUND_ZERO = "playing/roundZero";

    private static final String ROUND_N = "playing/roundN";
    
    private static final String SCORE_BOARD = null;
    
    @Autowired
    private PositionInGameService positionInGameService;
    
    @Autowired
    private GameService gameService;
    
    @Autowired
    IntializeGame gameInitializer;
    
    @Autowired
    private PlayerUsePositionService playerUsePositionService;
    @Autowired
    InitializePositionService positionInitialiter;
    @Autowired
    private PositionServiceRepo positionServiceRepo;

    @Autowired
    private CityService cityService;

    @Autowired
    private PathService pathService;

    



    @GetMapping("{gameId}")
    public String showActualRound(@PathVariable Integer gameId){
        Game game=gameService.getGameById(gameId);
        String result=null;
        game = gameInitializer.loadGame(game);
        

        if(game.getRound()==0){
            result="redirect:"+gameId+"/round/0";
        }
        else if(game.getFinished())
            result="redirect:"+gameId+"/scoreboard";
        else
            result="redirect:"+gameId+"/round/"+game.getRound();
        return result;
    }


    @GetMapping("{gameId}/round/0")
    public ModelAndView showInitialRound(@PathVariable Integer gameId){
        ModelAndView result=new ModelAndView(ROUND_ZERO);
        Game game=gameService.getGameById(gameId);
        Player player = game.getCurrentPlayer();

        List<Position> initialPositions=positionInGameService.getInitialPositions(game);
        result.addObject("player", game.getCurrentPlayer());
        result.addObject("round", game.getRound());
        result.addObject("cities", game.getCities());
        result.addObject("paths", game.getPaths());
        result.addObject("positions", initialPositions);
        result.addObject("vp", game.getPlayerScore(player));
        result.addObject("totalinnerCirclevp", game.getInnerCircleVP(player));

        return result;
    }

    @PostMapping("{gameId}/round/0")
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
            gameService.nextPlayerAndSave(game);
            result=new ModelAndView("redirect:/play/"+gameId);
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

    @GetMapping("{gameId}/round/{round}")
    public ModelAndView showInitialRound(@PathVariable Integer gameId, @PathVariable Integer round){
        ModelAndView result=new ModelAndView(ROUND_N);
        Game game=gameService.getGameById(gameId);
        Player player = game.getCurrentPlayer();

        List<Position> positions=positionServiceRepo.getAllPositionsFromGame(game);
        result.addObject("player", game.getCurrentPlayer());
        result.addObject("round", game.getRound());
        result.addObject("gameId", gameId);
        result.addObject("cities", game.getCities());
        result.addObject("paths", game.getPaths());
        result.addObject("positions", positions);
        result.addObject("vp", game.getPlayerScore(player));
        result.addObject("totalinnerCirclevp", game.getInnerCircleVP(player));
        return result;
    }

    @PostMapping("{gameId}/round/{round}")
    public ModelAndView processNextTurn(@PathVariable Integer gameId,@PathVariable Integer round
    ,BindingResult br){
        Game game=gameService.getGameById(gameId);
        ModelAndView result=null;
        if(br.hasErrors()){
            return new ModelAndView(ROUND_N,br.getModel());
        }
        try{
            gameService.nextPlayerAndSave(game);
            result=new ModelAndView("redirect:/play/"+gameId);
        }catch(Exception e){
            br.rejectValue("game","error","something happen,try again");
            result=new ModelAndView(ROUND_N,br.getModel());
        }
        return result;
    }
    
}
