package com.example.step1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class RestResource {

    @GetMapping("/api/greeting")
    Map<String, String> greeting() {
        return Collections.singletonMap("message", "Hello, hello!");
    }
}
