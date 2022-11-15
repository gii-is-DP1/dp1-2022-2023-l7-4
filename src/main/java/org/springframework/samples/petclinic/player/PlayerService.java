package org.springframework.samples.petclinic.player;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
	public Collection<Player> getPlayerByName(String name){
		return playerRepository.findPlayersByName(name);
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