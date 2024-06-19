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

    public TaskController(FirestoreService firestoreService) {
        this.firestoreService = firestoreService;
    }

    public void createTask(Task task) {
        firestoreService.addTask("tasks", task);
    }

    public List<Task> getAllTasks() {
        return firestoreService.getTasks("tasks");
    }

    public void updateTask(Task task, String newStatus, String notes) {
        // Update the task object
        // Modify the field names according to your Task model
        // task.setStatus(newStatus);
        // task.setNotes(notes);

        // Call FirestoreService to update the task in Firebase
        firestoreService.updateTask("tasks", task.getId(), task);
    }

    public List<Task> getCompaniesByFestival(String collectionName, String selectedFestival) {
        return firestoreService.getCompaniesByFestival(collectionName, selectedFestival);
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
}