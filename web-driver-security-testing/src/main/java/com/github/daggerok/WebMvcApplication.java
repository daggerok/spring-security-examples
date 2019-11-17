package com.github.daggerok;

import io.vavr.collection.HashMap;
import io.vavr.control.Try;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.csrf.LazyCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.security.config.http.SessionCreationPolicy.IF_REQUIRED;

//tag::security-config[]
@Log4j2
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    //@formatter:off
    http
        .authorizeRequests()
          .antMatchers("/login", "/webjars", "/favicon.*")
            .permitAll()
          .anyRequest()
            .fullyAuthenticated()
          .and()
        .cors()
          .disable()
        .csrf()
          .csrfTokenRepository(new LazyCsrfTokenRepository(new HttpSessionCsrfTokenRepository()))
          .and()
        .headers()
          .frameOptions()
            .sameOrigin()
          .xssProtection()
            .xssProtectionEnabled(true)
            .and()
          .and()
        .formLogin()
          .defaultSuccessUrl("/")
          .failureUrl("/login?error")
          .failureForwardUrl("/login?failure")
          .and()
        .sessionManagement()
          .sessionCreationPolicy(IF_REQUIRED)
          .invalidSessionUrl("/login?invalidSession")
          .sessionAuthenticationErrorUrl("/login?sessionAuthenticationError")
          .sessionFixation()
            .migrateSession()
            .and()
        .logout()
          .clearAuthentication(true)
          .invalidateHttpSession(true)
          .deleteCookies("JSESSIONID", "PLAY_SESSION", "NXSESSIONID", "csrfToken", "SESSION")
          .permitAll(true)
    ;
    //@formatter:on
  }

  @Override
  @Autowired
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //@formatter:off
    HashMap.of("usr", "pwd")
           .forEach((username, password) -> Try.run(() -> auth
               .inMemoryAuthentication()
                 .withUser(username)
                 .password(passwordEncoder().encode(password))
                 .roles("APP", "APP_USER", "APPLICATION_USER")));
    //@formatter:on
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
//end::security-config[]

//tag::controller[]
@Controller
class IndexPage {

  // @GetMapping
  // with get mapping here we're receiving an error like:
  // POST method is not supported right after re-login
  @RequestMapping({ "/", "/err", "/index" })
  public String index() {
    return "index";
  }

  @GetMapping("")
  public String redirect() {
    return "forward:/";
  }

  @RequestMapping({ "/logout", "/logout/**" })
  public String logout() {
    return "redirect:/login?logout";
  }
}
//end::controller[]

@SpringBootApplication
public class WebMvcApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebMvcApplication.class, args);
  }
}
