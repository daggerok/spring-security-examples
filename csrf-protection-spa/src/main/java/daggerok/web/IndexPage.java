package daggerok.web;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
public class IndexPage {

  @GetMapping({
      "/",
      "404"
  })
  public String get() {
    return "/index.html";
  }

  //tag::content[]
  @GetMapping("/logout")
  public String logoutGet(final HttpServletRequest request, final HttpServletResponse response) {
    return logout(request, response);
  }

  @PostMapping("/logout")
  public String logoutPost(final HttpServletRequest request, final HttpServletResponse response) {
    return logout(request, response);
  }

  private String logout(final HttpServletRequest request, final HttpServletResponse response) {

    Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .ifPresent(authentication -> new SecurityContextLogoutHandler().logout(request, response, authentication));

    return "redirect:/login";
  }
  //end::content[]
}
