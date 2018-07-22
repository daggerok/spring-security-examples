package com.github.daggerok;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.parseMediaType;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//tag::unauthorized[]
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class MockMvcSecurityTests {

  @Autowired
  WebApplicationContext wac;

  private MockMvc mvc;

  @Before
  public void setup() {
    this.mvc = MockMvcBuilders.webAppContextSetup(wac)
                              .apply(springSecurity())
                              .build();
  }

  @Test
  @SneakyThrows
  public void unauthorized_request_should_be_redirected_to_login_page() {
    mvc.perform(get("/"))
       .andExpect(status().isFound())
       .andExpect(header().string("location", containsString("/login")))
    ;
  }
  //end::unauthorized[]

  //tag::permit-all[]
  @Test
  @SneakyThrows
  public void login_page_is_publicly_accessible() {
    mvc.perform(get("/login"))
       .andExpect(status().isOk())
    ;
  }
  //end::permit-all[]

  //tag::authorized[]
  @Test
  @SneakyThrows
  @WithMockUser
  public void authorized_request_test() {
    mvc.perform(get("/"))
       .andExpect(status().isOk())
       .andExpect(content().contentType(parseMediaType("text/html;charset=UTF-8")))
       .andExpect(content().string(containsString("<title>Index Page</title>")))
    ;
  }
  //end::authorized[]
}
