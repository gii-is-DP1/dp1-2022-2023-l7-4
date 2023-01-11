package org.springframework.samples.tyrantsOfTheUnderdark.user;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.samples.tyrantsOfTheUnderdark.player.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users",uniqueConstraints={@UniqueConstraint(columnNames={"username","email"})})
public class User{

	@Id
	@NotBlank
	@Column(unique=true,length = 30)
	String username;


	@NotBlank
	@NotNull
	String password;
	
	boolean enabled;

	@NotBlank
	@NotNull
    String name;

    @Email
    @NotBlank
	@Column(unique=true,length = 30)
    String email;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
    LocalDate birthdate;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Authorities> authorities;

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	private Set<Player> players;

	public Boolean canBeDeleted(){
		return players.isEmpty();
	}
}
