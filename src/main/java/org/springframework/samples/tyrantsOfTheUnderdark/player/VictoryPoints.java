package org.springframework.samples.tyrantsOfTheUnderdark.player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VictoryPoints implements Comparable<VictoryPoints> { 

    Integer controlVP;

    Integer totalControlVP;

    Integer trophyHallVP;

    Integer handVP;

    Integer discardPileVP;

    Integer deckVP;

    Integer innerCircleVP; 

    Integer earnedVP;

    Integer markerVP;
    
    Integer totalVp;


    public Integer setTotalVp(){
        Integer result=0;
        result=this.controlVP+this.totalControlVP+this.trophyHallVP+this.handVP+this.discardPileVP+this.deckVP+this.innerCircleVP+this.earnedVP+this.markerVP;
        return this.totalVp=result;
    }

    public Integer getTotalVPByCards(){
        return deckVP+handVP+discardPileVP;
    }

    @Override
    public int compareTo(VictoryPoints victoryPoints){
        return(int)(victoryPoints.getTotalVp() - this.totalVp);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        VictoryPoints other = (VictoryPoints) obj;
        if (totalVp == null) {
            if (other.totalVp != null)
                return false;
        } else if (!totalVp.equals(other.totalVp))
            return false;
        return true;
    }
    
    
}