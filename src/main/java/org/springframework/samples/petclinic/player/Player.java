package org.springframework.samples.petclinic.player;

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

import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.house.House;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.user.User;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "players")
public class Player extends BaseEntity{


    @NotBlank
    String name;
    
    @Column(columnDefinition = "integer default 0")
    @Min(0)
    Integer power;

    @Column(columnDefinition = "integer default 0")
    @Min(0)
    Integer influence;

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
    private int markerVP=40;

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
    private List<Card> discardPile = new ArrayList<>(); 

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

    
    

    
    @Override
    public String toString() {
        return "ply_"+id +": "+ name;
    }
}