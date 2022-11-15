package org.springframework.samples.petclinic.player;

import java.util.Collection;

import org.hibernate.type.TrueFalseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.user.AuthoritiesService;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthoritiesService authoritiesService;

	@Transactional(readOnly = true)
	public Collection<Player> getPlayers(){
		return (Collection<Player>) playerRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Collection<Player> getPlayerByName(String username){
		return playerRepository.findPlayersByName(username);
	}

	@Transactional(readOnly = true)
	public Player getPlayerById(int id){
		return playerRepository.findById(id);
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