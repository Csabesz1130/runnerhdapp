package org.example.views;

import org.example.controllers.NotificationsController;

import javax.swing.*;
import java.awt.*;

public class NotificationsView extends JPanel {
    private NotificationsController notificationsController;
    private JList<String> értesítésekLista;

    public NotificationsView(NotificationsController notificationsController) {
        this.notificationsController = notificationsController;

        értesítésekLista = new JList<>();

        // TODO: Use notificationsController to fetch notifications and populate értesítésekLista
        // Add a button or timer to periodically refresh the notifications

        setLayout(new BorderLayout());
        add(new JLabel("Értesítések"), BorderLayout.NORTH);
        add(new JScrollPane(értesítésekLista), BorderLayout.CENTER);
    }

    public void frissítésÉrtesítések() {
        // TODO: Fetch the latest notifications from the notificationsController
        // Update the értesítésekLista with the new notifications
    }
}