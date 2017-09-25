package daggerok.web.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class Resource {

  @PostMapping("/message")
  Map<String, Object> message(@RequestBody final HashMap message) {
    log.warn("message: {}", message);
    return Collections.singletonMap("message", UUID.randomUUID().toString());
  }

  @PostMapping("/user")
  Map<String, Object> user(final Principal principal) {
    return Collections.singletonMap("user", principal);
  }
}
