package com.example.step1;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class IndexPage {

    private final RestTemplate restTemplate;

    @GetMapping({"", "/"})
    String index(Model model) {
        var response = restTemplate.getForEntity("http://127.0.0.1:8080/api/greeting", Map.class);
        var map = response.getBody();
        model.addAttribute("message", map.get("message"))
             .addAttribute("ctx", SecurityContextHolder.getContext());
        return "index";
    }
}
