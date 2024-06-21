package org.example.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.example.models.Task;
import org.example.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class FirestoreService {
    private static final Logger logger = LoggerFactory.getLogger(FirestoreService.class);
    private static final int QUERY_LIMIT = 100;
    private final Firestore db;
    private User loggedInUser;

    public FirestoreService(Firestore db) {
        this.db = db;
    }

    public boolean isInitialized() {
        return db != null;
    }

    public void login(String username, String password, Consumer<Boolean> onSuccess, Consumer<Exception> onFailure) {
        if (db == null) {
            onFailure.accept(new IllegalStateException("Firestore is not initialized"));
            return;
        }

        CollectionReference usersCollection = db.collection("Users");
        Query query = usersCollection.whereEqualTo("username", username).whereEqualTo("password", password).limit(1);
        ApiFuture<QuerySnapshot> future = query.get();
        try {
            QuerySnapshot querySnapshot = future.get();
            if (!querySnapshot.isEmpty()) {
                loggedInUser = querySnapshot.getDocuments().get(0).toObject(User.class);
                onSuccess.accept(true);
            } else {
                onSuccess.accept(false);
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Failed to login user: {}", e.getMessage());
            onFailure.accept(e);
        }
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public String getLoggedInUser() {
        return loggedInUser != null ? loggedInUser.getUsername() : null;
    }

    public void logout() {
        loggedInUser = null;
    }

    public void addTask(String collection, Task task, Runnable onSuccess, Consumer<Exception> onFailure) {
        if (db == null) {
            onFailure.accept(new IllegalStateException("Firestore is not initialized"));
            return;
        }

        ApiFuture<DocumentReference> future = db.collection(collection).add(task);
        try {
            DocumentReference documentReference = future.get();
            logger.info("Task added successfully with ID: {}", documentReference.getId());
            onSuccess.run();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error adding task: {}", e.getMessage());
            onFailure.accept(e);
        }
    }

    public void updateTask(String collection, String id, Task task, Runnable onSuccess, Consumer<Exception> onFailure) {
        if (db == null) {
            onFailure.accept(new IllegalStateException("Firestore is not initialized"));
            return;
        }

        ApiFuture<WriteResult> future = db.collection(collection).document(id).set(task);
        try {
            future.get();
            logger.info("Task updated successfully");
            onSuccess.run();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error updating task: {}", e.getMessage());
            onFailure.accept(e);
        }
    }

    public void getTasks(String collection, Consumer<List<Task>> onSuccess, Consumer<Exception> onFailure) {
        if (db == null) {
            onFailure.accept(new IllegalStateException("Firestore is not initialized"));
            return;
        }

        ApiFuture<QuerySnapshot> future = db.collection(collection).limit(QUERY_LIMIT).get();
        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            List<Task> tasks = new ArrayList<>();
            for (QueryDocumentSnapshot document : documents) {
                tasks.add(document.toObject(Task.class));
            }
            onSuccess.accept(tasks);
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error getting tasks: {}", e.getMessage());
            onFailure.accept(e);
        }
    }

    public void getFestivals(Consumer<List<String>> onSuccess, Consumer<Exception> onFailure) {
        if (db == null) {
            onFailure.accept(new IllegalStateException("Firestore is not initialized"));
            return;
        }

        ApiFuture<QuerySnapshot> future = db.collection("Programs").limit(QUERY_LIMIT).get();
        try {
            QuerySnapshot querySnapshot = future.get();
            List<String> festivals = new ArrayList<>();
            for (QueryDocumentSnapshot document : querySnapshot) {
                String festivalName = document.getString("ProgramName");
                if (festivalName != null) {
                    festivals.add(festivalName);
                }
            }
            onSuccess.accept(festivals);
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error getting festivals: {}", e.getMessage());
            onFailure.accept(e);
        }
    }

    public void getCompaniesByFestival(String collectionName, String festivalName, Consumer<List<Task>> onSuccess, Consumer<Exception> onFailure) {
        if (db == null) {
            onFailure.accept(new IllegalStateException("Firestore is not initialized"));
            return;
        }

        ApiFuture<QuerySnapshot> future = db.collection(collectionName)
                .whereEqualTo("ProgramName", festivalName)
                .limit(QUERY_LIMIT)
                .get();
        try {
            QuerySnapshot querySnapshot = future.get();
            List<Task> companies = new ArrayList<>();
            for (QueryDocumentSnapshot document : querySnapshot) {
                companies.add(document.toObject(Task.class));
            }
            onSuccess.accept(companies);
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error getting companies by festival: {}", e.getMessage());
            onFailure.accept(e);
        }
    }

    public void getCompanyById(String collectionName, String companyId, Consumer<Task> onSuccess, Consumer<Exception> onFailure) {
        if (db == null) {
            onFailure.accept(new IllegalStateException("Firestore is not initialized"));
            return;
        }

        ApiFuture<DocumentSnapshot> future = db.collection(collectionName).document(companyId).get();
        try {
            DocumentSnapshot documentSnapshot = future.get();
            if (documentSnapshot.exists()) {
                Task company = documentSnapshot.toObject(Task.class);
                onSuccess.accept(company);
            } else {
                onFailure.accept(new Exception("Company not found"));
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error getting company by ID: {}", e.getMessage());
            onFailure.accept(e);
        }
    }

    public void createCompany(String collectionName, Task company, Runnable onSuccess, Consumer<Exception> onFailure) {
        if (db == null) {
            onFailure.accept(new IllegalStateException("Firestore is not initialized"));
            return;
        }

        ApiFuture<WriteResult> future = db.collection(collectionName).document(company.getId()).set(company);
        try {
            future.get();
            logger.info("Company created successfully");
            onSuccess.run();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error creating company: {}", e.getMessage());
            onFailure.accept(e);
        }
    }

    public void getEquipmentList(String collectionName, String companyId, Consumer<List<Task.Equipment>> onSuccess, Consumer<Exception> onFailure) {
        if (db == null) {
            onFailure.accept(new IllegalStateException("Firestore is not initialized"));
            return;
        }

        ApiFuture<DocumentSnapshot> future = db.collection(collectionName).document(companyId).get();
        try {
            DocumentSnapshot documentSnapshot = future.get();
            if (documentSnapshot.exists()) {
                Task company = documentSnapshot.toObject(Task.class);
                if (company != null && company.getEquipmentList() != null) {
                    onSuccess.accept(company.getEquipmentList());
                } else {
                    onSuccess.accept(new ArrayList<>());
                }
            } else {
                onFailure.accept(new Exception("Company not found"));
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error getting equipment list: {}", e.getMessage());
            onFailure.accept(e);
        }
    }

    public void updateEquipmentList(String collectionName, String companyId, List<Task.Equipment> equipmentList, Runnable onSuccess, Consumer<Exception> onFailure) {
        if (db == null) {
            onFailure.accept(new IllegalStateException("Firestore is not initialized"));
            return;
        }

        ApiFuture<WriteResult> future = db.collection(collectionName).document(companyId).update("equipmentList", equipmentList);
        try {
            future.get();
            logger.info("Equipment list updated successfully");
            onSuccess.run();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error updating equipment list: {}", e.getMessage());
            onFailure.accept(e);
        }
    }
}