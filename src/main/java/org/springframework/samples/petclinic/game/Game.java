package org.springframework.samples.petclinic.game;

import java.util.ArrayList;

import java.util.Collections;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import org.springframework.samples.petclinic.board.map.MapTemplate;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.board.sector.path.Path;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.HalfDeck;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="games")
public class Game extends BaseEntity{

    @NotEmpty
    String name= "";
    

    @Temporal(TemporalType.DATE)
    Date date = new Date();

    @Column(columnDefinition = "integer default 0")
    private Integer round=0;

    @Min(1)
    @Column(columnDefinition = "integer default 1")
    private Integer turnPlayer=1;
    
    @Column(name="is_finished",columnDefinition = "boolean default false")
    Boolean isFinished = false;

    protected List<Player> getPlayersInternal() {
		if (this.players == null) {
			this.players = new ArrayList<>();
		}
		return this.players;
	}
    
    Boolean finished = false;
    
    @Column(columnDefinition = "boolean default false")
    Boolean loaded = false;

    @NotNull
    @Column(columnDefinition = "integer default 2")
    Integer size=2;

    @ManyToOne()
    MapTemplate mapTemplate = new MapTemplate();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    List<City> cities = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    List<Path> paths = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game",fetch = FetchType.EAGER)
    private List<Player> players = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "first_half_deck_id",columnDefinition = "integer default 1")
    private HalfDeck firstHalfDeck= new HalfDeck();

    @ManyToOne()
    @JoinColumn(name = "second_half_deck_id",columnDefinition = "integer default 2")
    private HalfDeck secondHalfDeck = new HalfDeck();

    @ManyToMany
    private List<Card> gameDeck = new ArrayList<>();
    
    @ManyToMany
    private List<Card> sellZone = new ArrayList<>();
    
    @ManyToMany
    private List<Card> devoured = new ArrayList<>();
    
    @ManyToMany
    private List<Card> houseGuards = new ArrayList<>();

    @ManyToMany
    private List<Card> lolths = new ArrayList<>();


    public void addPlayer(Player player) {
        getPlayers().add(player);
        player.setGame(this);
    }
    public void removePlayer(Player player) {
        players.remove(player);
        player.setGame(null);
    }
    
    public void setNextPlayer(){
       this.turnPlayer= (this.turnPlayer)%this.players.size()+1;
       if(this.turnPlayer==1) this.round++;
    }

    public Player getCurrentPlayer(){
        return this.players.get(this.turnPlayer-1);
    }

    public Boolean isFinished(){
        return getFinished();
    }

    /*public Boolean isNotLoaded(){
        return (getRound()==0 && getCurrentPlayer().equals(this.players.get(0)));
    }*/

    public void finishGame(){
        this.finished=true;
    }
    //TODO: COMPROBAR ESO

    public SortedMap<Player,Integer> getQualifying(){
        SortedMap<Player,Integer> map= new TreeMap<>();
        for(Player player:this.players){
            Integer result=getPlayerScore(player);
            map.put(player,result);
        }
        map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return map;
    }

    public Player getWinner(){
        return getQualifying().firstKey();
    }

    public Integer getPlayerScore(Player player){
        Integer result=0;
        Integer controlVP=getControlCityVP(player);
        Integer totalControlVP=getTotalControlVP(player);
        Integer trophyHallVP=player.getTrophyHallVPs();
        Integer handVP=player.getHandVPs();
        Integer dicardPileVP=player.getDiscardPile().stream().collect(Collectors.summingInt(card->card.getDeckVP()));
        Integer deckVP=player.getDeck().stream().collect(Collectors.summingInt(card->card.getDeckVP()));
        Integer innerCircleVP= getInnerCircleVP(player);
        result=controlVP+totalControlVP+trophyHallVP+handVP+dicardPileVP+deckVP+innerCircleVP;
        return result;
    }

    //No hacer privada, la utilizo en un controlador, gracias :D
    public Integer getInnerCircleVP(Player player){
        return player.getInnerCircle().stream()
        .collect(Collectors.summingInt(card->card.getInnerCirclePV()));
    }

    private Integer getControlCityVP(Player player){
        return this.cities.stream()
        .filter(city->city.whoControls()!=null && city.whoControls().equals(player))
        .collect(Collectors.summingInt(city->city.getVpEndgameValue()));
    }

    private Integer getTotalControlVP(Player player){
        return this.cities.stream()
        .filter(city->city.whoTotallyControls()!=null && city.whoTotallyControls().equals(player))
        .collect(Collectors.summingInt(city->city.getVpEndgameValue() + 2));
    }    
    
    public Boolean isNotLoaded(){
        return !getLoaded();
    }

    @Override
    public String toString() {
        return "Game [name=" + name + ", mapTemplate=" + mapTemplate + ", cities=" + cities + ", paths=" + paths
                + ", gameDeck=" + gameDeck + "]";
    }

 
    

    
}

