package org.springframework.samples.petclinic.player;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
<<<<<<< HEAD
import javax.persistence.FetchType;
=======
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
>>>>>>> b193d587252abe198cee0f551914d5d695b1a128
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.game.Game;
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

    @Email
    String email;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    LocalDate birthdate;

    Boolean privilege;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="game_id", nullable=true)
    private Game game;

   protected Set<Game> getGamesInternal() {
    if (this.games == null) {
        this.games = new HashSet<>();
    }
    return this.games;
}
protected void setGamesInternal(Set<Game> games) {
    this.games = games;
}

public void addGame(Game game) {
    getGamesInternal().add(game);
    game.setPlayers(new HashSet<>());
}
}