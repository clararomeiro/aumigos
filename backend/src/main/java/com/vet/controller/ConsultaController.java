package com.vet.controller;

import com.vet.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService service;

    public ConsultaController(ConsultaService s) {
        this.service = s;
    }

    @PostMapping
    public ResponseEntity<GenericResponse> agendar(@RequestBody ConsultaDTO dto) {
        service.agendar(dto.animal, dto.vet, dto.data);
        return ResponseEntity.ok(new GenericResponse("OK", "Consulta agendada."));
    }
}

class ConsultaDTO {
    public Long animal;
    public String vet;
    public String data;
}
