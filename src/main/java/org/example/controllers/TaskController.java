package org.example.controllers;

import org.example.models.Task;
import org.example.services.FirestoreService;

public class TaskController {
    private FirestoreService firestoreService;

    public TaskController(FirestoreService firestoreService) {
        this.firestoreService = firestoreService;
    }

    // Example method
    public void createTask(Task task) {
        firestoreService.addTask("tasks", task);
    }

    public void frissitFeladat(Task task, String ujStatusz, String megjegyzes) {
        // 1. Update the task object
        task.setStatusz(ujStatusz);
        task.setDescription(megjegyzes); // Assuming you have a 'megjegyzes' field in the Task model

        // 2. Call FirestoreService to update the task in Firebase
        firestoreService.updateTask("tasks", task.getId(), task); // Assuming 'tasks' is the collection name in Firestore
    }
}
