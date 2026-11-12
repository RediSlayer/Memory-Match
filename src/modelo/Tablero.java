package src.modelo;

import java.util.ArrayList;
import java.util.Collections;

public class Tablero {
    //Inicializamos atributos
    private char[][] tableroConSimbolos = new char[4][4]; //Tablero con los simbolos para las parejas
    private int parejasEncontradas = 0; //Numero de parejas encontrados

    //Iniciamos el tablero
    public void IniciarTablero(){
        //Iniciamos un array con simbolos para mezclarlo
        ArrayList<Character> cartas = new ArrayList<>();
        cartas.add('♥');cartas.add('♥');
        cartas.add('♦');cartas.add('♦');
        cartas.add('♣');cartas.add('♣');
        cartas.add('♠');cartas.add('♠');
        cartas.add('★');cartas.add('★');
        cartas.add('☀');cartas.add('☀');
        cartas.add('♫');cartas.add('♫');
        cartas.add('☯');cartas.add('☯');
        Collections.shuffle(cartas);

        int indiceArray = 0;
        for (int i = 0; i < tableroConSimbolos.length; i++) {
            for (int j = 0; j < tableroConSimbolos[i].length; j++) {
                tableroConSimbolos[i][j] = cartas.get(indiceArray);
                indiceArray++;
            }
        }
    }

    //Creamos función para verificar parejas
    public boolean VerificarParejas(int filaUno, int columnaDos, int filaDos, int columnaDos){
        if(tableroConSimbolos[filaUno][columnaDos] == tableroConSimbolos[filaDos][columnaDos]){
            //Por si encuentra
            parejasEncontradas++;
            return true;
        } else {
            //Si no encuentra nada
            return false;
        }
    }

    //Creamos funcion para reiniciar el juego
    public void Reiniciar(){
        parejasEncontradas = 0;
        IniciarTablero();
    }

    //Verifica si el juego a terminado
    public boolean isJuegoTerminado(){
        return parejasEncontradas == 8;
    }

    //Para poder pintar la casilla
    public char getSimbolo(int fila, int columna) {
        return tableroConSimbolos[fila][columna];
    }
}
