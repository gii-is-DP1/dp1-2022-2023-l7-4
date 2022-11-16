package org.springframework.samples.petclinic.house;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class HouseService {
    @Autowired
    HouseRepository hrepo;

    @Transactional
	public House getHousesByName(String username){
		return hrepo.findHousesByName(username);
	}

	@Transactional
	public House getHousesById(Integer id){
		return hrepo.findById(1);
	}

    @Transactional
	public void saveHouse(House house) throws DataAccessException {
		hrepo.save(house);
	}	

    
}