package com.soen6461.carrentalapplication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class is designed for different wen pages Authentication
 * and authorization
 */
@Configuration
@EnableWebSecurity
public class ClientSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SimpleAuthenticationSuccessHandler successHandler;

    @Autowired
    UserRegister userRegister;

    /**
     * Password encoder
     *
     * @return no operation password encoder.
     */
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    /**
     * Web security configuration.
     *
     * @param web web security.
     * @throws Exception Configuration exception.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    /**
     * Session registration.
     *
     * @return session registry.
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    /**
     * Http session event publisher.
     *
     * @return The servelet listener registration bean.
     */
    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }

    /**
     * Methods for authorization to Admin and Clerk
     *
     * @param http http
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("/")).and().authorizeRequests()
                .antMatchers("/vehicle-catalog").hasAnyRole("USER", User.RoleType.Clerk.name(), "ADMIN", User.RoleType.Administrator.name())
                .antMatchers("/client-register").hasAnyRole("USER", User.RoleType.Clerk.name())
                .antMatchers("/client-sign-up").hasAnyRole("USER", User.RoleType.Clerk.name())
                .antMatchers("/client-update").hasAnyRole("USER", User.RoleType.Clerk.name())
                .antMatchers("/online-help").hasAnyRole("USER", User.RoleType.Clerk.name(), "ADMIN", User.RoleType.Administrator.name())
                .antMatchers("/trans-list").hasAnyRole("ADMIN", User.RoleType.Administrator.name())
                .antMatchers("/vehicle-add").hasAnyRole("ADMIN", User.RoleType.Administrator.name())
                .antMatchers("/vehicle-register").hasAnyRole("ADMIN", User.RoleType.Administrator.name())
                .antMatchers("/edit-vehicle").hasAnyRole("ADMIN", User.RoleType.Administrator.name())
                .anyRequest().authenticated().and().formLogin().successHandler(successHandler)
                .loginPage("/login").permitAll().and().logout().permitAll().and().httpBasic()
                .and().exceptionHandling().accessDeniedPage("/accessDenied.jsp");
        // https://www.baeldung.com/spring-security-custom-access-denied-page

        http.formLogin().defaultSuccessUrl("/", true);
        http.csrf().disable();
    }

    /**
     * Method to access password from the application only
     *
     * @param authenticationMgr authenticationMgr
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationMgr) throws Exception {
        authenticationMgr.userDetailsService(inMemoryUserDetailsManager());
    }

    /**
     * Method to fetch passwords for Admin and Clerk
     */
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {

        // https://spring.io/guides/gs/securing-web/

        Collection<UserDetails> userDetailsCollection = new ArrayList<UserDetails>();

        // Add all clerks
        for (Clerk clerk : this.userRegister.getAllClerks()) {
            UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(clerk.getUsername())
                    .password(clerk.getPassword())
                    .roles(clerk.getRole().name())
                    .build();

            userDetailsCollection.add(userDetails);
        }

        // Add all administrators
        for (Administrator administrator : this.userRegister.getAllAdministrators()) {
            UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(administrator.getUsername())
                    .password(administrator.getPassword())
                    .roles(administrator.getRole().name())
                    .build();

            userDetailsCollection.add(userDetails);
        }

        return new InMemoryUserDetailsManager(userDetailsCollection);
    }
}
