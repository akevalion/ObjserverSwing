package observer.observerswing.view;

import java.awt.*;
import javax.swing.*;
import observer.observerswing.model.Person;

public class PersonCellRenderer extends JPanel implements ListCellRenderer<Person> {
    private JLabel nameLabel;
    private JLabel codeLabel;
    private JLabel subscriptionLabel;
    private JTextArea statusLabel;

    public PersonCellRenderer() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Panel izquierdo con información principal
        JPanel leftPanel = new JPanel(new GridLayout(3, 1));
        nameLabel = new JLabel();
        codeLabel = new JLabel();
        subscriptionLabel = new JLabel();
        leftPanel.add(nameLabel);
        leftPanel.add(codeLabel);
        leftPanel.add(subscriptionLabel);

        // Etiqueta para mostrar el estado
        statusLabel = new JTextArea();
        statusLabel.setEditable(false);
        this.add(leftPanel, BorderLayout.CENTER);
        this.add(statusLabel, BorderLayout.EAST);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Person> list, Person person, int index, boolean isSelected, boolean cellHasFocus) {
        nameLabel.setText("Nombre: " + person.getName());
        codeLabel.setText("Código: " + person.getCode());
        subscriptionLabel.setText("Suscripción: " + (person.isIsSubscribed() ? "Sí" : "No"));
        statusLabel.setText(person.getMessage());

        // Estilo para selección
        if (isSelected) {
            this.setBackground(list.getSelectionBackground());
            this.setForeground(list.getSelectionForeground());
        } else {
            this.setBackground(list.getBackground());
            this.setForeground(list.getForeground());
        }

        return this;
    }
}
