package com.jvportfolio.controller;

import com.jvportfolio.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/visitors")
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService visitorService;

    // POST: increment and return new count (called on page load)
    @PostMapping("/count")
    public ResponseEntity<Map<String, Long>> incrementCount() {
        long count = visitorService.incrementAndGetCount();
        return ResponseEntity.ok(Map.of("count", count));
    }

    // GET: just read the count
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getCount() {
        long count = visitorService.getCount();
        return ResponseEntity.ok(Map.of("count", count));
    }
}
