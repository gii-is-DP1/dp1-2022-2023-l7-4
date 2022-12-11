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
import org.springframework.stereotype.Service;

@Service
public class GameService {
	@Autowired
	HouseService houseService;
	@Autowired
	UserService userService;
    @Autowired
    GameRepository grepo;

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
		return grepo.findById(id);
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
		game.addPlayer(player);

	}

    
}