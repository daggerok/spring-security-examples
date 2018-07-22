package com.github.daggerok;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.htmlunit.webdriver.LocalHostWebConnectionHtmlUnitDriver;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Ignore
//tag::permit-all[]
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ChromeWebDriverSecurityTests {

  @LocalServerPort
  int port;

  private ChromeDriver driver;
  private String baseUrl;

  @Before
  public void setUp() throws Exception {
    final boolean headless = false;
    System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
    this.driver = new ChromeDriver(new ChromeOptions().setHeadless(headless));
    this.baseUrl = format("http://127.0.0.1:%d", port);
  }

  public void open(final String uri) {
    final boolean isValidUri = null != uri && uri.startsWith("/");
    final String path = isValidUri ? uri : "/" + uri;
    driver.get(baseUrl + path);
  }

  @Test
  @SneakyThrows
  public void login_page_is_publicly_accessible() {
    open("/");
    assertThat(driver.getTitle()).contains("Login Page");
  }
  //end::permit-all[]

  //tag::login[]
  @Test
  @SneakyThrows
  public void login_test() {

    open("/");
    assertThat(driver.getTitle()).contains("Login Page");

    final WebElement form = driver.findElementByTagName("form");

    form.findElement(By.cssSelector("input[name=username]"))
        .sendKeys("usr");
    form.findElement(By.cssSelector("input[name=password]"))
        .sendKeys("pwd");
    form.submit();

    assertThat(driver.getTitle()).contains("Index Page");
  }
}
//end::login[]
