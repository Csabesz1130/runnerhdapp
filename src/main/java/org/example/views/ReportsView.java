package org.example.views;

import org.example.controllers.ReportsController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportsView extends JPanel {
    private ReportsController reportsController;
    private JComboBox<String> jelentésTípusComboBox;
    private JPanel jelentésPanel;

    public ReportsView(ReportsController reportsController) {
        this.reportsController = reportsController;

        jelentésTípusComboBox = new JComboBox<>(new String[]{"Feladatok Összefoglaló", "Futár Teljesítmény", "Telephely Statisztikák"});
        jelentésPanel = new JPanel();

        jelentésTípusComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedJelentés = (String) jelentésTípusComboBox.getSelectedItem();
                // TODO: Call the appropriate method on the reportsController to generate the selected report
                // Update the jelentésPanel with the generated report
            }
        });

        setLayout(new BorderLayout());
        add(new JLabel("Jelentések"), BorderLayout.NORTH);
        add(jelentésTípusComboBox, BorderLayout.WEST);
        add(new JScrollPane(jelentésPanel), BorderLayout.CENTER);
    }
}