package org.springframework.samples.petclinic.map.position;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.map.position.auxiliarEntitys.CheckPlayerUsePosition;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class PricedPositionService {
    
    //poner aqui 3 funciones, uno por cada acción básica del turno que haga lo mismo que el positionservice
    //pero como parametro player y restandole coste(como referencia: buycard en marketplayermovementservice)

    private PlayerUsePositionService playerUsePositionService;
    private PlayerRepository playerRepository;

    @Autowired
    public PricedPositionService(PlayerUsePositionService playerUsePositionService,PlayerRepository playerRepository){
        this.playerUsePositionService=playerUsePositionService;
        this.playerRepository=playerRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    public void placeTroopWithPrice(Player player,Position position) throws Exception{
        CheckPlayerUsePosition.playerHasEnoughtPowerToPlaceTroop(player);
        playerUsePositionService.occupyTroopPosition(position, player, true);
        player.setPower(player.getPower()-1);
        playerRepository.save(player);
    }

    @Transactional(rollbackOn = Exception.class)
    public void killEnemyTroopWithPrice(Player player,Position position) throws Exception{
        CheckPlayerUsePosition.playerHasEnoughtPowerToKillEnemyTroop(player);
        playerUsePositionService.killTroop(position, player, true);
        player.setPower(player.getPower()-3);
        playerRepository.save(player);
    }

    @Transactional(rollbackOn = Exception.class)
    public void returnEnemySpyWithPrice(Player player,Position position) throws Exception{
        CheckPlayerUsePosition.playerHasChooseASpy(position);
        CheckPlayerUsePosition.playerHasEnoughtPowerToReturnSpy(player);
        playerUsePositionService.returnPiece(position, player);
        player.setPower(player.getPower()-3);
        playerRepository.save(player);

    }


}
