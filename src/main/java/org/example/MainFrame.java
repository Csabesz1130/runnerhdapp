package org.example;

import org.example.controllers.*;
import org.example.models.Task;
import org.example.views.*;
import org.example.services.FirestoreService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private TaskController taskController;
    private AuthController authController;
    private DashboardController dashboardController;
    private NotificationsController notificationsController;
    private UserManagementController userManagementController;
    private ReportsController reportsController;
    private SettingsController settingsController;
    private LoginView loginView;
    private MainView mainView;
    private JMenuBar menuBar;

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame() {
        FirestoreService firestoreService = new FirestoreService();
        this.authController = new AuthController(firestoreService, LoginView.FELHASZNÁLÓNEVEK, LoginView.JELSZÓ);
        this.taskController = new TaskController(firestoreService);

        initUI();
    }

    private void initUI() {
        setTitle("RunnerHDApp");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        loginView = new LoginView(this);
        mainView = new MainView(authController, this, taskController);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create and add views
        mainPanel.add(loginView, "Login");
        mainPanel.add(mainView, "MainView");
        mainPanel.add(new DashboardView(dashboardController), "Dashboard");
        mainPanel.add(new NotificationsView(notificationsController), "Notifications");
        mainPanel.add(new ReportsView(reportsController), "Reports");
        mainPanel.add(new SettingsView(settingsController), "Settings");
        mainPanel.add(new TaskView(taskController, new Task()), "Tasks");
        mainPanel.add(new UserManagementView(userManagementController), "UserManagement");

        add(mainPanel, BorderLayout.CENTER);
    }

    public void showView(String viewName) {
        cardLayout.show(mainPanel, viewName);
    }

    public void showMainView() {
        showView("MainView");
        loadTasksFromFirestore();
        initMenuBar();
    }

    private void loadTasksFromFirestore() {
        try {
            List<Task> tasks = taskController.getAllTasks();
            mainView.setTasks(tasks);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load tasks from Firestore: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initMenuBar() {
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        JMenuItem dataMenuItem = new JMenuItem("Data1");
        dataMenuItem.addActionListener(e -> showView("MainView"));
        menu.add(dataMenuItem);

        JMenuItem dashboardMenuItem = new JMenuItem("Dashboard");
        dashboardMenuItem.addActionListener(e -> showView("Dashboard"));
        menu.add(dashboardMenuItem);

        JMenuItem notificationsMenuItem = new JMenuItem("Notifications");
        notificationsMenuItem.addActionListener(e -> showView("Notifications"));
        menu.add(notificationsMenuItem);

        JMenuItem reportsMenuItem = new JMenuItem("Reports");
        reportsMenuItem.addActionListener(e -> showView("Reports"));
        menu.add(reportsMenuItem);

        JMenuItem settingsMenuItem = new JMenuItem("Settings");
        settingsMenuItem.addActionListener(e -> showView("Settings"));
        menu.add(settingsMenuItem);

        JMenuItem tasksMenuItem = new JMenuItem("Tasks");
        tasksMenuItem.addActionListener(e -> showView("Tasks"));
        menu.add(tasksMenuItem);

        JMenuItem userManagementMenuItem = new JMenuItem("User Management");
        userManagementMenuItem.addActionListener(e -> showView("UserManagement"));
        menu.add(userManagementMenuItem);

        JMenuItem logoutMenuItem = new JMenuItem("Logout");
        logoutMenuItem.addActionListener(e -> logout());
        menu.add(logoutMenuItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    private void logout() {
        authController.logout();
        setJMenuBar(null);
        showView("Login");
    }

    public void showLoginView() {
        showView("Login");
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainFrame ex = new MainFrame();
            ex.setVisible(true);
        });
    }
}