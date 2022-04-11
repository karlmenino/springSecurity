package com.example.qcm.qcm.configuration;

import com.example.qcm.qcm.entity.User;
import com.example.qcm.qcm.service.JpaUserDetailService;
import com.example.qcm.qcm.service.JpaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("jpaUserDetailService")
    @Autowired
    private UserDetailsService jpaUserDetailsService;
    @Autowired
    HttpSession session;
    @Autowired
    JpaUserService jpaUserService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession (true)
                .deleteCookies ("JSESSIONID")
                .logoutSuccessUrl ("/")
                .permitAll()
                .and()
                .rememberMe ()
                .key ("test")
                .tokenValiditySeconds (2400)
                .rememberMeParameter ("remember-me");
    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("recup")
                .password(passwordEncoder().encode("recup"))
                .roles("ADMIN","USER")
                .authorities("WITHDRAW","DEPOSIT","ADMIN");
        auth.userDetailsService(jpaUserDetailsService).passwordEncoder(passwordEncoder());
    }
//    @EventListener
//    public void succesAuthentification(InteractiveAuthenticationSuccessEvent event) {
//        UserDetails userDetail = (UserDetails) event.getAuthentication ().getPrincipal ();
//        User user = jpaUserService.findByUsername (userDetail.getUsername ());
//        session.setAttribute ("user",user);
//    }
}