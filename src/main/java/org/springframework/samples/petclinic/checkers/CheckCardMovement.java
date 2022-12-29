package org.springframework.samples.petclinic.checkers;

import java.util.List;

import org.springframework.samples.petclinic.card.Card;

public class CheckCardMovement {

    public static void sellZoneContainsCard(List<Card> sellZone,Card card) throws Exception{
        
        Boolean mustBeTrue = sellZone.contains(card);
 
        Preconditions.check(mustBeTrue)
        .formattedError("Card %s not found in sell zone", card);
        }

    public static void playerHasEnoughInfluenceToBuyCard(int playerInfluence,int cardCost) throws Exception{
        
        Boolean mustBeTrue = playerInfluence>=cardCost;

        Preconditions.check(mustBeTrue)
        .formattedError("Player do not have enough resources (%s) to buy that card (%s)", playerInfluence, cardCost);
    }
}
