package org.example.views;

import org.example.MainFrame;
import org.example.controllers.AuthController;
import org.example.services.FirestoreService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JPanel {
    public static final String[] FELHASZNÁLÓNEVEK = {"admin", "user1", "user2"};
    public static final String JELSZÓ = "password";

    private AuthController authController;

    private JTextField felhasználónévTextField;
    private JPasswordField jelszóPasswordField;
    private JButton bejelentkezésButton;

    public LoginView(MainFrame mainFrame) {
        FirestoreService firestoreService = new FirestoreService();
        this.authController = new AuthController(firestoreService, FELHASZNÁLÓNEVEK, JELSZÓ);

        felhasználónévTextField = new JTextField(20);
        jelszóPasswordField = new JPasswordField(20);
        bejelentkezésButton = new JButton("Bejelentkezés");

        bejelentkezésButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String felhasználónév = felhasználónévTextField.getText();
                String jelszó = new String(jelszóPasswordField.getPassword());

                if (authController.login(felhasználónév, jelszó)) {
                    JOptionPane.showMessageDialog(null, "Sikeres bejelentkezés!");
                    mainFrame.showMainView();
                } else {
                    JOptionPane.showMessageDialog(null, "Hibás felhasználónév vagy jelszó!", "Hiba", JOptionPane.ERROR_MESSAGE);
                }
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