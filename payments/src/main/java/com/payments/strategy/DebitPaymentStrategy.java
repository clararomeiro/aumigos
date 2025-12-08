package com.payments.strategy;

import com.payments.model.PaymentResponse;

public class DebitPaymentStrategy implements PaymentStrategy {

    @Override
    public PaymentResponse process(double valor) {
        return new PaymentResponse(
            "OK",
            "Pagamento no débito aprovado. Valor: R$ " + valor
        );
    }
}