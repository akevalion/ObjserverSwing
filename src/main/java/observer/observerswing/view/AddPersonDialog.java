package observer.observerswing.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import observer.observerswing.model.Person;

public class AddPersonDialog extends JDialog {
    private JTextField nameField;
    private JTextField codeField;
    private JComboBox<String> notificationComboBox;
    private JButton acceptButton;
    private JButton cancelButton;
    private Person createdPerson;
    private boolean isAccepted;

    public AddPersonDialog(JFrame parent) {
        super(parent, "Añadir Persona", true);
        this.setSize(300, 200);
        this.setResizable(false);
        this.setLocationRelativeTo(parent);
        this.setLayout(new BorderLayout());

        // Panel de entrada de datos
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("Nombre:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Código:"));
        codeField = new JTextField();
        inputPanel.add(codeField);

        inputPanel.add(new JLabel("Notificaciones:"));
        notificationComboBox = new JComboBox<>(new String[]{"Sí", "No"});
        inputPanel.add(notificationComboBox);

        this.add(inputPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        acceptButton = new JButton("Aceptar");
        cancelButton = new JButton("Cancelar");
        buttonPanel.add(acceptButton);
        buttonPanel.add(cancelButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        // Eventos de botones
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isAccepted = true;
                String name = nameField.getText().trim();
                String code = codeField.getText().trim();
                boolean isSubscribed = notificationComboBox.getSelectedItem().equals("Sí");

                if (!name.isEmpty() && !code.isEmpty()) {
                    createdPerson = new Person(name, code, isSubscribed);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(AddPersonDialog.this, 
                        "Por favor, llena todos los campos.", 
                        "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> {
            isAccepted = false;
            dispose();
        });
    }

    public Person showDialog() {
        this.setVisible(true);
        return isAccepted ? createdPerson : null;
    }
}
