package org.example;

import org.example.controllers.*;
import org.example.models.Task;
import org.example.views.*;
import org.example.services.FirestoreService;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private TaskController taskController;
    private AuthController authController;
    private DashboardController dashboardController;
    private NotificationsController notificationsController;
    private UserManagementController userManagementController;
    private ReportsController reportsController;
    private SettingsController settingsController;

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame() {
        FirestoreService firestoreService = new FirestoreService();
        this.authController = new AuthController();
        this.taskController = new TaskController(firestoreService);


        initUI();
    }

    private void initUI() {
        setTitle("RunnerHDApp");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create and add views
        mainPanel.add(new DashboardView(dashboardController), "Dashboard");
        mainPanel.add(new LoginView(authController), "Login");
        mainPanel.add(new NotificationsView(notificationsController), "Notifications");
        mainPanel.add(new ReportsView(reportsController), "Reports");
        mainPanel.add(new SettingsView(settingsController), "Settings");
        mainPanel.add(new TaskView(taskController, new Task()), "Tasks");
        mainPanel.add(new UserManagementView(userManagementController), "UserManagement");

        add(mainPanel, BorderLayout.CENTER);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        JMenuItem dashboardMenuItem = new JMenuItem("Dashboard");
        dashboardMenuItem.addActionListener(e -> showView("Dashboard"));

        JMenuItem loginMenuItem = new JMenuItem("Login");
        loginMenuItem.addActionListener(e -> showView("Login"));

        JMenuItem notificationsMenuItem = new JMenuItem("Notifications");
        notificationsMenuItem.addActionListener(e -> showView("Notifications"));

        JMenuItem reportsMenuItem = new JMenuItem("Reports");
        reportsMenuItem.addActionListener(e -> showView("Reports"));

        JMenuItem settingsMenuItem = new JMenuItem("Settings");
        settingsMenuItem.addActionListener(e -> showView("Settings"));

        JMenuItem tasksMenuItem = new JMenuItem("Tasks");
        tasksMenuItem.addActionListener(e -> showView("Tasks"));

        JMenuItem userManagementMenuItem = new JMenuItem("User Management");
        userManagementMenuItem.addActionListener(e -> showView("UserManagement"));

        menu.add(dashboardMenuItem);
        menu.add(loginMenuItem);
        menu.add(notificationsMenuItem);
        menu.add(reportsMenuItem);
        menu.add(settingsMenuItem);
        menu.add(tasksMenuItem);
        menu.add(userManagementMenuItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    private void showView(String viewName) {
        cardLayout.show(mainPanel, viewName);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainFrame ex = new MainFrame();
            ex.setVisible(true);
        });
    }
}
