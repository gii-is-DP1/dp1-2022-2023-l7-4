package org.springframework.samples.tyrantsOfTheUnderdark.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/resources/**","/webjars/**","/h2-console/**").permitAll()
				.antMatchers(HttpMethod.GET, "/","/oups").permitAll()
				.antMatchers("/user/new","/js/**","/styles/**").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/session/**").permitAll()
				.antMatchers("/player/**").permitAll()
				.antMatchers("/myprofile/**").permitAll()
				.antMatchers("/changepassword/**").permitAll()
				.antMatchers("/users/**").hasAnyAuthority("admin")
				.antMatchers("/players/**").hasAnyAuthority("admin")
				.antMatchers("/admin/**").hasAnyAuthority("admin")
				.antMatchers("/games/**").hasAnyAuthority("admin","player")
				.antMatchers("/play/**").hasAnyAuthority("admin","player")
				.antMatchers("/positions/**").hasAnyAuthority("admin","player")
				.antMatchers("/cards/**").hasAnyAuthority("admin","player")
				.antMatchers("/sandbox/**").hasAnyAuthority("admin","player")
				.antMatchers("/api/**").hasAnyAuthority("admin","player")
				.antMatchers("/actions/**").hasAnyAuthority("admin","player")
				.anyRequest().denyAll()
				.and()
				 	.formLogin()
					.loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/")
                    .failureUrl("/login?error=true")
                    .usernameParameter("username")
                    .passwordParameter("password")
				 	.failureUrl("/login-error")
				.and()
					.logout()
						.logoutSuccessUrl("/"); 
                // Configuraci??n para que funcione la consola de administraci??n 
                // de la BD H2 (deshabilitar las cabeceras de protecci??n contra
                // ataques de tipo csrf y habilitar los framesets si su contenido
                // se sirve desde esta misma p??gina.
                http.csrf().ignoringAntMatchers("/h2-console/**");
                http.headers().frameOptions().sameOrigin();
				http.csrf().disable();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
	      .dataSource(dataSource)
	      .usersByUsernameQuery(
	       "select username,password,enabled "
	        + "from users "
	        + "where username = ?")
	      .authoritiesByUsernameQuery(
	       "select username, authority "
	        + "from authorities "
	        + "where username = ?")	      	      
	      .passwordEncoder(passwordEncoder());	
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {	    
		PasswordEncoder encoder =  NoOpPasswordEncoder.getInstance();
	    return encoder;
	}
	
}


