package observer.observerswing.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import javax.swing.JPanel;
import observer.observerswing.model.Observer;

public class ThermometerPanel extends JPanel implements Observer{
    private int temperature; // La temperatura actual

    public ThermometerPanel() {
        this.temperature = 0; // Valor inicial
        this.setPreferredSize(new Dimension(100, 300)); // Tamaño predeterminado
    }

    /**
     * Dibuja el termómetro en el panel.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Crear un Path2D para el termómetro
        Path2D path = new Path2D.Double();

        // Coordenadas del termómetro
        int x = 100; // Posición X del termómetro
        int y = 50;  // Posición Y del termómetro
        int width = 40; // Ancho del termómetro
        int height = 200; // Altura del tubo
        int bulbDiameter = 80; // Diámetro del bulbo (parte inferior del termómetro)

        // Dibuja el tubo del termómetro (un rectángulo largo)
        path.moveTo(x + width / 2.0, y); // Comienza en la parte superior del tubo
        path.lineTo(x + width / 2.0, y + height); // Dibuja el tubo hacia abajo
        
        double bulbRadius = bulbDiameter / 2.0;
        int tubeWidth = width;
        int tubeHeight = height;
        double centerX = x;
        double centerY = y + tubeHeight + bulbRadius;
        // Dibuja el semicírculo en la parte inferior (el bulbo)
        path.curveTo(
            x + tubeWidth / 2.0, centerY - bulbRadius, // Punto de control 1
            centerX + bulbRadius, centerY,            // Punto de control 2
            centerX, centerY                          // Punto final en la parte inferior del bulbo
        );
        path.curveTo(
            centerX - bulbRadius, centerY,            // Punto de control 3
            x - tubeWidth / 2.0, centerY - bulbRadius, // Punto de control 4
            x - tubeWidth / 2.0, y + tubeHeight       // Punto inicial del lado izquierdo del tubo
        );

        // Dibuja el lado izquierdo del tubo
        path.lineTo(x - width / 2.0, y); // Línea hacia arriba hasta el borde superior del tubo

        // Color de fondo del termómetro según la temperatura
        Color thermometerColor = getBackgroundColor(temperature);
        g2d.setColor(thermometerColor);

        // Dibujar el termómetro
        g2d.fill(path); // Rellenar el termómetro

        // Borde del termómetro
        g2d.setColor(Color.BLACK); // Color negro para el borde
        g2d.draw(path); // Dibujar el borde del termómetro

        // Opcional: dibujar el "mercado" de la temperatura dentro del termómetro
        int mercuryHeight = (int) (temperature*200/40); // Escalar la temperatura
        g2d.setColor(Color.RED.darker()); // El mercurio es de color rojo
        g2d.fillRect(x - width / 4, y + height - mercuryHeight, width / 2, mercuryHeight); // Rellenar el mercurio
    }

    /**
     * Determina el color de fondo según la temperatura.
     *
     * @param temperature la temperatura actual
     * @return el color correspondiente
     */
    private Color getBackgroundColor(int temperature) {
        if (temperature <= 10) {
            return Color.CYAN; // Frío
        } else if (temperature <= 20) {
            return Color.GREEN; // Templado
        } else if (temperature <= 30) {
            return Color.ORANGE; // Cálido
        } else {
            return Color.RED; // Caluroso
        }
    }

    @Override
    public void update(double value) {
        this.temperature = (int)value;
        repaint();
    }
}
