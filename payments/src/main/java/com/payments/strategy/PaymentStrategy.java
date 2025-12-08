package com.payments.strategy;

import com.payments.model.PaymentResponse;

public interface PaymentStrategy {
    PaymentResponse process(double valor);
}