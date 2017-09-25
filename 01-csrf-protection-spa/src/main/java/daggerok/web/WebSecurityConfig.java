package daggerok.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.http.HttpMethod.POST;

//tag::content[]
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  @Autowired
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

    auth
        .inMemoryAuthentication()
        .withUser("user")
          .password("password")
          .roles("USER")
          .and()
        .withUser("admin")
          .password("admin")
          .roles("ADMIN");
  }

  @Override
  public void configure(final WebSecurity web) throws Exception {

    web.ignoring()
       .antMatchers(
           "/favicon.ico",
           "/webjars/**",
           "/login.html",
           "/index.html",
           "/logout.html"
       );
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {

    http
        .authorizeRequests()
          .antMatchers(POST)
          .hasRole("ADMIN")
        .anyRequest()
          .authenticated()
          .and()
        .formLogin()
          .defaultSuccessUrl("/", true)
          .permitAll()
          .and()
        .logout()
          .logoutUrl("/logout")
          .logoutSuccessUrl("/")
          .clearAuthentication(true)
          .deleteCookies("JSESSIONID")
          .invalidateHttpSession(false)
          .permitAll()
          .and()
        .headers()
          .frameOptions()
          .sameOrigin()
          .and()
        .csrf()
          .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
          .and()
        .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.NEVER)
    ;
  }
}
//end::content[]
