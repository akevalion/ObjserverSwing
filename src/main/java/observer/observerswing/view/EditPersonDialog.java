package observer.observerswing.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import observer.observerswing.model.Person;

public class EditPersonDialog extends JDialog {
    private Person person;
    private JTextField nameField;
    private JTextField codeField;
    private JComboBox<String> subscriptionComboBox;
    private boolean updated;

    public EditPersonDialog(JFrame parent, Person person) {
        super(parent, "Editar Persona", true);
        this.person = person;
        this.updated = false;
        
        buildComponents();
        populateFields();
        
        this.pack();
        this.setLocationRelativeTo(parent);
    }

    private void buildComponents() {
        this.setLayout(new BorderLayout());

        // Panel central con campos de texto
        JPanel fieldsPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        fieldsPanel.add(new JLabel("Nombre:"));
        nameField = new JTextField();
        fieldsPanel.add(nameField);

        fieldsPanel.add(new JLabel("Código:"));
        codeField = new JTextField();
        fieldsPanel.add(codeField);

        fieldsPanel.add(new JLabel("Suscripción:"));
        subscriptionComboBox = new JComboBox<>(new String[]{"Sí", "No"});
        fieldsPanel.add(subscriptionComboBox);

        this.add(fieldsPanel, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel buttonPanel = new JPanel();
        JButton updateButton = new JButton("Actualizar");
        JButton cancelButton = new JButton("Cancelar");
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        // Acciones de los botones
        updateButton.addActionListener(e -> updatePerson());
        cancelButton.addActionListener(e -> dispose());
    }

    private void populateFields() {
        nameField.setText(person.getName());
        codeField.setText(person.getCode());
        subscriptionComboBox.setSelectedItem(person.isIsSubscribed() ? "Sí" : "No");
    }

    private void updatePerson() {
        person.setName(nameField.getText().trim());
        person.setCode(codeField.getText().trim());
        person.setIsSubscribed(subscriptionComboBox.getSelectedItem().equals("Sí"));
        updated = true; // Marcar que se realizó una actualización
        dispose();
    }

    public boolean isUpdated() {
        return updated;
    }
}
