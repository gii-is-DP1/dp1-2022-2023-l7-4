package org.springframework.samples.petclinic.player;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.user.AuthoritiesService;
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
	public Collection<Player> getPlayerByUsername(String username){
		return playerRepository.findPlayersByUsername(username);
	}

	@Transactional
	public Player getPlayerById(Integer id){
		return playerRepository.findById(1);
	}

    @Transactional
	public void savePlayer(Player player) throws DataAccessException {
		//creating owner
		playerRepository.save(player);		
		//creating user
		userService.saveUser(player.getUser());
		//creating authorities
		authoritiesService.saveAuthorities(player.getUser().getUsername(), "player");
	}	

    
}