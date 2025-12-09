package com.vet.controller;

import com.vet.service.RegistrosFachada;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final RegistrosFachada fachada;

    public PagamentoController(RegistrosFachada fachada) {
        this.fachada = fachada;
    }

    @PostMapping
    public ResponseEntity<GenericResponse> realizarPagamento(@RequestBody PagamentoDTO dto) {

        fachada.realizarPagamento(dto.consultaId, dto.tipo, dto.valor);
        
        return ResponseEntity.ok(new GenericResponse("OK", "Pagamento realizado e registrado."));
    }
}

class PagamentoDTO {
    public Long consultaId;
    public String tipo;
    public Float valor;
}