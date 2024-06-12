package org.example.views;

import org.example.controllers.DashboardController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DashboardView extends JPanel {
    private DashboardController dashboardController;
    private JLabel cimke;
    private JPanel statisztikákPanel;
    private JButton importButton;
    private JButton exportButton;

    public DashboardView(DashboardController dashboardController) {
        this.dashboardController = dashboardController;

        cimke = new JLabel("Irányítópult", SwingConstants.CENTER);
        cimke.setFont(new Font("Arial", Font.BOLD, 24));

        statisztikákPanel = new JPanel();
        importButton = new JButton("Import Excel");
        exportButton = new JButton("Export Excel");

        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    List<List<String>> data = dashboardController.importData(filePath);
                    // TODO: Display data in the statisztikákPanel
                }
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    // TODO: Gather data to export
                    List<List<String>> data = null; // Replace with actual data
                    dashboardController.exportData(filePath, data);
                }
            }
        });

        setLayout(new BorderLayout());
        add(cimke, BorderLayout.NORTH);
        add(statisztikákPanel, BorderLayout.CENTER);
        add(importButton, BorderLayout.WEST);
        add(exportButton, BorderLayout.EAST);
    }

    public void frissítésStatisztikák() {
        // TODO: Update the statistics and charts based on the latest data from the controller
        // Clear the statisztikákPanel and repopulate it with updated data
    }
}
