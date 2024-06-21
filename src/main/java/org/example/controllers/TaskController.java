// TaskController.java
package org.example.controllers;

import org.example.models.Task;
import org.example.services.FirestoreService;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TaskController {
    private final FirestoreService firestoreService;

    public TaskController(FirestoreService firestoreService) {
        this.firestoreService = firestoreService;
    }

    public CompletableFuture<Void> createTask(Task task) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        firestoreService.addTask("tasks", task,
                () -> future.complete(null),
                future::completeExceptionally);
        return future;
    }

    public CompletableFuture<List<Task>> getAllTasks() {
        CompletableFuture<List<Task>> future = new CompletableFuture<>();
        firestoreService.getTasks("tasks", future::complete, future::completeExceptionally);
        return future;
    }

    public CompletableFuture<Void> updateTask(Task task) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        firestoreService.updateTask("tasks", task.getId(), task,
                () -> future.complete(null),
                future::completeExceptionally);
        return future;
    }

    public CompletableFuture<List<Task>> getCompaniesByFestival(String collectionName, String selectedFestival) {
        CompletableFuture<List<Task>> future = new CompletableFuture<>();
        firestoreService.getCompaniesByFestival(collectionName, selectedFestival,
                future::complete,
                future::completeExceptionally);
        return future;
    }

    public CompletableFuture<List<String>> getFestivals() {
        CompletableFuture<List<String>> future = new CompletableFuture<>();
        firestoreService.getFestivals(future::complete, future::completeExceptionally);
        return future;
    }

    public CompletableFuture<Task> getCompanyById(String collectionName, String companyId) {
        CompletableFuture<Task> future = new CompletableFuture<>();
        firestoreService.getCompanyById(collectionName, companyId,
                future::complete,
                future::completeExceptionally);
        return future;
    }

    public CompletableFuture<Void> createCompany(String collectionName, Task company) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        firestoreService.createCompany(collectionName, company,
                () -> future.complete(null),
                future::completeExceptionally);
        return future;
    }

    public CompletableFuture<List<Task.Equipment>> getEquipmentList(String collectionName, String companyId) {
        CompletableFuture<List<Task.Equipment>> future = new CompletableFuture<>();
        firestoreService.getEquipmentList(collectionName, companyId,
                future::complete,
                future::completeExceptionally);
        return future;
    }

    public CompletableFuture<Void> updateEquipmentList(String collectionName, String companyId, List<Task.Equipment> equipmentList) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        firestoreService.updateEquipmentList(collectionName, companyId, equipmentList,
                () -> future.complete(null),
                future::completeExceptionally);
        return future;
    }
}