package org.springframework.samples.petclinic.player;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.map.sector.city.CityRepository;
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
	public Collection<Player> getPlayerByName(String name){
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
		
		userService.saveUser(player.getUser());
		
		authoritiesService.saveAuthorities(player.getUser().getUsername(), "player");
	}
	
	@Transactional
	public void deletePlayer (Integer id){
		playerRepository.deleteById(id);
	}

    
}