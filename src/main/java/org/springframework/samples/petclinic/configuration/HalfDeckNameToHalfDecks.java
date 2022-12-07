package org.springframework.samples.petclinic.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.samples.petclinic.card.CardServiceRepo;
import org.springframework.samples.petclinic.card.HalfDeck;
import org.springframework.stereotype.Component;



@Component
public class HalfDeckNameToHalfDecks implements Converter<Object, HalfDeck>{
    @Autowired
    CardServiceRepo cardServiceRepo;

    public HalfDeck convert(Object element){
        Integer id = Integer.parseInt((String)element);
        HalfDeck halfDeck = cardServiceRepo.getHalfDeckById(id);
        System.out.println(halfDeck);
        return halfDeck;
    }
    
}