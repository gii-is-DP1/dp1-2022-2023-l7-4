package org.springframework.samples.petclinic.player;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.house.House;
import org.springframework.samples.petclinic.house.HouseService;
import org.springframework.samples.petclinic.user.AuthoritiesService;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerService {




    private PlayerRepository playerRepository;


    private UserService userService;


    private AuthoritiesService authoritiesService;

	@Autowired
	private HouseService houseService;

	@Autowired
	public PlayerService(PlayerRepository playerRepository,UserService userService
	,AuthoritiesService authoritiesService){
		this.playerRepository=playerRepository;
		this.userService=userService;
		this.authoritiesService=authoritiesService;
	}


	@Transactional(readOnly = true)
	public Collection<Player> getPlayers(){
		return (Collection<Player>) playerRepository.findAll();
	}

	@Transactional
	public void updatePlayersGames(Integer player, Integer game){
		playerRepository.updatePlayersGames(player,game);}
	@Transactional
	public List<Player> getPlayerByName(String name){
		return playerRepository.findPlayerByName(name);
	}

	@Transactional(readOnly = true)
	public Player getPlayerById(int id){
		return playerRepository.findPlayerById(id);
	}

	@Transactional(readOnly = true)
	public Player getPlayerByUsername(String username){
		return playerRepository.findPlayerByUsername(username);
	}



    @Transactional
	public void savePlayer(Player player) throws DataAccessException {
		playerRepository.save(player);
	}
	
	@Transactional
	public void deletePlayer (Integer id){
		playerRepository.deleteById(id);
	}


    public void setHouses(Game game) {
		List<House> houses =  houseService.getHouses();
		game.getPlayers().forEach(p -> p.setHouse(houses.remove(houses.size()-1)));
    }


    public void savePlayers(@Valid Game game) {
		for (Player player:game.getPlayers()){
			player.setGame(game);
			savePlayer(player);
		}
    }

	/* 
	@Transactional
    public Player playerFromUser(User user, Game game){
		Player player = Player.of(user);
		player.setGame(game);
		playerRepository.save(player);
		return player;
	}*/
}