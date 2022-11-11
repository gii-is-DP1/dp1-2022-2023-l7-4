package org.springframework.samples.petclinic.player;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.BaseEntity;
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


    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;

    @Column(columnDefinition = "integer default 40")
    @Min(0)
    private int troops=40;

    @Column(columnDefinition = "integer default 5")
    @Min(0)
    private int spies=5;

    @Column(columnDefinition = "integer default 0")
    @Min(0)
    private int trophyPV=0;

    
    /* @ManyToOne
    @JoinColumn(name="game_id", nullable=false)
    private Game game; */
    
    @Override
    public String toString() {
        return "Player [id=" + id + ", name=" + name 
        + ", email=" + email + ", birthdate=" + birthdate 
        + ", user=" + user + ", troops=" + troops 
        + ", spies=" + spies + ", trophyPV=" + trophyPV
                + "]";
    }
}