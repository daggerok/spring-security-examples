package daggerok.web;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

import static java.util.Collections.singletonMap;

@Slf4j
//@ControllerAdvice
//@RestControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(Throwable.class)
  public ResponseEntity<Map<String, Object>> handleAnyErrors(final Throwable throwable) {

    val error = Try.of(throwable::getLocalizedMessage)
                   .getOrElse("no message");

    log.error("unexpected error: {}", error, throwable);

    return ResponseEntity.badRequest()
                         .body(singletonMap("error", error));
  }
}
