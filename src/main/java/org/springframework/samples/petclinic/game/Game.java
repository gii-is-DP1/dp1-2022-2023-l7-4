package org.springframework.samples.petclinic.game;

import java.util.ArrayList;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.board.map.MapTemplate;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.board.sector.path.Path;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.HalfDeck;
import org.springframework.samples.petclinic.card.action.Action;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.VictoryPoints;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="games")
public class Game extends BaseEntity{

    @NotEmpty
    String name= "";
    
    @ManyToOne
    Action currentAction;

    @Temporal(TemporalType.DATE)
    Date date = new Date();

    @Column(columnDefinition = "integer default 0")
    private Integer round=0;

    @Min(1)
    @Column(columnDefinition = "integer default 1")
    private Integer turnPlayer=1;
    
    protected List<Player> getPlayersInternal() {
		if (this.players == null) {
			this.players = new ArrayList<>();
		}
		return this.players;
	}
    
    @Column(columnDefinition = "boolean default false")
    Boolean finished = false;
    
    @Column(columnDefinition = "boolean default false")
    Boolean loaded = false;

    
    
    @ManyToOne()
    MapTemplate mapTemplate ;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    List<City> cities = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    List<Path> paths = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game",fetch = FetchType.EAGER)
    private List<Player> players = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "first_half_deck_id",columnDefinition = "integer default 1")
    private HalfDeck firstHalfDeck= null;

    @ManyToOne()
    @JoinColumn(name = "second_half_deck_id",columnDefinition = "integer default 2")
    private HalfDeck secondHalfDeck = null;

    @ManyToMany
    @JoinTable(name = "games_cards_in_deck",
    inverseJoinColumns = @JoinColumn(name = "card_in_deck_id"))
    private List<Card> gameDeck = new ArrayList<>();
    
    @ManyToMany
    private List<Card> sellZone = new ArrayList<>();
    
    @ManyToMany
    private List<Card> devoured = new ArrayList<>();
    
    @ManyToMany
    private List<Card> houseGuards = new ArrayList<>();
    
    @ManyToMany
    private List<Card> lolths = new ArrayList<>();
    
    @Column(columnDefinition = "integer default 0")
    private Integer numberOfPromoveCardFromDeckLeft=0;

    @Column(columnDefinition = "integer default 0")
    private Integer numberOfPromoveCardFromDiscardedLeft=0;

    @Column(columnDefinition = "integer default 0")
    private Integer numberOfPromoveCardFromPlayedLeft=0;

    public Boolean canFinishTurn(){
        return numberOfPromoveCardFromDeckLeft==0 & numberOfPromoveCardFromDiscardedLeft==0 & numberOfPromoveCardFromPlayedLeft==0;
    }

    
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
       if(this.turnPlayer==1) {
            this.round++;
            Boolean anyPlayerHasNoTroops = players.stream().anyMatch(player->player.getTroops()<=0);
            if(gameDeck.isEmpty() || anyPlayerHasNoTroops) setFinished(true);
            
       }
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
            Integer result=getPlayerScore(player).getTotalVP();
            map.put(player,result);
        }
        map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return map;
    }

        public void removePlayerByName(String name){
            
        }

    public void addPlayerInternal(Player player){
        getPlayersInternal().add(player);
    }
    public void addCurrentPlayer(Player player){
        getPlayersInternal().add(0, player);
        player.setGame(this);
    }
       

    public Player getWinner(){
        return getQualifying().firstKey();
    }

    /* public Integer getPlayerScore(Player player){
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
    } */
    public VictoryPoints getPlayerScore(Player player){
        VictoryPoints vp= new VictoryPoints();
        Integer controlVP=getControlCityVP(player);
        Integer totalControlVP=getTotalControlVP(player);
        Integer trophyHallVP=player.getTrophyHallVPs();
        Integer handVP=player.getHandVPs();
        Integer dicardPileVP=player.getDiscarded().stream().collect(Collectors.summingInt(card->card.getDeckVP()));
        Integer deckVP=player.getDeck().stream().collect(Collectors.summingInt(card->card.getDeckVP()));
        Integer innerCircleVP= getInnerCircleVP(player);
        vp.setControlVP(controlVP);
        vp.setDeckVP(deckVP);
        vp.setDicardPileVP(dicardPileVP);
        vp.setInnerCircleVP(innerCircleVP);
        vp.setTotalControlVP(totalControlVP);
        vp.setTrophyHallVP(trophyHallVP);
        vp.setHandVP(handVP);
        return vp;
        
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
    public Integer getSize(){
        return players.size();
    }
    public List<Integer> getPlayableZones(){
        List<Integer> zones = new ArrayList<>();
        zones.add(2);
        int playerCount = players.size();
        if(playerCount>=3) zones.add(3);
        if(playerCount==4) zones.add(1);
        return zones;
    }
    @Override
    public String toString() {
        return "Game [name=" + name + ", date=" + date + ", round=" + round + ", turnPlayer=" + turnPlayer
                + ", finished=" + finished + ", loaded=" + loaded + ", mapTemplate=" + mapTemplate
                + ", cities=" + cities + ", paths=" + paths + ", players=" + players + ", firstHalfDeck="
                + firstHalfDeck + ", secondHalfDeck=" + secondHalfDeck + ", gameDeck=" + gameDeck + ", sellZone="
                + sellZone + ", devoured=" + devoured + ", houseGuards=" + houseGuards + ", lolths=" + lolths + "]";
    }

    
 
    

    
}

