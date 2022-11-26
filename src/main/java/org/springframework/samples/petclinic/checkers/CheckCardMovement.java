package org.springframework.samples.petclinic.checkers;

import java.util.List;

import org.springframework.samples.petclinic.card.Card;

public class CheckCardMovement {
    
    public static void sellZoneContainsCard(List<Card> sellZone,Card card) throws Exception{
        
        Boolean condition = sellZone.contains(card);
 
        Preconditions.check(condition)
        .FormattedError("Card %s not found in sell zone", card);
    }

    public static void playerHasEnoughInfluenceToBuyCard(int playerInfluence,int cardCost) throws Exception{
        
        Boolean condition = playerInfluence>=cardCost;
        Preconditions.check(condition)
        .FormattedError("Player do not have enough resources (%s) to buy that card (%s)", playerInfluence, cardCost);
    }
}
