package org.example.views;

import org.example.controllers.AuthController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JPanel {
    private AuthController authController;

    private JTextField felhasználónévTextField;
    private JPasswordField jelszóPasswordField;
    private JButton bejelentkezésButton;

    public LoginView(AuthController authController) {
        this.authController = authController;

        felhasználónévTextField = new JTextField(20);
        jelszóPasswordField = new JPasswordField(20);
        bejelentkezésButton = new JButton("Bejelentkezés");

        bejelentkezésButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String felhasználónév = felhasználónévTextField.getText();
                String jelszó = new String(jelszóPasswordField.getPassword());

                // TODO: Call the login method on the authController with the provided credentials
                // Handle the login result (success or failure) and update the UI accordingly
            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(new JLabel("Felhasználónév:"), constraints);

        constraints.gridx = 1;
        add(felhasználónévTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(new JLabel("Jelszó:"), constraints);

        constraints.gridx = 1;
        add(jelszóPasswordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(bejelentkezésButton, constraints);
    }
}