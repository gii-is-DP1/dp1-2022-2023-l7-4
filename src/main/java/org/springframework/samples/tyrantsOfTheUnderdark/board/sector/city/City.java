package org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.samples.tyrantsOfTheUnderdark.board.position.Position;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
import org.springframework.samples.tyrantsOfTheUnderdark.model.BaseEntity;
import org.springframework.samples.tyrantsOfTheUnderdark.player.Player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "cities")
@JsonIgnoreProperties({"cityTemplate","game","freeTroopPosition","positions","new","unaligned"})
public class City extends BaseEntity{
   
    //this contains all constant data.
    @ManyToOne(optional = false)
    CityTemplate cityTemplate= new CityTemplate(); 

    @ManyToOne
    private Game game;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "city")
    private List<Position> positions= new ArrayList<>();

    public List<Position> getTroopPositions(){
        return positions.stream().filter(pos->pos.getForSpy()==false).collect(Collectors.toList());
    }
    public List<Position> getSpyPositions(){
        return positions.stream().filter(pos-> pos.getForSpy()).collect(Collectors.toList());
    }
    public List<Position> getFreeTroopPosition(){
        return positions.stream().filter(pos->pos.getForSpy()==false & !pos.isOccupied()).collect(Collectors.toList());
    }
    

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

            //Check player has all troop positions available ocuppied
            if( players.get(player).intValue() != getCapacity()) return null;

            if(positions.stream().filter(ps-> ps.getForSpy())
            .allMatch(p->player.equals(p.getPlayer()) || p.getPlayer() == null)) return player;
            return null;
        
        }
        public static City of(CityTemplate template,Game game){
            City city = new City();
            city.setCityTemplate(template);
            city.setGame(game);
            return city;
        }

        
        public Integer getCapacity() {return cityTemplate.getCapacity();}
        public String getName() {return cityTemplate.getName();}
        public Integer getZone() {return cityTemplate.getZone();}
        public Integer getMarkerVp() {return cityTemplate.getMarkerVp();}
        public Integer getVpEndgameValue() {return cityTemplate.getVpEndgameValue();}
        public Integer getMarkerInfluence() {return cityTemplate.getMarkerInfluence();}
        public Boolean isStartingCity(){
            return cityTemplate.getStartingCity();
        }
        public List<Integer> getUnaligned(){
            List<Integer> res = new ArrayList<>();
            try {
                String [] s = cityTemplate.getUnaligned().split(",");
                for (String n : s) {
                        res.add(Integer.parseInt(n));
    
                }
                
            } catch (Exception e) {
                // TODO: handle exception
            }
            return res;
        }
        
    public Boolean hasNoPlayerTroops(){
        return this.positions.stream().allMatch(position->position.isOccupied()==false|| position.getPlayer().isWhite());
    }
    @Override
    public String toString() {
        return  getName() ;
    }

    //For JSON
    public Double getX(){
        return cityTemplate.getX();
    }
    public Double getY(){
        return cityTemplate.getY();
    }
    public String getControlColor(){
        Player controlling = whoControls();
        if(controlling==null) return "#fff";
        return controlling.color();
    }
    public String getTotalControlColor(){
        Player controlling = whoTotallyControls();
        if(controlling==null) return "#fff";
        return controlling.color();
    }
}
