package com.payments.strategy;

import com.payments.model.PaymentResponse;

public class CreditPaymentStrategy implements PaymentStrategy {

    @Override
    public PaymentResponse process(double valor) {
        return new PaymentResponse(
            "OK",
            "Pagamento no crédito aprovado. Valor: R$ " + valor
        );
    }
}