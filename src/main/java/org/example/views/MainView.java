package org.example.views;

import org.example.MainFrame;
import org.example.controllers.AuthController;
import org.example.controllers.TaskController;
import org.example.models.Task;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainView extends JPanel {
    private final AuthController authController;
    private final MainFrame mainFrame;
    private final TaskController taskController;

    private JComboBox<String> festivalComboBox;
    private JRadioButton installationRadioButton;
    private JRadioButton demolitionRadioButton;
    private JButton fetchCompaniesButton;
    private JTable companyTable;
    private DefaultTableModel companyTableModel;
    private JTable equipmentTable;
    private DefaultTableModel equipmentTableModel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton saveButton;
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
        festivalComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchCompanies();
            }
        });
        topPanel.add(festivalComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        installationRadioButton = new JRadioButton("Installation");
        installationRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchCompanies();
            }
        });
        topPanel.add(installationRadioButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        demolitionRadioButton = new JRadioButton("Demolition");
        demolitionRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchCompanies();
            }
        });
        topPanel.add(demolitionRadioButton, gbc);

        ButtonGroup choiceButtonGroup = new ButtonGroup();
        choiceButtonGroup.add(installationRadioButton);
        choiceButtonGroup.add(demolitionRadioButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        fetchCompaniesButton = new JButton("Fetch Companies");
        fetchCompaniesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchCompanies();
            }
        });
        topPanel.add(fetchCompaniesButton, gbc);

        add(topPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();

        companyTableModel = new DefaultTableModel(new Object[]{"ID", "Name"}, 0);
        companyTable = new JTable(companyTableModel);
        companyTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = companyTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String companyId = (String) companyTableModel.getValueAt(selectedRow, 0);
                        loadEquipmentList(companyId);
                    }
                }
            }
        });
        JScrollPane companyScrollPane = new JScrollPane(companyTable);
        tabbedPane.addTab("Companies", companyScrollPane);

        equipmentTableModel = new DefaultTableModel(new Object[]{"SN/DID", "Type", "Model", "Status"}, 0);
        equipmentTable = new JTable(equipmentTableModel);
        JScrollPane equipmentScrollPane = new JScrollPane(equipmentTable);

        JPanel equipmentButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEquipment();
            }
        });
        equipmentButtonPanel.add(addButton);

        editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editEquipment();
            }
        });
        equipmentButtonPanel.add(editButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEquipment();
            }
        });
        equipmentButtonPanel.add(deleteButton);

        JPanel equipmentPanel = new JPanel(new BorderLayout());
        equipmentPanel.add(equipmentScrollPane, BorderLayout.CENTER);
        equipmentPanel.add(equipmentButtonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Equipment", equipmentPanel);

        add(tabbedPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEquipmentList();
            }
        });
        bottomPanel.add(saveButton, BorderLayout.WEST);

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        bottomPanel.add(logoutButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadFestivals() {
        List<String> festivals = taskController.getFestivals();
        festivalComboBox.removeAllItems();
        for (String festival : festivals) {
            festivalComboBox.addItem(festival);
        }
        if (!festivals.isEmpty()) {
            fetchCompanies();
        }
    }

    private void fetchCompanies() {
        String selectedFestival = (String) festivalComboBox.getSelectedItem();
        boolean isInstallation = installationRadioButton.isSelected();
        String collectionName = isInstallation ? "Company_Install" : "Company_Demolition";
        List<Task> companies = taskController.getCompaniesByFestival(collectionName, selectedFestival);
        setTasks(companies);
    }

    public void setTasks(List<Task> tasks) {
        companyTableModel.setRowCount(0);
        for (Task task : tasks) {
            Object[] rowData = {task.getId(), task.getCompanyName()};
            companyTableModel.addRow(rowData);
        }
    }

    private void loadEquipmentList(String companyId) {
        String collectionName = installationRadioButton.isSelected() ? "Company_Install" : "Company_Demolition";
        List<Task.Equipment> equipmentList = taskController.getEquipmentList(collectionName, companyId);
        equipmentTableModel.setRowCount(0);
        for (Task.Equipment equipment : equipmentList) {
            Object[] rowData = {equipment.getSnDid(), equipment.getType(), equipment.getModel(), equipment.getStatus()};
            equipmentTableModel.addRow(rowData);
        }
    }

    private void addEquipment() {
        JTextField snDidField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField modelField = new JTextField();
        JTextField statusField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("SN/DID:"));
        panel.add(snDidField);
        panel.add(new JLabel("Type:"));
        panel.add(typeField);
        panel.add(new JLabel("Model:"));
        panel.add(modelField);
        panel.add(new JLabel("Status:"));
        panel.add(statusField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Equipment", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String snDid = snDidField.getText().trim();
            String type = typeField.getText().trim();
            String model = modelField.getText().trim();
            String status = statusField.getText().trim();

            if (!snDid.isEmpty() && !type.isEmpty() && !model.isEmpty() && !status.isEmpty()) {
                Task.Equipment equipment = new Task.Equipment(snDid, type, model, status);
                Object[] rowData = {snDid, type, model, status};
                equipmentTableModel.addRow(rowData);
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editEquipment() {
        int selectedRow = equipmentTable.getSelectedRow();
        if (selectedRow != -1) {
            String snDid = (String) equipmentTableModel.getValueAt(selectedRow, 0);
            String type = (String) equipmentTableModel.getValueAt(selectedRow, 1);
            String model = (String) equipmentTableModel.getValueAt(selectedRow, 2);
            String status = (String) equipmentTableModel.getValueAt(selectedRow, 3);

            JTextField snDidField = new JTextField(snDid);
            JTextField typeField = new JTextField(type);
            JTextField modelField = new JTextField(model);
            JTextField statusField = new JTextField(status);

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("SN/DID:"));
            panel.add(snDidField);
            panel.add(new JLabel("Type:"));
            panel.add(typeField);
            panel.add(new JLabel("Model:"));
            panel.add(modelField);
            panel.add(new JLabel("Status:"));
            panel.add(statusField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Edit Equipment", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String newSnDid = snDidField.getText().trim();
                String newType = typeField.getText().trim();
                String newModel = modelField.getText().trim();
                String newStatus = statusField.getText().trim();

                if (!newSnDid.isEmpty() && !newType.isEmpty() && !newModel.isEmpty() && !newStatus.isEmpty()) {
                    equipmentTableModel.setValueAt(newSnDid, selectedRow, 0);
                    equipmentTableModel.setValueAt(newType, selectedRow, 1);
                    equipmentTableModel.setValueAt(newModel, selectedRow, 2);
                    equipmentTableModel.setValueAt(newStatus, selectedRow, 3);
                } else {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an equipment item to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteEquipment() {
        int selectedRow = equipmentTable.getSelectedRow();
        if (selectedRow != -1) {
            equipmentTableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an equipment item to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveEquipmentList() {
        String collectionName = installationRadioButton.isSelected() ? "Company_Install" : "Company_Demolition";
        int selectedCompanyRow = companyTable.getSelectedRow();
        if (selectedCompanyRow != -1) {
            String companyId = (String) companyTableModel.getValueAt(selectedCompanyRow, 0);
            List<Task.Equipment> equipmentList = getEquipmentListFromTable();
            taskController.updateEquipmentList(collectionName, companyId, equipmentList);
            JOptionPane.showMessageDialog(this, "Equipment list saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a company to save the equipment list.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private List<Task.Equipment> getEquipmentListFromTable() {
        List<Task.Equipment> equipmentList = new ArrayList<>();
        for (int i = 0; i < equipmentTableModel.getRowCount(); i++) {
            String snDid = (String) equipmentTableModel.getValueAt(i, 0);
            String type = (String) equipmentTableModel.getValueAt(i, 1);
            String model = (String) equipmentTableModel.getValueAt(i, 2);
            String status = (String) equipmentTableModel.getValueAt(i, 3);
            Task.Equipment equipment = new Task.Equipment(snDid, type, model, status);
            equipmentList.add(equipment);
        }
        return equipmentList;
    }

    private void logout() {
        authController.logout();
        mainFrame.showLoginView();
    }

    public void clearTasks() {
        companyTableModel.setRowCount(0);
        equipmentTableModel.setRowCount(0);
    }
}