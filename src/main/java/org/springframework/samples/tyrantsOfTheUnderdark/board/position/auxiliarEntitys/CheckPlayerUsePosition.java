package org.springframework.samples.tyrantsOfTheUnderdark.board.position.auxiliarEntitys;

import java.util.List;

import org.springframework.samples.tyrantsOfTheUnderdark.board.position.Position;
import org.springframework.samples.tyrantsOfTheUnderdark.checkers.Preconditions;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
import org.springframework.samples.tyrantsOfTheUnderdark.player.Player;

public class CheckPlayerUsePosition {

    public static void playerHasEnoughtPowerToPlaceTroop(Player player) throws Exception{
    Preconditions.check(player.getPower()>=1)
    .formattedError("No tienes suficiente poder para desplegar una tropa"+
    " ,necesitas 1 de poder y tienes %(s)", player.getPower());
    }

    public static void playerHasEnoughtPowerToKillEnemyTroop(Player player) throws Exception{
        Preconditions.check(player.getPower()>=3)
        .formattedError("No tienes suficiente poder para matar una tropa enemiga"+
        " ,necesitas 3 de poder y tienes %(s)", player.getPower());
        }
    public static void playerHasEnoughtPowerToReturnSpy(Player player) throws Exception{
    Preconditions.check(player.getPower()>=3)
    .formattedError("No tienes suficiente poder para devolver un espía enemigo"+
    " ,necesitas 3 de poder y tienes %(s)", player.getPower());
    }

    public static void playerHasChooseASpy(Position position) throws Exception{
        Preconditions.check(position.getForSpy())
        .formattedError("No puedes devolver una pieza que no es un espía");
    }

    public static void playerHasChooseAPosition(Position position) throws Exception{
        Preconditions.check(position!=null)
        .formattedError("Debes elegir una posición");
    }

    public static void playerHasChooseTwoPositionsOfSameType(Position target,Position source) throws Exception{
        Preconditions.check(target.getForSpy()==source.getForSpy())
        .formattedError("No puedes elegir 2 posiciones de distinto tipo");
    }

    public static void playerHasChooseAEmptyPosition(Position position) throws Exception{
        Preconditions.check(position.isOccupied()==false)
        .formattedError("No puedes elegir una posición ya ocupada por otro jugador");
    }

    public static void playerHasChooseANotEmptyPosition(Position position) throws Exception{
        Preconditions.check(position.isOccupied()==true)
        .formattedError("No puedes elegir una posición ya ocupada por otro jugador");
    }

    public static void playerHasChooseACorrectTypeOfPosition(Position position,Boolean isForSpy) throws Exception{
        Preconditions.check(position.getForSpy()==isForSpy)
        .formattedError(isForSpy?"No puedes escoger una posición para tropas para colocar 1 espía"
        :"No puedes escoger una posición para espías para colocar 1 tropa");
    }

    public static void playerHasChooseAPositionUsingPresence(Position position
    ,List<Position> presencePositionsOfPlayer) throws Exception{
        Preconditions.check(presencePositionsOfPlayer.contains(position))
        .formattedError("No puedes elegir una posición en la que no tengas presencia");
    }

    public static void playerIsNotNull(Player player) throws Exception{
        Preconditions.check(player!=null)
        .formattedError("El jugador no existe");
    }

    public static void playerHasChooseANotOwnedPosition(Player player,Position position) throws Exception{
        Preconditions.check(!position.getPlayer().equals(player))
        .formattedError("Para esta acción"+
        ", un jugador no puede seleccionar una posición ocupada por el mismo jugador");
    }

    public static void playerHasntAnySpyInChoosedPosition(List<Position> playerSpiesInSameCityOfChoosedPosition) throws Exception {
        Preconditions.check(playerSpiesInSameCityOfChoosedPosition.isEmpty())
        .formattedError("No puedes tener más de 1 espía en la misma ciudad,elige de nuevo");
    }
    //NO IMPLEMENTADO
    public static void playerIsPlayingInTheirTurn(Player player) throws Exception{
        Game game=player.getGame();
        Preconditions.check(game.getCurrentPlayer().equals(player))
        .formattedError("Cada jugador debe jugar en su turno");
    }

    public static void playerHasChooseACorrectTypeOfEnemy(Player player, Position position,Boolean onlyWhite) throws Exception{
        Player enemy=position.getPlayer();
        if(onlyWhite !=null & onlyWhite)
            Preconditions.check(enemy.isWhite())
            .formattedError("Cada jugador debe jugar en su turno");
        else if(onlyWhite!=false)
            Preconditions.check(!enemy.equals(player) & !enemy.isWhite())
        .formattedError("Cada jugador debe jugar en su turno");
    }

    public static void playerHasChooseAPositionInTheSameSite(Position position, Player player) throws Exception{
        Preconditions.check(player.getGame().getLastSpyLocation().getAdjacents().contains(position))
        .formattedError("No puedes elegir una posición que no se encuentre en el último sitio utilizado");
    }
    


    
    
}
