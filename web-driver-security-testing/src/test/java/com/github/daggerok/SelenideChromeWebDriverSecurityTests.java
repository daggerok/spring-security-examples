package com.github.daggerok;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static com.codeborne.selenide.Selenide.$;
import static java.lang.String.format;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

//tag::permit-all[]
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SelenideChromeWebDriverSecurityTests {

  @LocalServerPort
  int port;

  private String baseUrl;

  @Before
  public void setUp() throws Exception {
    System.setProperty("selenide.browser", "chrome");
    this.baseUrl = format("http://127.0.0.1:%d", port);
  }

  public void open(final String uri) {
    final boolean isValidUri = null != uri && uri.startsWith("/");
    final String path = isValidUri ? uri : "/" + uri;
    Selenide.open(baseUrl + path);
  }

  @Test
  @SneakyThrows
  public void login_page_is_publicly_accessible() {

    open("/");

    $("html > head > title")
        .text()
        .contains("Login Page");
  }
  //end::permit-all[]

  //tag::login[]
  @Test
  @SneakyThrows
  public void login_test() {

    open("/");

    final SelenideElement form = $("form");

    form.$("input[name=username]")
        .setValue("usr")
    ;

    form.$("input[name=password]")
        .setValue("pwd")
        .submit()
    ;

    $("html > head > title")
        .text()
        .contains("Index Page");
  }
}
//end::login[]
