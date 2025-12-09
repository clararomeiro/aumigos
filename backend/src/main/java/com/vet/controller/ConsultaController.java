package com.vet.controller;

import com.vet.model.Consulta;
import com.vet.service.RegistrosFachada;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final RegistrosFachada fachada;

    public ConsultaController(RegistrosFachada f) {
        this.fachada = f;
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> listar() {
        return ResponseEntity.ok(fachada.listarConsultas());
    }

    @PostMapping
    public ResponseEntity<GenericResponse> agendar(@RequestBody ConsultaDTO dto) {
        fachada.agendarConsulta(dto.animal, dto.vet, dto.data);
        return ResponseEntity.ok(new GenericResponse("OK", "Consulta agendada."));
    }
}

class ConsultaDTO {
    public Long animal;
    public String vet;
    public String data;
}
