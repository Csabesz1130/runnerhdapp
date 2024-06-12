package org.example.services;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.example.models.Task;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FirestoreService {
    private final Firestore db;
    private String loggedInUser;

    public FirestoreService() {
        Firestore firestore = null;
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                FileInputStream serviceAccount = new FileInputStream("src/main/resources/google-services.json");

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
        try {
            db.collection(collection).add(task);
            System.out.println("Task added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to add task: " + e.getMessage());
        }
    }

    public void updateTask(String collection, String id, Task task) {
        try {
            db.collection(collection).document(id).set(task);
            System.out.println("Task updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to update task: " + e.getMessage());
        }
    }

    public List<Task> getTasks(String collection) {
        List<Task> tasks = new ArrayList<>();
        CollectionReference tasksCollection = db.collection(collection);
        ApiFuture<QuerySnapshot> future = tasksCollection.get();
        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                Task task = document.toObject(Task.class);
                tasks.add(task);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve tasks: " + e.getMessage());
        }
        return tasks;
    }

    public void registerUser(String username, String password) {
        CollectionReference users = db.collection("users");
        DocumentReference newUser = users.document(username);
        try {
            newUser.set(new User(username, password, false));
            System.out.println("User registered successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to register user: " + e.getMessage());
        }
    }

    public boolean authenticateUser(String username, String password) {
        CollectionReference users = db.collection("users");
        try {
            QuerySnapshot snapshot = users.whereEqualTo("username", username)
                    .whereEqualTo("password", password)
                    .get().get();
            return !snapshot.isEmpty();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            System.err.println("Failed to authenticate user: " + e.getMessage());
            return false;
        }
    }

    public void login(String username) {
        try {
            db.collection("users").document(username).update("loggedIn", true);
            loggedInUser = username;
            System.out.println("User logged in successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to log in user: " + e.getMessage());
        }
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void logout() {
        if (loggedInUser != null) {
            try {
                db.collection("users").document(loggedInUser).update("loggedIn", false);
                loggedInUser = null;
                System.out.println("User logged out successfully.");
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Failed to log out user: " + e.getMessage());
            }
        }
    }

    public boolean isInitialized() {
        return this.db != null;
    }

    public void listenForTaskChanges(String collection) {
        CollectionReference tasksCollection = db.collection(collection);
        tasksCollection.addSnapshotListener((snapshot, error) -> {
            if (error != null) {
                System.err.println("Error listening for task changes: " + error.getMessage());
                return;
            }

            for (DocumentChange change : snapshot.getDocumentChanges()) {
                switch (change.getType()) {
                    case ADDED:
                        Task newTask = change.getDocument().toObject(Task.class);
                        System.out.println("New task added: " + newTask);
                        break;
                    case MODIFIED:
                        Task modifiedTask = change.getDocument().toObject(Task.class);
                        System.out.println("Task modified: " + modifiedTask);
                        break;
                    case REMOVED:
                        System.out.println("Task removed: " + change.getDocument().getId());
                        break;
                }
            }
        });
    }

    static class User {
        String username;
        String password;
        boolean loggedIn;

        User(String username, String password, boolean loggedIn) {
            this.username = username;
            this.password = password;
            this.loggedIn = loggedIn;
        }
    }
}
