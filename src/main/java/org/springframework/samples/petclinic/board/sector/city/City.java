package org.springframework.samples.petclinic.board.sector.city;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.board.sector.Sector;
import org.springframework.samples.petclinic.player.Player;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "cities")
public class City extends Sector{

    @NotBlank
    private String name;
    
    Integer zone;

    @NotBlank
    @Min(1) //RN-vpEndgameValue >0
    @Column(name="vp_endgame_value")
    private Integer vpEndgameValue;

    @NotBlank
    @Column(name="starting_city")
    private Boolean isStartingCity;

    private Integer vpControlled;
    private Integer ifluenceTotalControlled;
    private Integer vpTotalControlled;
    @ManyToOne
    private Player controllingPlayer;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "city")
    private List<Position> positions;
    

    /**
     * <pre>
     * control is archived by having more troops than
     * any other player in the city
     * </pre>
     * @return <b>player</b> who controlls or <b>null</b>
     */
    public Player whoControls(){
        if(whoTotallyControls()!=null) return null; //skips if TC
        Map<Player,Integer> playersMap = new HashMap<>();
        int max=0;
        Player controller=null;
        List<Position> troopPosition = positions.stream().filter(ps-> !ps.getForSpy()).collect(Collectors.toList());
        for(Position p: troopPosition){ //map Player, number of troops
            Player player = p.getPlayer();
            if(player!=null){

                playersMap.put(player,playersMap.getOrDefault(player, 0)+1);
                int counter = playersMap.get(player);
                if(counter>max){ 
                    max = counter;
                    controller = player; //becomes the controller
                }else if(counter == max){// its a tie, no one has the control
                    controller= null;
                }

            }
        }
        return controller;
    }
    /**<pre>
     * Total control is archieved by having all troop-positions
     * occupied by the same player. Also it is necessary that
     * there is no enemy spies (different player) in the spy-zones
     * </pre>
     * @return <b>player</b> who totally controlls or <b>null</b>
     */
    public Player whoTotallyControls(){
        //no enemy spyes
        //all positions with one player
        
            List<Position> troopPosition = positions.stream()
            .filter(ps-> !ps.getForSpy()).collect(Collectors.toList());

            
            Map<Player,Long> players = troopPosition.stream()
            .filter(p-> p.getPlayer() != null) //avoids NullPointerException: element cannot be mapped to a null key
            .collect(Collectors.groupingBy(p -> p.getPlayer(), 
            Collectors.counting()));
            
            if(players.keySet().size()!=1) return null;
            
            Player player = players.keySet().iterator().next();

            if( players.get(player).intValue() != getCapacity()) return null;

            
            if(positions.stream().filter(ps-> ps.getForSpy())
            .allMatch(p->player.equals(p.getPlayer()) || p.getPlayer() == null)) return player;
            return null;
        
        }
        @Override
    public String toString() {
        return  name ;
    }
}
