package org.example.controllers;

import org.example.services.FirestoreService;

public class AuthController {
    private FirestoreService firestoreService;
    private String[] felhasználónevek;
    private String jelszó;

    public AuthController(FirestoreService firestoreService, String[] felhasználónevek, String jelszó) {
        if (firestoreService.isInitialized()) {
            this.firestoreService = firestoreService;
            // Perform Firestore-specific initialization here
        } else {
            this.firestoreService = null;
            System.err.println("FirestoreService is not initialized. AuthController will work without Firestore.");
        }
        this.felhasználónevek = felhasználónevek;
        this.jelszó = jelszó;
    }

    public boolean login(String felhasználónév, String jelszó) {
        if (firestoreService != null && firestoreService.isInitialized()) {
            // Check if the provided username and password are valid
            if (isValidCredentials(felhasználónév, jelszó)) {
                // Perform login logic using FirestoreService
                firestoreService.login(felhasználónév, jelszó);
                return true;
            }
        } else {
            System.out.println("FirestoreService is not initialized. AuthController will work without Firestore.");
            // Check if the provided username and password match the hardcoded values
            if (isValidCredentials(felhasználónév, jelszó)) {
                // Login successful (without Firestore)
                System.out.println("Login successful (offline mode).");
                return true;
            } else {
                // Invalid credentials
                return false;
            }
        }
        return false;
    }

    public boolean isLoggedIn() {
        return firestoreService.isLoggedIn();
    }

    public String getLoggedInUser() {
        return firestoreService.getLoggedInUser();
    }

    public void logout() {
        firestoreService.logout();
    }

    private boolean isValidCredentials(String felhasználónév, String jelszó) {
        // Check if the provided username and password match the hardcoded values
        for (String validFelhasználónév : felhasználónevek) {
            if (validFelhasználónév.equals(felhasználónév) && this.jelszó.equals(jelszó)) {
                return true;
            }
        }
        return false;
    }
}