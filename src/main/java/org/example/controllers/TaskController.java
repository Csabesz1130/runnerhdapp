package org.example.controllers;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.example.models.Task;
import org.example.services.FirestoreService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TaskController {
    private final FirestoreService firestoreService;
    private final Firestore db;

    public TaskController(FirestoreService firestoreService) {
        this.firestoreService = firestoreService;
        this.db = firestoreService.getFirestore();
    }

    public void createTask(Task task) {
        firestoreService.addTask("tasks", task);
    }

    public List<Task> getAllTasks() {
        return firestoreService.getTasks("tasks");
    }

    public void frissitFeladat(Task task, String ujStatusz, String megjegyzes) {
        // Update the task object
        task.setStatusz(ujStatusz);
        task.setMegjegyzes(megjegyzes);

        // Call FirestoreService to update the task in Firebase
        firestoreService.updateTask("tasks", task.getId(), task);
    }

    public List<String> getFestivals() {
        return firestoreService.getFestivals();
    }

    public Task getCompanyById(String collectionName, String companyId) {
        return firestoreService.getCompanyById(collectionName, companyId);
    }

    public void createCompany(String collectionName, Task company) {
        firestoreService.createCompany(collectionName, company);
    }

    public List<Task.Equipment> getEquipmentList(String collectionName, String companyId) {
        return firestoreService.getEquipmentList(collectionName, companyId);
    }

    public void updateEquipmentList(String collectionName, String companyId, List<Task.Equipment> equipmentList) {
        firestoreService.updateEquipmentList(collectionName, companyId, equipmentList);
    }

    public List<Task> getCompaniesByFestival(String collectionName, String selectedFestival) {
        List<Task> companies = new ArrayList<>();
        if (db != null) {
            CollectionReference companiesCollection = db.collection(collectionName);
            Query query = companiesCollection.whereEqualTo("programName", selectedFestival);
            ApiFuture<QuerySnapshot> future = query.get();
            try {
                List<QueryDocumentSnapshot> documents = future.get().getDocuments();
                for (QueryDocumentSnapshot document : documents) {
                    Task company = document.toObject(Task.class);
                    companies.add(company);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                System.err.println("Failed to retrieve companies: " + e.getMessage());
            }
        } else {
            System.err.println("Firestore is not initialized. Cannot retrieve companies.");
        }
        return companies;
    }
}