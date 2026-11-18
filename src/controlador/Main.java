package src.controlador;

import src.vista.PanelJuego;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Memory Match");
            ventana.setSize(445, 525);
            ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            ventana.setLocationRelativeTo(null);
            ventana.setResizable(false);

            PanelJuego panelJuego = new PanelJuego();
            ventana.add(panelJuego);

            ventana.setVisible(true);
        });
    }
}