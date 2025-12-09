package com.vet.controller;

import com.vet.model.*;
import com.vet.service.RegistrosFachada;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List; 

@RestController
@RequestMapping("/veterinarios")
public class VeterinarioController {

    private final RegistrosFachada fachada;

    public VeterinarioController(RegistrosFachada f) {
        this.fachada = f;
    }

    @GetMapping
    public ResponseEntity<List<Veterinario>> listar() {
        return ResponseEntity.ok(fachada.listarVeterinarios());
    }

    @PostMapping
    public ResponseEntity<GenericResponse> cadastrar(@RequestBody Veterinario v) {
        fachada.cadastrarVeterinario(v);
        return ResponseEntity.ok(new GenericResponse("OK", "Veterinário cadastrado."));
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<GenericResponse> deletar(@PathVariable String cpf) {
        fachada.deletarVeterinario(cpf);
        return ResponseEntity.ok(new GenericResponse("OK", "Veterinário removido com sucesso."));
    }
}
