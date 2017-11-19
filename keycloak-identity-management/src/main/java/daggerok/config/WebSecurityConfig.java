package daggerok.config;

/*
    auth
        .inMemoryAuthentication()
        .withUser("user")
          .password("password")
          .roles("USER")
          .and()
        .withUser("admin")
          .password("admin")
          .roles("ADMIN");

    web.ignoring()
       .antMatchers(
           "/favicon.ico",
           "/webjars/**",
           "/login.html",
           "/index.html",
           "/logout.html"
       );

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
*/

//tag::content[]
public class WebSecurityConfig {}
//end::content[]
