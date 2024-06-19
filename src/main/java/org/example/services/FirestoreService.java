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

public class FirestoreService {
    private static final Logger logger = LoggerFactory.getLogger(FirestoreService.class);
    private final Firestore db;
    private User loggedInUser;

    public FirestoreService(Firestore db) {
        this.db = db;
    }

    public boolean isInitialized() {
        return db != null;
    }

    public boolean login(String username, String password) {
        if (db != null) {
            CollectionReference usersCollection = db.collection("Users");
            Query query = usersCollection.whereEqualTo("username", username).whereEqualTo("password", password);
            ApiFuture<QuerySnapshot> future = query.get();
            try {
                QuerySnapshot snapshot = future.get();
                if (!snapshot.isEmpty()) {
                    loggedInUser = snapshot.getDocuments().get(0).toObject(User.class);
                    return true;
                }
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Failed to login user: {}", e.getMessage());
            }
        } else {
            logger.error("Firestore is not initialized. Cannot login user.");
        }
        return false;
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public String getLoggedInUser() {
        if (loggedInUser != null) {
            return loggedInUser.getUsername();
        }
        return null;
    }

    public void logout() {
        loggedInUser = null;
    }

    public void addTask(String collection, Task task) {
        if (db != null) {
            db.collection(collection).add(task).addListener(() -> logger.info("Task added successfully."), Runnable::run);
        } else {
            logger.error("Firestore is not initialized. Cannot add task.");
        }
    }

    public void updateTask(String collection, String id, Task task) {
        if (db != null) {
            db.collection(collection).document(id).set(task).addListener(() -> logger.info("Task updated successfully."), Runnable::run);
        } else {
            logger.error("Firestore is not initialized. Cannot update task.");
        }
    }

    public List<Task> getTasks(String collection) {
        List<Task> tasks = new ArrayList<>();
        if (db != null) {
            CollectionReference tasksCollection = db.collection(collection);
            ApiFuture<QuerySnapshot> future = tasksCollection.get();
            try {
                List<QueryDocumentSnapshot> documents = future.get().getDocuments();
                for (QueryDocumentSnapshot document : documents) {
                    Task task = document.toObject(Task.class);
                    tasks.add(task);
                }
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Failed to retrieve tasks: {}", e.getMessage());
            }
        } else {
            logger.error("Firestore is not initialized. Cannot retrieve tasks.");
        }
        return tasks;
    }

    public List<String> getFestivals() {
        List<String> festivals = new ArrayList<>();
        if (db != null) {
            CollectionReference programsCollection = db.collection("Programs");
            try {
                QuerySnapshot querySnapshot = programsCollection.get().get();
                for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
                    String festivalName = document.getString("ProgramName");
                    festivals.add(festivalName);
                }
            } catch (Exception e) {
                logger.error("Error retrieving festivals: {}", e.getMessage());
            }
        } else {
            logger.error("Firestore is not initialized. Cannot retrieve festivals.");
        }
        return festivals;
    }

    public List<Task> getCompaniesByFestival(String collectionName, String festivalName) {
        List<Task> companies = new ArrayList<>();
        if (db != null) {
            CollectionReference companiesCollection = db.collection(collectionName);
            Query query = companiesCollection.whereEqualTo("ProgramName", festivalName);
            ApiFuture<QuerySnapshot> future = query.get();
            try {
                List<QueryDocumentSnapshot> documents = future.get().getDocuments();
                for (QueryDocumentSnapshot document : documents) {
                    Task company = document.toObject(Task.class);
                    companies.add(company);
                }
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Failed to retrieve companies: {}", e.getMessage());
            }
        } else {
            logger.error("Firestore is not initialized. Cannot retrieve companies.");
        }
        return companies;
    }

    public Task getCompanyById(String collectionName, String companyId) {
        if (db != null) {
            DocumentReference companyDocument = db.collection(collectionName).document(companyId);
            try {
                DocumentSnapshot documentSnapshot = companyDocument.get().get();
                if (documentSnapshot.exists()) {
                    return documentSnapshot.toObject(Task.class);
                }
            } catch (Exception e) {
                logger.error("Error retrieving company: {}", e.getMessage());
            }
        } else {
            logger.error("Firestore is not initialized. Cannot retrieve company.");
        }
        return null;
    }

    public void createCompany(String collectionName, Task company) {
        if (db != null) {
            db.collection(collectionName).document(company.getId()).set(company).addListener(() -> logger.info("Company created successfully."), Runnable::run);
        } else {
            logger.error("Firestore is not initialized. Cannot create company.");
        }
    }

    public List<Task.Equipment> getEquipmentList(String collectionName, String companyId) {
        List<Task.Equipment> equipmentList = new ArrayList<>();
        if (db != null) {
            DocumentReference companyDocument = db.collection(collectionName).document(companyId);
            try {
                DocumentSnapshot documentSnapshot = companyDocument.get().get();
                if (documentSnapshot.exists()) {
                    Task company = documentSnapshot.toObject(Task.class);
                    if (company != null) {
                        equipmentList = company.getEquipmentList();
                    }
                }
            } catch (Exception e) {
                logger.error("Error retrieving equipment list: {}", e.getMessage());
            }
        } else {
            logger.error("Firestore is not initialized. Cannot retrieve equipment list.");
        }
        return equipmentList;
    }

    public void updateEquipmentList(String collectionName, String companyId, List<Task.Equipment> equipmentList) {
        if (db != null) {
            db.collection(collectionName).document(companyId).update("equipmentList", equipmentList)
                    .addListener(() -> logger.info("Equipment list updated successfully."), Runnable::run);
        } else {
            logger.error("Firestore is not initialized. Cannot update equipment list.");
        }
    }
}