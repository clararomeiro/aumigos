package com.vet.controller;

import com.vet.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/animais")
public class AnimalController {

    private final AnimalService service;

    public AnimalController(AnimalService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<GenericResponse> cadastrar(@RequestBody AnimalDTO dto) {
        service.cadastrar(
            dto.tutor, dto.nome, dto.especie,
            dto.raca, dto.nascimento, dto.sexo,
            dto.peso, dto.plano, dto.obs
        );
        return ResponseEntity.ok(new GenericResponse("OK", "Animal cadastrado."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.ok(new GenericResponse("OK", "Animal removido."));
    }
}

class AnimalDTO {
    public String tutor;
    public String nome;
    public String especie;
    public String raca;
    public String nascimento;
    public String sexo;
    public Float peso;
    public Long plano;
    public String obs;
}