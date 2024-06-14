package org.example.controllers;

import org.example.models.Task;
import org.example.services.FirestoreService;

import java.util.List;

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
}