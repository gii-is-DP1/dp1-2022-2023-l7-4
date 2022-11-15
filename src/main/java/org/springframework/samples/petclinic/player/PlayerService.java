package org.springframework.samples.petclinic.player;

import java.util.Collection;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.user.AuthoritiesService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthoritiesService authoritiesService;

	@Transactional
	public Collection<Player> getPlayes(){
		return (Collection<Player>) playerRepository.findAll();
	}

	@Transactional
<<<<<<< HEAD
	public void updatePlayersGames(Integer player, Integer game){
		playerRepository.updatePlayersGames(player,game);}
	@Transactional
	public Player getPlayerByName(String username){
		return playerRepository.findPlayersByUsername(username);
=======
	public Collection<Player> getPlayerByName(String name){
		return playerRepository.findPlayersByName(name);
>>>>>>> b193d587252abe198cee0f551914d5d695b1a128
	}

	@Transactional
	public Collection<Player> getPlayerByUsername(String name){
		return playerRepository.findPlayersByUsername(name);
	}
	@Transactional
	public Player getPlayerById(int id){
		return playerRepository.findById(id);
	}

    @Transactional
	public void savePlayer(Player player) throws DataAccessException {
		
		playerRepository.save(player);		
		
		userService.saveUser(player.getUser());
		
		authoritiesService.saveAuthorities(player.getUser().getUsername(), "player");
	}

    
}