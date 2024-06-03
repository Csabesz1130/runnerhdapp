package org.example.views;

import org.example.controllers.SettingsController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsView extends JPanel {
    private SettingsController settingsController;
    private JCheckBox offlineMódCheckBox;
    private JButton mentésButton;

    public SettingsView(SettingsController settingsController) {
        this.settingsController = settingsController;

        offlineMódCheckBox = new JCheckBox("Offline Mód");
        mentésButton = new JButton("Mentés");

        mentésButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean offlineMód = offlineMódCheckBox.isSelected();
                // TODO: Call the settingsController to save the offline mode setting
                // Show a success message or handle any errors
            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(offlineMódCheckBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(mentésButton, constraints);
    }
}