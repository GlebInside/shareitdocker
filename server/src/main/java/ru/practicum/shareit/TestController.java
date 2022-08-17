package ru.practicum.shareit;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/test")
public class TestController {
    @GetMapping
    public Map<String, String> get() {
        return Map.of("v", "1");
    }
}
