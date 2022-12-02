package org.springframework.samples.petclinic.player;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.house.House;
import org.springframework.samples.petclinic.user.User;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "players")
public class Player{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trophyHall")
    private List<Player> trophyHall=new ArrayList<>();

    @ManyToMany()
    @JoinTable(
        inverseJoinColumns=
            @JoinColumn(name="CARD_ID"))
    private List<Card> deck =  new ArrayList<>();

    @ManyToMany
    @JoinTable(
        inverseJoinColumns=
            @JoinColumn(name="CARD_ID"))
    private List<Card> hand = new ArrayList<>(); 

    @ManyToMany
    @JoinTable(
        inverseJoinColumns=
            @JoinColumn(name="CARD_ID"))
    private List<Card> played = new ArrayList<>(); 

    @ManyToMany
    @JoinTable(
        inverseJoinColumns=
            @JoinColumn(name="CARD_ID"))
    private List<Card> discardPile = new ArrayList<>(); 

    @ManyToMany
    @JoinTable(
        inverseJoinColumns=
            @JoinColumn(name="CARD_ID"))
    private List<Card> innerCircle = new ArrayList<>(); 
    

    
    @Override
    public String toString() {
        return "ply_"+id +": "+ name;
    }
}