package com.zuitt.wdc044.config;
//WebSecurityConfig
    // - class that contains the following:
    // - instantiation of a BcryptPassword Encoder object for use in hashing a user's password upon registration
    //which routes exempt from auth (auth will be implemented on others routes).
    //what to do during the authentication in jwtAuthenticate
    //what filters to apply and in what order(jwtRequestFilter before UsernamePasswordAuthenticationFilter)

import com.zuitt.wdc044.services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticate jwtAuthenticate;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());

    }

    //Indicate what to do during authentication
    @Bean
    public static JwtAuthenticate jwtAuthenticationEntryPointBean() throws Exception{
        return new JwtAuthenticate();
    }

    //Instantiate a BCryptPasswordEncoder object for password hashing which can be used later.
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //This will allow use to exempt routes from requiring Jwt Tokens.
    //All other routes not exempted here will require Jwt Tokens
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {


        httpSecurity.cors().and().csrf().disable()

                .authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/users/register").permitAll()
                .antMatchers(HttpMethod.GET, "/posts").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticate).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

}

