package src.vista;

import javax.swing.*;
import java.awt.*;

public class Boton extends JButton {
    private final int fila;
    private final int columna;

    //Constructor de la clase botón
    public Boton(String texto, int fila, int columna) {
        super(texto); //El texto que enseñara el boton
        this.fila = fila;
        this.columna = columna;

        // --- Configuración visual encapsulada ---
        this.setFont(new Font("Segoe UI Symbol", Font.BOLD, 28));
        this.setFocusPainted(false);
        this.setBackground(Color.LIGHT_GRAY); // O lo que prefieras
    }
    //Getter para la fila
    public int getFila() {
        return fila;
    }
    //Getter para la columna
    public int getColumna() {
        return columna;
    }
}