package com.vet.controller;

import com.vet.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public ResponseEntity<GenericResponse> test() {
        return ResponseEntity.ok(new GenericResponse("OK", "Backend up and running"));
    }
}