package org.springframework.samples.petclinic.house;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


@Service
public class HouseService {
    @Autowired
    HouseRepository houseRepository;

    @Transactional
	public House getHousesByName(String username){
		return houseRepository.findHousesByName(username);
	}
	public List<House> getHouses(){
		return (List<House>)houseRepository.findAll();
	}


	@Transactional
	public House getHousesById(Integer id){
		return houseRepository.findById(1);
	}

    @Transactional
	public void saveHouse(House house) throws DataAccessException {
		houseRepository.save(house);
	}	

    
}