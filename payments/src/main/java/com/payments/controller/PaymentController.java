package com.payments.controller;

import com.payments.model.PaymentRequest;
import com.payments.model.PaymentResponse;
import com.payments.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagar")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public PaymentResponse pagar(@RequestBody PaymentRequest req) {
        return service.process(req);
    }
}