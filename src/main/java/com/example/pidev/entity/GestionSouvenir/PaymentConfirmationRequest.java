package com.example.pidev.entity.GestionSouvenir;

import javax.validation.constraints.NotBlank;

public class PaymentConfirmationRequest {
    @NotBlank(message = "L'ID de la méthode de paiement est requis")
    private String paymentMethodId;

    // Constructeurs
    public PaymentConfirmationRequest() {}

    public PaymentConfirmationRequest(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }


    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
}
