package com.github.daggerok;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.htmlunit.webdriver.LocalHostWebConnectionHtmlUnitDriver;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

//tag::permit-all[]
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class HtmlUnitWebDriverSecurityTests {

  @Autowired
  Environment env;

  private HtmlUnitDriver driver;

  @Before
  public void setUp() throws Exception {
    this.driver = new LocalHostWebConnectionHtmlUnitDriver(env);
  }

  @Test
  @SneakyThrows
  public void login_page_is_publicly_accessible() {
    driver.get("/");
    assertThat(driver.getTitle()).contains("Login Page");
  }
  //end::permit-all[]

  //tag::login[]
  @Test
  @SneakyThrows
  public void login_test() {

    driver.get("/");

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
