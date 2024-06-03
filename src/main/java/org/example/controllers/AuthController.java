package org.example.controllers;

import org.example.services.FirestoreService;

public class AuthController {
    private FirestoreService firestoreService;

    public AuthController() {

    }

    public AuthController(FirestoreService firestoreService) {
        this.firestoreService = firestoreService;
    }

    // Example method
    public void authenticate(String user, String password) {
        // Authentication logic
    }
}
