package org.generation.webproject.security;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.web.bind.annotation.*;

import javax.sql.*;


@Configuration
@EnableWebSecurity //below extends = inherit
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /*
        overriding the methods from WebSecurityConfigurerAdapter Class which is provided by the
        Spring Security package.

        1) provide a datasource:
        Spring will locate the data source from the applicationProperties
        -a datasource is an object for connection to the database(s). We need to provide the URL,
         username/password credential to establish the database connection
         -When we do the autowired annotation, Spring boot will auto-inject the information from
         the application.properties on the related configuration that it requires
    */
    @Autowired
    private DataSource dataSource;

    /*
    AuthenticationManagerBuilder is a built-in class by the Spring Security package
    @Autowired will inject the AuthenticationManagerBuilder class to this class
    (WebSecurityConfig) so that we are able to use/override the methods from the
    AuthenticationManagerBuilder Class
        Spring Security package is generic. We need to override to provide our own information/
        customisation/configuration. e.g. passwordEncoder
    */
    /*
    username=? : represents a parameter to be supplied by the client (browser)
    usersByUsernameQuery : sets the query to be used for finding a user by their username
        Method from AuthenticationManagerBuilder Class: provide a query that is used to find a
        username and password from the database
        Login page: jean; password: 1234
    authoritiesByUsernameQuery : Sets the query to be used for finding a user's
    authorities by their username
    */

    //For authentication handling
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from users where username=?")
                .authoritiesByUsernameQuery("select username, role from users where username=?")
        ;
    }

    /*
    After the authentication, what do you need to customise if the user login or logout from the
    session, e.g. When user is authenticated (login) as an admin, user will be able to access the
     ProductForm.html. When the user is not authenticated (logout), user will be able still acess
      other pages (homepage, aboutUs, product)

    */

    @CrossOrigin
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //CSRF: cross-site request forgery. to disable just for development in localhost
        //as localhost is not a valid domain
        http.csrf().disable();

        //Not using the Spring Security HttpSecurity default login page
        http.formLogin().loginPage("/login");

        //if user successfully login, user will be directed to the productform.html
        http.formLogin()
                .defaultSuccessUrl("/productform");

        //if user successfully logout, user will be directed to the index.html
        http.logout()
                .logoutSuccessUrl("/index");


        /*.antMatchers(......).permitAll() - tells Spring Security that these webpages
         do not need to have login services

        .antMatchers(.....).hasRole("ADMIN") - tells Spring Security that only user
        with ADMIN role will be able to access the productform.html

        logout method : configure logout functionality provided by Spring Security -
        ensure that the login session to be invalidated.

         */
        http.authorizeRequests()
                .antMatchers("/", "/products", "/aboutus").permitAll()
                .antMatchers("/productform/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().permitAll();
    }
}
