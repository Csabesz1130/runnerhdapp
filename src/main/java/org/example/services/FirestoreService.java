package org.example.services;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.example.models.Task;

public class FirestoreService {
    private final Firestore db;
    private String loggedInUser;

    public FirestoreService() {
        // Initialize Firestore
        db = FirestoreClient.getFirestore();
    }

    public void addTask(String collection, Task task) {
        // Add task to Firestore
        CollectionReference tasksCollection = db.collection(collection);
        tasksCollection.add(task);
    }

    public void updateTask(String collection, String id, Task task) {
        // Update task in Firestore
        DocumentReference taskDocument = db.collection(collection).document(id);
        taskDocument.set(task);
    }

    public void login(String felhasználónév) {
        // Perform login logic using Firestore
        // Example: Update user's login status in Firestore
        CollectionReference usersCollection = db.collection("users");
        DocumentReference userDocument = usersCollection.document(felhasználónév);
        userDocument.update("loggedIn", true);
        loggedInUser = felhasználónév;
    }

    public boolean isLoggedIn() {
        // Check if a user is currently logged in using Firestore
        // Example: Retrieve user's login status from Firestore
        return loggedInUser != null;
    }

    public String getLoggedInUser() {
        // Retrieve the currently logged-in user from Firestore
        return loggedInUser;
    }

    public void logout() {
        // Perform logout logic using Firestore
        // Example: Update user's login status in Firestore
        if (loggedInUser != null) {
            CollectionReference usersCollection = db.collection("users");
            DocumentReference userDocument = usersCollection.document(loggedInUser);
            userDocument.update("loggedIn", false);
            loggedInUser = null;
        }
    }

    public boolean isInitialized() {
        return this.db != null;
    }
}