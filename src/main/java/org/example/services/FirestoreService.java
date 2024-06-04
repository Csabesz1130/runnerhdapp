package org.example.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.example.models.Task;

import java.io.FileInputStream;
import java.io.IOException;

public class FirestoreService {
    private final Firestore db;
    private String loggedInUser;

    public FirestoreService() {
        Firestore firestore = null;
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                FileInputStream serviceAccount = new FileInputStream("main/resources/google-services.json");

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
            }
            firestore = FirestoreClient.getFirestore();
        } catch (IOException e) {
            e.printStackTrace();

        } catch (IllegalStateException e) {
            e.printStackTrace();
            firestore = FirestoreClient.getFirestore();
        } finally {
            this.db = firestore;
        }
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