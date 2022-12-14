package org.springframework.samples.tyrantsOfTheUnderdark.player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city.City;
import org.springframework.samples.tyrantsOfTheUnderdark.card.Card;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
import org.springframework.samples.tyrantsOfTheUnderdark.house.House;
import org.springframework.samples.tyrantsOfTheUnderdark.model.BaseEntity;
import org.springframework.samples.tyrantsOfTheUnderdark.user.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "players")
@JsonIgnoreProperties({"power", "influence", "vpEarned", "user","controlMarker","totalControlMarker", "game","hand","played","discarded","innerCircle","handVPs","deck","markerVP","trophyHallVPs", "house", "troops", "spies", "markerVp", "trophyHall","white","new"})
public class Player extends BaseEntity{


    @NotBlank
    String name;
    
    @Column(columnDefinition = "integer default 0")
    @Min(0)
    Integer power = 0;

    @Column(columnDefinition = "integer default 0")
    @Min(0)
    Integer influence = 0;

    @Column(name = "vp_earned",columnDefinition = "integer default 0")
    @Min(0)
    private int vpEarned = 0;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name="game_id", nullable=true)
    private Game game;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="house_id",nullable = true)


    private House house;
   
    @Column(columnDefinition = "integer default 40")
    @Min(0)
    private int troops=40;

    @Column(columnDefinition = "integer default 5")
    @Min(0)
    private int spies=5;

    @Column(columnDefinition = "integer default 0")
    @Min(0)
    private int markerVP=0;

    public List<City> getControlMarker(){
        return this.getGame().getCities().stream()
        .filter(city->city.whoControls()!=null && city.whoControls().equals(this))
        .collect(Collectors.toList());
    }

    public List<City> getTotalControlMarker(){
        return this.getGame().getCities().stream()
        .filter(city->city.whoTotallyControls()!=null && city.whoTotallyControls().equals(this))
        .collect(Collectors.toList());
    }

    @ManyToMany()
    @JoinTable(
        inverseJoinColumns=
            @JoinColumn(name="killed_player_id"))
    private List<Player> trophyHall =  new ArrayList<>();

    @ManyToMany()
    @JoinTable(
        inverseJoinColumns=
            @JoinColumn(name="card_id"))
    private List<Card> deck =  new ArrayList<>();

    @ManyToMany
    @JoinTable(
        inverseJoinColumns=
            @JoinColumn(name="card_id"))
    private List<Card> hand = new ArrayList<>(); 

    @ManyToMany
    @JoinTable(
        inverseJoinColumns=
            @JoinColumn(name="card_id"))
    private List<Card> played = new ArrayList<>(); 



    @ManyToMany
    @JoinTable(
        inverseJoinColumns=
            @JoinColumn(name="card_id"))
    private List<Card> discarded = new ArrayList<>(); 

    @ManyToMany
    @JoinTable(
        inverseJoinColumns=
            @JoinColumn(name="card_id"))
    private List<Card> innerCircle = new ArrayList<>(); 


    public Boolean isWhite(){
        return this.id==0;
    }

    public Integer getTrophyHallVPs(){
        return this.getTrophyHall().size();
    }

    public Integer getHandVPs(){
        return this.getHand().stream().collect(Collectors.summingInt(card->card.getDeckVP()));
    }

    public void earnInfluence(Integer n){
        Integer newInfluence = influence+n;
        influence = newInfluence<0?0:newInfluence;
    }
    public void earnPower(Integer n){
        Integer newPower = power+n;
        power = newPower<0?0:newPower;
    }
    @Override
    public String toString() {
        return name;
    }

    public static Player of(User user){
        Player player = new Player();
        player.setUser(user);
        return player;   
    }

    public static Player ofUser(User user) {
        Player player = new Player();
        player.setUser(user);
        player.setName(user.getName());
        return player;
    }

    //USED IN JSON DO NOT DELETE THIS
    @JsonInclude
    public String color(){
        return house.getHexColor();
    }
}