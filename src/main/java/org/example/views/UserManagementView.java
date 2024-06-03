package org.example.views;

import org.example.controllers.UserManagementController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserManagementView extends JPanel {
    private UserManagementController userManagementController;
    private JTable felhasználókTábla;
    private DefaultTableModel felhasználókTáblaModel;

    public UserManagementView(UserManagementController userManagementController) {
        this.userManagementController = userManagementController;

        felhasználókTáblaModel = new DefaultTableModel(new Object[]{"ID", "Név", "Szerepkör"}, 0);
        felhasználókTábla = new JTable(felhasználókTáblaModel);

        // TODO: Use userManagementController to fetch user data and populate felhasználókTáblaModel
        // Add buttons for adding, editing, and deleting users
        // Implement event listeners for the buttons to interact with the userManagementController

        setLayout(new BorderLayout());
        add(new JLabel("Felhasználók Kezelése"), BorderLayout.NORTH);
        add(new JScrollPane(felhasználókTábla), BorderLayout.CENTER);
    }

    public void frissítésFelhasználók() {
        // TODO: Fetch the latest user data from the userManagementController
        // Update the felhasználókTáblaModel with the new data
    }
}