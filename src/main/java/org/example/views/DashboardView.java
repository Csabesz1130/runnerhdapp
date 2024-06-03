package org.example.views;

import org.example.controllers.DashboardController;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends JPanel {
    private DashboardController dashboardController;
    private JLabel cimke;
    private JPanel statisztikákPanel;

    public DashboardView(DashboardController dashboardController) {
        this.dashboardController = dashboardController;

        cimke = new JLabel("Irányítópult", SwingConstants.CENTER);
        cimke.setFont(new Font("Arial", Font.BOLD, 24));

        statisztikákPanel = new JPanel();
        // TODO: Add charts, summaries, and other dashboard elements to statisztikákPanel
        // Use dashboardController to fetch data and update the UI

        setLayout(new BorderLayout());
        add(cimke, BorderLayout.NORTH);
        add(statisztikákPanel, BorderLayout.CENTER);
    }

    public void frissítésStatisztikák() {
        // TODO: Update the statistics and charts based on the latest data from the controller
        // Clear the statisztikákPanel and repopulate it with updated data
    }
}