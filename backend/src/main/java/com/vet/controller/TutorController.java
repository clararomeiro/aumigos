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

    @GetMapping("/{cpf}")
    public ResponseEntity<Tutor> buscar(@PathVariable String cpf) {
        Tutor tutor = service.buscarPorCpf(cpf);
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
