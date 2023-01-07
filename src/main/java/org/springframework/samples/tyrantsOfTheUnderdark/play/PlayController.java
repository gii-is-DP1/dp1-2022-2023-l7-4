package org.springframework.samples.tyrantsOfTheUnderdark.play;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.CustomListingPositionService;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.PlayerUsePositionService;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.Position;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.PositionServiceRepo;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.PricedPositionService;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.auxiliarEntitys.Idposition;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city.CityService;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.path.PathService;
import org.springframework.samples.tyrantsOfTheUnderdark.card.Card;
import org.springframework.samples.tyrantsOfTheUnderdark.cardsMovement.MarketPlayerMoveCardsService;
import org.springframework.samples.tyrantsOfTheUnderdark.game.EndTurnService;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
import org.springframework.samples.tyrantsOfTheUnderdark.game.GameService;
import org.springframework.samples.tyrantsOfTheUnderdark.initializer.InitializePositionService;
import org.springframework.samples.tyrantsOfTheUnderdark.initializer.IntializeGame;
import org.springframework.samples.tyrantsOfTheUnderdark.player.Player;
import org.springframework.samples.tyrantsOfTheUnderdark.player.PlayerService;
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
    
    private static final String SCORE_BOARD = "playing/scoreBoard";
    
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
    private EndTurnService endTurnService;

    @Autowired
    private MarketPlayerMoveCardsService playerMoveCardsService;

    @Autowired
    private PlayerService playerService;


    public void putPlayerDataInModel(Game game, Player actualPlayer,ModelAndView result ){
        result.addObject("player", game.getCurrentPlayer());
        result.addObject("round", game.getRound());
        result.addObject("turn", game.getTurnPlayer());
        result.addObject("cities", game.getCities());
        result.addObject("paths", game.getPaths());
        result.addObject("totalVp", game.getPlayerScore(actualPlayer).getTotalVp());
        result.addObject("totalinnerCirclevp", game.getInnerCircleVP(actualPlayer));
    }



    @GetMapping("{gameId}")
    public String showActualRound(@PathVariable Integer gameId){
        Game game=gameService.getGameById(gameId);
        String result="redirect:{gameId}";// redirect aqui mismo (recursividad)
        game = gameInitializer.loadGame(game);
        
        if(game.getRound()==-1){
            if(gameService.enoughUnaligned(game)){
                game.setRound(0);
                gameService.save(game);
            }else{
                if(game.getAutomaticWhiteTroops()){
                    //TODO llamas a automatic y no haces nada (recursivo)
                    // fichas blancas = posiciones de tropa * 0.28  !!!!!!!!!!
                    this.gameInitializer.loadAutomaticWhitePositions(game);

                }else{
                    result="redirect:{gameId}/chooseWhitePositions";
                }
            }
        }else if(game.getRound()==0){
            result="redirect:"+gameId+"/round/0";
        }
        else if(game.isFinished())
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
        putPlayerDataInModel(game, player, result);
        result.addObject("positions", initialPositions);
        result.addObject("totalVp", game.getPlayerScore(player).getTotalVp());
        result.addObject("vp", game.getPlayerScore(player));
        result.addObject("totalinnerCirclevp", game.getInnerCircleVP(player));
        return result;
    }

    @PostMapping("{gameId}/round/0")
    public ModelAndView chooseInitialPosition(@Valid Idposition idpos,BindingResult br
    ,@PathVariable Integer gameId){
        Game game=gameService.getGameById(gameId);
        Player player=game.getCurrentPlayer();
        ModelAndView result=null;
        if(br.hasErrors()){
            result=new ModelAndView("redirect:/play/"+gameId);
        }
        try{
            Position position= positionServiceRepo.findPositionById(idpos.getId());
            this.playerUsePositionService.occupyTroopPosition(position, player,false);
            gameService.nextPlayerAndSave(game);
            result=new ModelAndView("redirect:/play/"+gameId);
        }catch(Exception e){
            result=new ModelAndView("redirect:/play/"+gameId);
        }
        return result;
    }

    @GetMapping("{gameId}/chooseWhitePositions")
    public ModelAndView initDeployWhiteTroops(@PathVariable Integer gameId){
        ModelAndView result=new ModelAndView(ROUND_ZERO);
        Game game=gameService.getGameById(gameId);
        Player player = game.getCurrentPlayer();
        
        List<Position> initialPositions=this.positionInGameService.getAvailableFreeWhiteTroopPositions(game);
        putPlayerDataInModel(game, player, result);
        result.addObject("positions", initialPositions);
        result.addObject("totalVp", game.getPlayerScore(player).getTotalVp());
        result.addObject("vp", game.getPlayerScore(player));
        result.addObject("totalinnerCirclevp", game.getInnerCircleVP(player));
        result.addObject("whiteTroopsLeft",gameService.getNumberOfWhiteTroopsLeftToDeploy(game));
        return result;
    }

    @PostMapping("{gameId}/chooseWhitePositions")
    public ModelAndView processChosenWhitePosition(@Valid Idposition idpos,BindingResult br
    ,@PathVariable Integer gameId){
        Game game=gameService.getGameById(gameId);
        Player whitePlayer=this.playerService.getPlayerById(0);
        ModelAndView result=null;
        if(br.hasErrors()){
            result=new ModelAndView("redirect:/play/"+gameId);
        }
        try{
            Position position= positionServiceRepo.findPositionById(idpos.getId());
            this.playerUsePositionService.occupyTroopPosition(position, whitePlayer,false);
            gameService.save(game);
            result=new ModelAndView("redirect:/play/"+gameId);
        }catch(Exception e){
            result=new ModelAndView("redirect:/play/"+gameId);
        }
        return result;
    }

    

    @GetMapping("{gameId}/round/{round}")
    public ModelAndView showRoundN(@PathVariable Integer gameId, @PathVariable Integer round){
        ModelAndView result=new ModelAndView(ROUND_N);
        Game game=gameService.getGameById(gameId);
        Player player = game.getCurrentPlayer();
        List<Position> positions=positionServiceRepo.getAllPositionsByGame(game);
        List<Integer> sellZoneCounter = List.of(0,1,2,3,4,5);
        putPlayerDataInModel(game, player, result);
        result.addObject("game", game);
        result.addObject("player", player);
        result.addObject("positions", positions);
        result.addObject("totalVp", game.getPlayerScore(player).getTotalVp());
        result.addObject("vp", game.getPlayerScore(player));
        result.addObject("totalinnerCirclevp", game.getInnerCircleVP(player));
        result.addObject("sellZoneCounter", sellZoneCounter);
        return result;
    }

    @GetMapping("{gameId}/round/{round}/next")
    public ModelAndView nextTurn(@PathVariable("gameId") Game game){
        ModelAndView result=new ModelAndView("redirect:/play/{gameId}");

        if(game.canFinishTurn()){
            endTurnService.execute(game);
        }else{
    
            gameService.loadEndTurnActionAndSave(game);
            result=new ModelAndView("redirect:/play/{gameId}/round/{round}/execute-action");
            
        }
        
        return result;
    }





    @GetMapping("{gameId}/round/{round}/card-action/{cardId}")
    public String playCard (@PathVariable Integer gameId,@PathVariable Integer cardId){
        return null;
    }
    @GetMapping("{game}/round/{round}/buy/{card}")
    public String buyCard (@PathVariable Game game,@PathVariable Card card){
        try {
            playerMoveCardsService.buyCard(card, game.getCurrentPlayer());
        } catch (Exception e) {
            System.out.println(game);
            System.out.println(card);
            e.printStackTrace();
        }
        return "redirect:/play/{game}";
    }

    @GetMapping("{gameId}/scoreboard")
    public ModelAndView showScoreBoard(@PathVariable Integer gameId){
        Game game=gameService.getGameById(gameId);
        ModelAndView result= new ModelAndView(SCORE_BOARD);
        result.addObject("ranking", game.getQualifying());
        result.addObject("players", game.getPlayers());
        return result;
    }
    
}
