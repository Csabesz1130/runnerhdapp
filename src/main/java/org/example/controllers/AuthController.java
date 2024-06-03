package org.example.controllers;

import org.example.services.FirestoreService;

public class AuthController {
    private FirestoreService firestoreService;
    private String[] felhasználónevek;
    private String jelszó;

    public AuthController(FirestoreService firestoreService, String[] felhasználónevek, String jelszó) {
        this.firestoreService = firestoreService;
        this.felhasználónevek = felhasználónevek;
        this.jelszó = jelszó;
    }

    public boolean login(String felhasználónév, String jelszó) {
        // Check if the provided username and password are valid
        if (isValidCredentials(felhasználónév, jelszó)) {
            // Perform login logic using FirestoreService
            firestoreService.login(felhasználónév);
            return true;
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