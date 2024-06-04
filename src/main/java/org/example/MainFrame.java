package org.example;

import org.example.controllers.*;
import org.example.models.Task;
import org.example.views.*;
import org.example.services.FirestoreService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private TaskController taskController;
    private AuthController authController;
    private DashboardController dashboardController;
    private NotificationsController notificationsController;
    private UserManagementController userManagementController;
    private ReportsController reportsController;
    private SettingsController settingsController;
    private LoginView loginView;
    private JPanel mainView;
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
        mainView = new JPanel(cardLayout);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create and add views
        mainPanel.add(new DashboardView(dashboardController), "Dashboard");
        mainPanel.add(new NotificationsView(notificationsController), "Notifications");
        mainPanel.add(new ReportsView(reportsController), "Reports");
        mainPanel.add(new SettingsView(settingsController), "Settings");
        mainPanel.add(new TaskView(taskController), "Tasks");
        mainPanel.add(new UserManagementView(userManagementController), "UserManagement");

        add(loginView, BorderLayout.CENTER);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        JMenuItem dashboardMenuItem = new JMenuItem("Dashboard");
        dashboardMenuItem.addActionListener(e -> showView(new DashboardView(dashboardController)));

        JMenuItem loginMenuItem = new JMenuItem("Login");
        loginMenuItem.addActionListener(e -> showLoginView());

        JMenuItem notificationsMenuItem = new JMenuItem("Notifications");
        notificationsMenuItem.addActionListener(e -> showView(new NotificationsView(notificationsController)));

        JMenuItem reportsMenuItem = new JMenuItem("Reports");
        reportsMenuItem.addActionListener(e -> showView(new ReportsView(reportsController)));

        JMenuItem settingsMenuItem = new JMenuItem("Settings");
        settingsMenuItem.addActionListener(e -> showView(new SettingsView(settingsController)));

        JMenuItem tasksMenuItem = new JMenuItem("Tasks");
        tasksMenuItem.addActionListener(e -> showView(new TaskView(taskController)));

        JMenuItem userManagementMenuItem = new JMenuItem("User Management");
        userManagementMenuItem.addActionListener(e -> showView(new UserManagementView(userManagementController)));

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

    private void showView(JComponent view) {
        mainView.removeAll();
        mainView.add(view, BorderLayout.CENTER);
        mainView.revalidate();
        mainView.repaint();
    }

    public void showMainView() {
        if (mainView == null) {
            mainView = new JPanel(new BorderLayout());
            mainPanel.add(mainView, "Main");
        }

        // Remove the login view
        mainPanel.remove(loginView);

        // Show the main view
        cardLayout.show(mainPanel, "Main");

        // Initialize and add menu bar
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        JMenuItem dashboardMenuItem = new JMenuItem("Dashboard");
        dashboardMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showView(new DashboardView(dashboardController));
            }
        });
        menu.add(dashboardMenuItem);

        JMenuItem notificationsMenuItem = new JMenuItem("Notifications");
        notificationsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showView(new NotificationsView(notificationsController));
            }
        });
        menu.add(notificationsMenuItem);

        JMenuItem reportsMenuItem = new JMenuItem("Reports");
        reportsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showView(new ReportsView(reportsController));
            }
        });
        menu.add(reportsMenuItem);

        JMenuItem settingsMenuItem = new JMenuItem("Settings");
        settingsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showView(new SettingsView(settingsController));
            }
        });
        menu.add(settingsMenuItem);

        JMenuItem tasksMenuItem = new JMenuItem("Tasks");
        tasksMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showView(new TaskView(taskController));
            }
        });
        menu.add(tasksMenuItem);

        JMenuItem userManagementMenuItem = new JMenuItem("User Management");
        userManagementMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showView(new UserManagementView(userManagementController));
            }
        });
        menu.add(userManagementMenuItem);

        JMenuItem logoutMenuItem = new JMenuItem("Logout");
        logoutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        menu.add(logoutMenuItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);

        revalidate();
        repaint();
    }

    private void logout() {
        authController.logout();
        showLoginView();
    }

    public void showLoginView() {
        getContentPane().removeAll();
        getContentPane().add(loginView, BorderLayout.CENTER);
        setJMenuBar(null); // Remove the menu bar
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainFrame ex = new MainFrame();
            ex.setVisible(true);
        });
    }
}
