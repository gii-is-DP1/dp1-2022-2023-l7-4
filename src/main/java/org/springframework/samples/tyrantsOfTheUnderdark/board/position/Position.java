package org.springframework.samples.tyrantsOfTheUnderdark.board.position;

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

import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city.City;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.path.Path;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
import org.springframework.samples.tyrantsOfTheUnderdark.player.Player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "positions")
@Entity
@JsonIgnoreProperties({"city","path","game","adjacents","inPath","inCity"})
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
    private List<Position> adjacents=new ArrayList<>();
    
    
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

    //For JSON when in path
    public Double getX(){
        return 50.;
    }
    public Double getY(){
        return 50.;
    }
    
}
