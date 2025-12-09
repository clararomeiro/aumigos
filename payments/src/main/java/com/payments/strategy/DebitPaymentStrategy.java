package com.payments.strategy;

import com.payments.model.PaymentResponse;
import org.springframework.stereotype.Component;

@Component("DEBITO")
public class DebitPaymentStrategy implements PaymentStrategy {

    @Override
    public PaymentResponse process(double valor) {
        return new PaymentResponse(
            "OK",
            "Pagamento no débito aprovado. Valor: R$ " + valor
        );
    }
}