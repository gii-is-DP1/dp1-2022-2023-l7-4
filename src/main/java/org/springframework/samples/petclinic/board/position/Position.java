package org.springframework.samples.petclinic.board.position;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.board.sector.path.Path;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.security.access.method.P;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "positions")
@Entity
public class Position{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional=true)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne
    Game game;

    @ManyToOne(optional=true)
    @JoinColumn(name="city_id")
    private City city;

    @ManyToOne(optional = true)
    @JoinColumn(name="path_id")
    private Path path;

    @NotNull 
    @Column(name="for_spy")
    private Boolean forSpy;

    @ManyToMany
    @JoinTable(
        inverseJoinColumns = @JoinColumn(name = "adjacent_id"))
    private List<Position> adjacents=null;
    
    public List<Position> getAdjacentsInternal(){
        if(adjacents != null) return adjacents;
        return new ArrayList<>();
        
    }
    public void addAdjacents(List<Position> positions) {
		getAdjacentsInternal().addAll(positions);
	}
    
    public boolean isOccupied(){
        return player!=null;
    }
    public Boolean isInCity(){
        return city!=null;
    }
    public Boolean isInPath(){
        return path!=null;
    }
    

    @Override
    public String toString() {
        return ""+id ;
    }


    public static Position of(Game game) {
        Position p = new Position();
        p.player= null;
        p.setForSpy(false);
        p.setGame(game);
        return p;
    }

    
    
}
