package com.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
@RequiredArgsConstructor
public class IndexPage {

    private final RestResource restResource;

    @GetMapping({"", "/"})
    String index(Model model) {
        var map = restResource.greeting();
        model.addAttribute("message", map.get("message"))
             .addAttribute("ctx", SecurityContextHolder.getContext())
             .addAttribute("user", (DefaultOAuth2User) SecurityContextHolder.getContext()
                                                                            .getAuthentication()
                                                                            .getPrincipal());
        return "index";
    }
}
