package org.springframework.samples.petclinic.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import org.springframework.samples.petclinic.house.House;
import org.springframework.samples.petclinic.house.HouseService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.board.position.PositionServiceRepo;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.board.sector.city.CityService;

import org.springframework.stereotype.Service;

@Service
public class GameService {
	@Autowired
	HouseService houseService;
	@Autowired
	UserService userService;
    @Autowired
    GameRepository grepo;

    @Autowired
	CityService cityService;

	@Autowired
	PositionServiceRepo positionServiceRepo;



	@Transactional
	public Collection<Game> getGameByName(String name){
		return grepo.findGameByName(name);
	}

	@Transactional
	public Collection<Game> getGames(){
		return (Collection<Game>) grepo.findAll();
	}

	@Transactional
	public Game getGameById(int id){
		return grepo.findById2(id);
	}
    @Transactional
	public void saveGame(Game game) throws DataAccessException {
		grepo.save(game);
	}

	public void setGameAndHouseToPlayers(Game game){
		List<Player> players = game.getPlayers();
		List<Player> playerAux = new ArrayList<>(players);
		players.removeAll(playerAux);
		List<House> houses = houseService.getHouses();
		houses.remove(0);
		playerAux.forEach(p -> p.setGame(game));
		int i = 0;
		for(Player p:playerAux){
			p.setHouse(houses.get(i++));
			game.addPlayerInternal(p);
		}
	}

	public void addPlayerByUsername(Game game, String username){
		Player player = new Player();
		User user = userService.getUserByUsername(username);
		player.setName(user.getName());
		player.setUser(user);
		game.addCurrentPlayer(player);
	}


	public void save(Game game) throws DataAccessException {
		grepo.saveAndFlush(game);
	}

	public void nextPlayerAndSave(Game game) throws DataAccessException{
		game.setNextPlayer();
		save(game);
	}

    public void loadEndTurnActionAndSave(Game game) {
		game.setCurrentAction(game.getEndTurnAction());
		game.setEndTurnAction(null);
        save(game);
    }

    public boolean enoughUnaligned(Game game) {
		Boolean res=false;
		List<Position> positions =this.positionServiceRepo.getTroopPositionsFromGame(game);
        Long numberOfWhiteTroopsToDeploy=Math.round(positions.size()*0.28);
		if(this.positionServiceRepo.getTroopPositionsOfPlayer(0, game).size()==numberOfWhiteTroopsToDeploy) res=true;
        return res;
    }

    
}