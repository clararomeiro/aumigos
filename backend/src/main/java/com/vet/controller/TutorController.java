package com.vet.controller;

import com.vet.model.*;
import com.vet.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    private final TutorService service;

    public TutorController(TutorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Tutor> buscar(@RequestParam String nome) {
        Tutor tutor = service.buscarPorNome(nome);
        if (tutor == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tutor);
    }

    @PostMapping
    public ResponseEntity<GenericResponse> cadastrar(@RequestBody Tutor t) {
        service.cadastrar(t);
        return ResponseEntity.ok(new GenericResponse("OK", "Tutor cadastrado com sucesso."));
    }
}
