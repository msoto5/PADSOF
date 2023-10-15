package vistas.inicio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImagenDeFondoPanel extends JPanel {
    private Image imagen;
    private JPanel panel;

    public ImagenDeFondoPanel() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }

    public ImagenDeFondoPanel(Image imagen) {
        this();
        this.imagen = imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
        repaint();
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public JPanel getPanel() {
        return panel;
    }
}   
