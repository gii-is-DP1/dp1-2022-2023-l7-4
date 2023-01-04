package org.springframework.samples.petclinic.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.board.map.MapTemplate;
import org.springframework.samples.petclinic.board.position.Position;
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

    @ManyToOne
    Action endTurnAction;

    @Temporal(TemporalType.DATE)
    Date date = new Date();

    @Column(columnDefinition = "integer default -1")
    private Integer round=-1;

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
    
    @Column(columnDefinition = "boolean default false")
    Boolean lastActionSkipped= false;

    @Column(columnDefinition = "boolean default true",name="automatic")
    Boolean automaticWhiteTroops= true;
    
    //TODO  esto es para las acciones que piden suplantar una tropa en donde pusiste/devolviste espia
    @OneToOne()
    Position lastSpyLocation;

    @OneToOne()
    Position chosenPieceToMove;
    
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

    
    //TODO esto es nuevo, almacena las cartas a ascender a final de turno
    @ManyToMany
    private List<Card> toBePromoted;


    
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

    public Map<Player,Integer> getQualifyingTotalVp(){
        Map<Player,Integer> map= new LinkedHashMap<>();
        for(Player player:this.players){
            Integer result=getPlayerScore(player).getTotalVp();
            map.put(player,result);
        }
        Map<Player, Integer> resultado = map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (map1, map2) -> map1, LinkedHashMap::new));
        return resultado;
    }
    
    public Map<Player, VictoryPoints> getQualifying(){
        Map<Player,VictoryPoints> map= new LinkedHashMap<>();
        for(Player player:this.players){
            VictoryPoints vps=getPlayerScore(player);
            map.put(player,vps);
        }
        Map<Player, VictoryPoints> resultado = map.entrySet()
                .stream()
                .sorted((e1,e2) -> e1.getValue().compareTo(e2.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (map1, map2) -> map1, LinkedHashMap::new));
        return resultado;

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
        return getQualifyingTotalVp().entrySet().stream().findFirst().get().getKey();
    }
    public VictoryPoints getPlayerScore(Player player){
        VictoryPoints vp= new VictoryPoints();
        Integer controlVP=getControlCityVP(player);
        Integer totalControlVP=getTotalControlVP(player);
        Integer trophyHallVP=player.getTrophyHallVPs();
        Integer handVP=player.getHandVPs();
        Integer dicardPileVP=player.getDiscarded().stream().collect(Collectors.summingInt(card->card.getDeckVP()));
        Integer deckVP=player.getDeck().stream().collect(Collectors.summingInt(card->card.getDeckVP()));
        Integer innerCircleVP= getInnerCircleVP(player);
        Integer vpEarned = player.getVpEarned();
        vp.setControlVP(controlVP);
        vp.setDeckVP(deckVP);
        vp.setDicardPileVP(dicardPileVP);
        vp.setInnerCircleVP(innerCircleVP);
        vp.setTotalControlVP(totalControlVP);
        vp.setTrophyHallVP(trophyHallVP);
        vp.setHandVP(handVP);
        vp.setEarnedVP(vpEarned);
        vp.setTotalVp();
        
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
    /**
     * Devuelve una nueva accion si no existe. NO GUARDADA EN BASE DE DATOS
     * @return
     */
    public Action getEndTurnAction(){
        if(endTurnAction==null){
            return Action.ofEndOfTurn();
        }else{
            return endTurnAction;
        }
    }
    @Override
    public String toString() {
        return "Game [name=" + name + ", date=" + date + ", round=" + round + ", turnPlayer=" + turnPlayer
                + ", finished=" + finished + ", loaded=" + loaded + ", mapTemplate=" + mapTemplate
                + ", cities=" + cities + ", paths=" + paths + ", players=" + players + ", firstHalfDeck="
                + firstHalfDeck + ", secondHalfDeck=" + secondHalfDeck + ", gameDeck=" + gameDeck + ", sellZone="
                + sellZone + ", devoured=" + devoured + ", houseGuards=" + houseGuards + ", lolths=" + lolths + "]";
    }
    public boolean canFinishTurn() {
        return endTurnAction==null;
    }
    public boolean hasLastActionSkipped() {
        return lastActionSkipped;
    }

    
 
    

    
}

