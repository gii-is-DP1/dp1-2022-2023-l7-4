package org.springframework.samples.petclinic.game;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class GameService {
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
		return grepo.findById2(id);
	}


	public void save(Game game) throws DataAccessException {
		grepo.saveAndFlush(game);
	}	

    
}