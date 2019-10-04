package com.soen6461.carrentalapplication.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class ClientSecurityConfiguration extends WebSecurityConfigurerAdapter {

	/*
	 * @Autowired DataSource dataSource;
	 */

	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}

	// Enable jdbc authentication
	/*
	 * @Autowired public void configAuthentication(AuthenticationManagerBuilder
	 * auth) throws Exception { auth.jdbcAuthentication().dataSource(dataSource); }
	 */

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	
	/*
	 * @Override protected void configure(HttpSecurity http) throws Exception {
	 * http.authorizeRequests().antMatchers("/").permitAll().antMatchers(
	 * "/vehicle-catalog") .hasAnyRole("USER",
	 * "ADMIN").antMatchers("/getClients").hasAnyRole("USER", "ADMIN")
	 * .antMatchers("/addNewClient").hasAnyRole("ADMIN").anyRequest().authenticated(
	 * ).and().formLogin()
	 * .loginPage("/login").permitAll().and().logout().permitAll().and() // added
	 * .httpBasic(); ;
	 * 
	 * http.formLogin().defaultSuccessUrl("/vehicle-catalog", true);
	 * http.csrf().disable(); }
	 */
	 

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/vehicle-catalog")
				.hasAnyRole("USER", "ADMIN").antMatchers("/getClients").hasAnyRole("USER", "ADMIN")
				.antMatchers("/addNewClient").hasAnyRole("ADMIN").anyRequest().authenticated().and().formLogin()
				.loginPage("/login").permitAll().and().logout().permitAll().and().httpBasic();
		http.formLogin().defaultSuccessUrl("/vehicle-catalog", true);
		http.csrf().disable();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
		authenticationMgr.inMemoryAuthentication().withUser("admin").password("admin").authorities("ROLE_USER")
				.and().withUser("clerk").password("clerk").authorities("ROLE_USER", "ROLE_ADMIN");
	}

}