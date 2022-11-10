package org.springframework.samples.petclinic.player;

import java.time.LocalDate;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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


    String name;

    @Email
    String email;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    LocalDate birthdate;

    Boolean privilege;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;

    /* @ManyToOne
    @JoinColumn(name="game_id", nullable=false)
    private Game game; */
   
}