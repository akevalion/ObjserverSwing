package observer.observerswing.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import observer.observerswing.model.ConcreteSubject;
import observer.observerswing.model.Person;

public class WeatherSystem extends JFrame {

    public static String NO_NOTIFICATION = "No hay notificacion";

    private ConcreteSubject subject;
    private List<Person> persons;
    private JSlider temperatureSlider;

    private JList<Person> personList;
    private DefaultListModel<Person> listModel;

    public WeatherSystem() {
        super("Sistema meteorologico");
        subject = new ConcreteSubject();
        persons = new ArrayList<>();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.buildComponents();
    }

    public void addPerson(Person person) {
        persons.add(person);
        listModel.addElement(person);
        this.updateSubscription(person);
        this.update();
    }

    private void buildComponents() {
        this.setLayout(new BorderLayout());
        // Panel superior con botones
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Crear Persona");
        JButton searchButton = new JButton("Buscar Persona");
        topPanel.add(addButton);
        topPanel.add(searchButton);

        JPanel centerPanel = new JPanel(new BorderLayout());
        // Crear el modelo de la lista y el JList
        listModel = new DefaultListModel<>();
        personList = new JList<>(listModel);
        personList.setCellRenderer(new PersonCellRenderer());

        // Agregar la lista con scroll
        JScrollPane scrollPane = new JScrollPane(personList);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel lateral derecho con gráfico y slider
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        ThermometerPanel thermometerPanel = new ThermometerPanel();
        subject.addObserver(thermometerPanel);
        rightPanel.add(thermometerPanel, BorderLayout.CENTER);
        rightPanel.setPreferredSize(new Dimension(200, 0));

        // Gráfico de termómetro (simulación simple con un JLabel)
        // Slider para cambiar la temperatura
        temperatureSlider = new JSlider(JSlider.HORIZONTAL, 0, 40, 20);
        temperatureSlider.setMajorTickSpacing(10);
        temperatureSlider.setPaintTicks(true);
        temperatureSlider.setPaintLabels(true);
        rightPanel.add(temperatureSlider, BorderLayout.SOUTH);
        temperatureSlider.addChangeListener(e -> updateTemperature());

        // Agregar los paneles a la ventana
        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(rightPanel, BorderLayout.EAST);

        // Acciones básicas para los botones
        addButton.addActionListener(e -> addPersonUI());
        searchButton.addActionListener(e -> searchPerson());
    }

    public void updateTemperature() {
        subject.setState(temperatureSlider.getValue());
        this.update();
    }

    public void addPersonUI() {
        AddPersonDialog dialog = new AddPersonDialog(this);
        Person newPerson = dialog.showDialog();
        if (newPerson != null) {
            this.addPerson(newPerson);
        }
    }

    public void searchPerson() {
        String searchTerm = JOptionPane.showInputDialog(this,
                "Introduce el código de la persona que deseas buscar:",
                "Buscar Persona",
                JOptionPane.QUESTION_MESSAGE);

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            // Buscar la persona en la lista
            Person foundPerson = persons.stream()
                    .filter(p -> p.getCode().equalsIgnoreCase(searchTerm.trim()))
                    .findFirst()
                    .orElse(null);
            if (foundPerson == null) {
                JOptionPane.showMessageDialog(this,
                        "No se encontró ninguna persona con ese término de búsqueda.",
                        "Sin resultados",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                boolean oldIsSubscribed = foundPerson.isIsSubscribed();
                EditPersonDialog editDialog = new EditPersonDialog(this, foundPerson);
                editDialog.setVisible(true);

                // Verificar si se actualizó la persona
                if (editDialog.isUpdated()) {
                    if (oldIsSubscribed != foundPerson.isIsSubscribed()) {
                        this.updateSubscription(foundPerson);
                    }
                    JOptionPane.showMessageDialog(this,
                            "La persona ha sido actualizada exitosamente.",
                            "Actualización exitosa",
                            JOptionPane.INFORMATION_MESSAGE);
                    update(); // Llamar a update para refrescar los datos en la interfaz
                }
            }
        }
    }

    public void update() {
        personList.repaint();
    }

    public void open() {
        this.updateTemperature();
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void updateSubscription(Person person) {
        if (person.isIsSubscribed()) {
            subject.addObserver(person);
            subject.notifyObservers();
        } else {
            subject.removeObserver(person);
            person.setMessage(WeatherSystem.NO_NOTIFICATION);
        }
    }

}
