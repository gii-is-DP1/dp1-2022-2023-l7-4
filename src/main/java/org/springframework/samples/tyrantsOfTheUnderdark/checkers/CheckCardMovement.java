package org.springframework.samples.tyrantsOfTheUnderdark.checkers;

import java.util.List;

import org.springframework.samples.tyrantsOfTheUnderdark.card.Card;

public class CheckCardMovement {

    public static void sellZoneLolthsGuardsContainsCard(List<Card> sellZone,List<Card> lolths, List<Card> houseGuards, Card card) throws Exception{
        
        Boolean mustBeTrue = sellZone.contains(card);
        Boolean mustBeTrue2 = lolths.contains(card);
        Boolean mustBeTrue3 = houseGuards.contains(card);

        Preconditions.check(mustBeTrue || mustBeTrue2 || mustBeTrue3)
        .formattedError("Card %s not found in sell zone", card);
        }

    public static void playerHasEnoughInfluenceToBuyCard(int playerInfluence,int cardCost) throws Exception{
        
        Boolean mustBeTrue = playerInfluence>=cardCost;

        Preconditions.check(mustBeTrue)
        .formattedError("Player do not have enough resources (%s) to buy that card (%s)", playerInfluence, cardCost);
    }
}
