package org.springframework.samples.petclinic.card.action.executeActions;

import org.springframework.samples.petclinic.card.action.Action;
import org.springframework.samples.petclinic.cardsMovement.PlayerMoveCardsService;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.player.Player;

public class AutomaticActions {

    public static void earnInfluence(Game game, Action action){
        Player player = game.getCurrentPlayer();
        player.earnInfluence(action.getValue());

    }

    public static void earnPower(Game game, Action action){
        Player player = game.getCurrentPlayer();
        player.earnPower(action.getValue());
    }

    public static void earnVpFor2ControlledSites (Game game, Action action){
        Player player = game.getCurrentPlayer();
        Integer ControlledCities = (int) (long) game.getCities().stream()
        .filter(city->(city.whoControls()!=null && city.whoControls().equals(player))||
        (city.whoTotallyControls()!=null && city.whoTotallyControls().equals(player)))
        .count();
        if(ControlledCities>=2){
            Integer Vp= ControlledCities/2;
            Integer ActualVP=player.getVpEarned();
            player.setVpEarned(Vp+ActualVP);
        }
    }
    public static void earnVpForTotalControlledSites (Game game, Action action){
        Player player = game.getCurrentPlayer();
        Integer TotalControlledCities = (int) (long) game.getCities().stream()
        .filter(city->city.whoTotallyControls()!=null && city.whoTotallyControls().equals(player))
        .count();
        Integer Vp= TotalControlledCities;
        Integer ActualVP=player.getVpEarned();
        player.setVpEarned(Vp+ActualVP);
    }
    
    public static void earnVpFor3WhiteKilled (Game game, Action action){
        Player player = game.getCurrentPlayer();
        Integer whitesKilled = (int) (long) player.getTrophyHall().stream()
        .filter(troop->troop.isWhite())
        .count();
        if(whitesKilled>=3){
            Integer Vp= whitesKilled/3;
            Integer ActualVP=player.getVpEarned();
            player.setVpEarned(Vp+ActualVP);
        }
    }

    public static void earnVpFor5Killed (Game game, Action action){
        Player player = game.getCurrentPlayer();
        Integer troopsKilled = player.getTrophyHall().size();
        if(troopsKilled>=5){
            Integer Vp= troopsKilled/5;
            Integer ActualVP=player.getVpEarned();
            player.setVpEarned(Vp+ActualVP);
        }
    }

    public static void earnVpFor3Inner (Game game, Action action){
        Player player = game.getCurrentPlayer();
        Integer innerCards = player.getInnerCircle().size();
        if(innerCards>=3){
            Integer Vp= innerCards/3;
            Integer ActualVP=player.getVpEarned();
            player.setVpEarned(Vp+ActualVP);
        }
    }
    public static Boolean checkInnerCardsGreaterThan(Game game,Integer value){
        return game.getCurrentPlayer().getInnerCircle().size()>value;
    }

    public static Boolean checkAnyEnemyPlayerTroopInSite(Game game, Integer value) {
        return game.getLastSpyLocation().getAdjacents().stream().filter(position->position.isOccupied())
        .filter(position->!position.getPlayer().isWhite() & !position.getPlayer().equals(game.getCurrentPlayer())).count()>=value;
    }

    //CHECK_KILLED_PLAYER_TROOPS_GREATER_THAN
    public static Boolean checkKilledEnemyPlayerTroopsGreaterThan(Game game, Integer value){
        return game.getCurrentPlayer().getTrophyHall().stream().filter(player->!player.isWhite()).count()>value;
    }



}
