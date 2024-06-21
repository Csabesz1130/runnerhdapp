package org.example.views;

import org.example.MainFrame;
import org.example.controllers.AuthController;
import org.example.controllers.TaskController;
import org.example.models.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MainView extends JPanel {
    private final AuthController authController;
    private final MainFrame mainFrame;
    private final TaskController taskController;

    private JComboBox<String> festivalComboBox;
    private JRadioButton installationRadioButton;
    private JRadioButton demolitionRadioButton;
    private JTable companyTable;
    private DefaultTableModel companyTableModel;
    private JButton logoutButton;

    public MainView(AuthController authController, MainFrame mainFrame, TaskController taskController) {
        this.authController = authController;
        this.mainFrame = mainFrame;
        this.taskController = taskController;
        initComponents();
        loadFestivals();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel festivalLabel = new JLabel("Festival:");
        topPanel.add(festivalLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        festivalComboBox = new JComboBox<>();
        festivalComboBox.addActionListener(e -> fetchCompanies());
        topPanel.add(festivalComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        installationRadioButton = new JRadioButton("Company_Install");
        installationRadioButton.addActionListener(e -> fetchCompanies());
        topPanel.add(installationRadioButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        demolitionRadioButton = new JRadioButton("Company_Demolition");
        demolitionRadioButton.addActionListener(e -> fetchCompanies());
        topPanel.add(demolitionRadioButton, gbc);

        ButtonGroup choiceButtonGroup = new ButtonGroup();
        choiceButtonGroup.add(installationRadioButton);
        choiceButtonGroup.add(demolitionRadioButton);

        add(topPanel, BorderLayout.NORTH);

        companyTableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Last Modified", "Program"}, 0);
        companyTable = new JTable(companyTableModel);
        JScrollPane companyScrollPane = new JScrollPane(companyTable);
        add(companyScrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        bottomPanel.add(logoutButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadFestivals() {
        taskController.getFestivals().thenAccept(festivals -> {
            SwingUtilities.invokeLater(() -> {
                festivalComboBox.removeAllItems();
                for (String festival : festivals) {
                    festivalComboBox.addItem(festival);
                }
                if (!festivals.isEmpty()) {
                    fetchCompanies();
                }
            });
        }).exceptionally(ex -> {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this, "Failed to load festivals: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            });
            return null;
        });
    }

    public String getSelectedCollectionName() {
        return installationRadioButton.isSelected() ? "Company_Install" : "Company_Demolition";
    }

    public void setTasks(List<Task> companies) {
        SwingUtilities.invokeLater(() -> {
            companyTableModel.setRowCount(0);
            for (Task company : companies) {
                Object[] rowData = {company.getId(), company.getCompanyName(), company.getLastModified(), company.getProgramName()};
                companyTableModel.addRow(rowData);
            }
        });
    }

    private void fetchCompanies() {
        String selectedFestival = (String) festivalComboBox.getSelectedItem();
        String collectionName = getSelectedCollectionName();
        taskController.getCompaniesByFestival(collectionName, selectedFestival)
                .thenAccept(this::setTasks)
                .exceptionally(ex -> {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, "Failed to fetch companies: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    });
                    return null;
                });
    }

    private void logout() {
        authController.logout();
        mainFrame.showLoginView();
    }
}