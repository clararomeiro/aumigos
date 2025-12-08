package com.vet.controller;

import com.vet.model.*;
import com.vet.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/veterinarios")
public class VeterinarioController {

    private final VeterinarioService service;

    public VeterinarioController(VeterinarioService s) {
        this.service = s;
    }

    @PostMapping
    public ResponseEntity<GenericResponse> cadastrar(@RequestBody Veterinario v) {
        service.cadastrar(v);
        return ResponseEntity.ok(new GenericResponse("OK", "Veterinário cadastrado."));
    }
}
