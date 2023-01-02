package org.springframework.samples.petclinic.player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VictoryPoints { 

    Integer controlVP;

    Integer totalControlVP;

    Integer trophyHallVP;

    Integer handVP;

    Integer dicardPileVP;

    Integer deckVP;

    Integer innerCircleVP; 

    Integer earnedVP;

    public Integer getTotalVP(){
        Integer result=0;
        result=this.controlVP+this.totalControlVP+this.trophyHallVP+this.handVP+this.dicardPileVP+this.deckVP+this.innerCircleVP+this.earnedVP;
        return result;
    }
    
}