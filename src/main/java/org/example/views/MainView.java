package org.example.views;

import org.example.MainFrame;
import org.example.controllers.AuthController;
import org.example.controllers.TaskController;
import org.example.models.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainView extends JPanel {
    private final AuthController authController;
    private final MainFrame mainFrame;
    private final TaskController taskController;
    private JTable taskTable;
    private DefaultTableModel tableModel;

    public MainView(AuthController authController, MainFrame mainFrame, TaskController taskController) {
        this.authController = authController;
        this.mainFrame = mainFrame;
        this.taskController = taskController;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Create the task table
        tableModel = new DefaultTableModel(new Object[]{"ID", "Telephely kód", "Telephely név", "Státusz", "Megjegyzés"}, 0);
        taskTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(taskTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setTasks(List<Task> tasks) {
        // Clear existing data from the table model
        tableModel.setRowCount(0);

        // Add tasks to the table model
        for (Task task : tasks) {
            Object[] rowData = {
                    task.getId(),
                    task.getTelephelyKod(),
                    task.getTelephelyNev(),
                    task.getStatusz(),
                    task.getMegjegyzes()
            };
            tableModel.addRow(rowData);
        }
    }
}