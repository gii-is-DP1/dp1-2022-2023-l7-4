package org.springframework.samples.petclinic.card.action;

import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.player.Player;
//THIS CLASS IS IN ALPHA VERSION, IT HAS NOTES OF METHODS THAT WILL BE IMPLENTED HERE OR SOMEWHERE ELSE
public class ExecuteAction {
    
    public void gainInfluence(int amount,Player player){  
    }

    public void promoteAnotherPlayedCardsWhenFinished(Card card,int amount, Player player){ 
        //  in player or turn .setToPromote()=amount;
        //  .setExcludedCard(Card card) this cant be promoted
        //  getPlayedPromotableCards() somewhere (excluding these)
    }
    public void supplantWhiteTroop(Position position,Player player){ 
    }
    public void gainPower(int amount, Player player){ 
    }
    public void deployTroop(Position position, Player player){ 
    }
    public void supplantWhiteTroopAnywhere(Position position,Player player){ 
    }
    public void killTroop(Position position, Player player){

    }
    public void gainPowerIfMoreThan5PlayerKills(int amount, Player player){ 
    }

    public void gainInfluenceIfMoreThan4CardsAtInner(int amount, Player player){ 

    }
    public void PlaceSpy(Position position, Player player){ 
    }
    public void returnPiece(Position position, Player player){ 
    }
    public void returnOwnSpy(Position position, Player player){ 
    }
    public void killWhiteTroop(Position position, Player player){ 
    }
    public void returnOwnSpyAndSupplantThere(Position spyPos,Position enemy, Player player){ 
    //THIS IS A CONTROLLER METHOD DO NOT IMPLEMENT HERE
    }
    public void returnOwnSpyAndDrawCards(Position spyPos,int amount, Player player){ 
    //THIS IS A CONTROLLER METHOD DO NOT IMPLEMENT HERE
    }
    public void supplantTroop(Position position, Player player){ 

    }
    //------------------DRAGONS NEXT---------------------------------------------------
    
    public void gainVPforEvery2ControlledSites( Player player){  
        //site is equivalent to city
    }

    public void returnOwnSpyAndGainPower(int amount,Player player){  
        //DO NOT IMPLEMENT
    }
    public void returnEnemySpy( Player player){  
    }
    public void gainVPforEveryTotalControlledSites(int amount, Player player){  
    }
    public void returnOwnSpyAndGainInfluence( Player player){
        //DO NOT IMPLEMENT (COMPOUND)  
    }
    public void devoreMarketCard(Card card,Player player){  
        //remember to replace it later
        // market is equivalent sellzone
    }
    public void gainPowerAndInfluence( Player player){  
        //not neccesary its a compound one
    }
    public void gainVPforEvery3whiteTroops( Player player){  
    }
    public void placeSpyIfEnemyTroopThereGainInfluence( Player player){  
    }

    public void promoteAnotherPlayedCardsWhenFinishedAndGain( Player player){  
    }
    public void setEndTurnAction(Action action, Player player){
        /*
         * player has a list of endgame actions, in jsp if
         * no later actions end turn, else do endgame actions and
         * then proceed to endgame point getters (control...)
         */
    }
    public void drawCard(int amount,Player player){  
    }
    public void moveEnemyTroop( Player player){  
    }
    public void placeSpyAndSuplantThereAndVpforEveryControlled( Player player){  
    }
    
    // ------ ELEMENTAL NEXT--------------------
    
    public void gainInfluenceIfFocus(Card card, Player player){
        //focus is true if played or hand cards 
        // contains a card of
        // the same aspect as the card used   
    }
    public void dra( Player player){  
    }


}
