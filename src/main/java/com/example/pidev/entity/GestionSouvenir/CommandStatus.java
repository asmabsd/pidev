package com.example.pidev.entity.GestionSouvenir;

public enum CommandStatus {
    PENDING,    // Order waiting for validation
    CONFIRMED,  // Order confirmed by the user
    SHIPPED,    // Order shipped
    DELIVERED,  // Order delivered
    CANCELED    // Order canceled
}
