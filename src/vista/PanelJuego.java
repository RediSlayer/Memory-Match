package src.vista;
import src.modelo.Reloj;
import src.modelo.Tablero;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelJuego extends JPanel{
    //Iniciamos los atributos y variables
    private JLabel tiempo, mensaje;//Labels de información
    private Boton[][] botonesTablero = new Boton[4][4];//Creamos los botones del tablero
    private JButton btnReiniciar; //Botón de reiniciar

    //Inicializamos de nuestras clases Reloj y Tablero
    Tablero tablero;//No los instancio ya que lo haremos al constructor para guardar buenos habitos
    Reloj reloj;
    private Timer relojVisual;

    //Variables de control
    private Boton primerBotonSeleccionado = null; //Para guardar si ha sido el primer botón clickeado
    private Boton segundoBotonSeleccionado = null; //Para guardar el segundo botón, que se activa ssi el primero no es null
    private boolean esperandoVerificacion = false; //Variable para freezear el tablero mientras esperamos la verificación

    public PanelJuego(){
        setLayout(null); //Para tener libertad a la hora de colocar todo
        setBackground(new Color(245, 245, 245)); //Colo de fondo asi grisaceo tirando a blanquito

        //Instanciamos las clases de modelo
        tablero = new Tablero();
        reloj = new Reloj();

        //Label de tiempo
        tiempo = new JLabel("Han pasado 0s");
        tiempo.setBounds(30,15,150,20);
        tiempo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        add(tiempo);

        //Label de mensaje
        mensaje = new JLabel("Encuentra las parejas");
        mensaje.setBounds(130,15,250,20);
        mensaje.setFont(new Font("Segoe UI", Font.BOLD, 12));
        add(mensaje);

        //Creación y colocación de cada boton en el JFrame
        for (int i = 0; i < botonesTablero.length; i++) {
            for (int j = 0; j < botonesTablero[i].length; j++) {
                int x = i * 90 + 40; //La posición de los botones
                int y = j * 90 + 55; //La posición de los botones

                //Hay que CREAR el botón antes de usarlo (antes era null)
                Boton botonActual = new Boton(" ", i, j); //La referencia del objeto
                botonesTablero[i][j] = botonActual;
                botonActual.setBounds(x, y, 80, 80);
                //AddActionListener
                botonActual.addActionListener(e -> gestionarClickBoton(botonActual));
                add(botonActual);
            }
        }

        //-----------Botón Reiniciar------------
        btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.setBounds(165, 430, 110, 35);
        btnReiniciar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnReiniciar.setFocusPainted(false);
        btnReiniciar.setVisible(false);
        btnReiniciar.addActionListener(e -> ReiniciarJuego()); //Aqui agregue la función de que si le hace click reinicia
        add(btnReiniciar);


        //Inicio del juego
        tablero.IniciarTablero();
        IniciarRelojVisual();
    }
    //===================================================
    //===    Logica visual que interactua con modelo  ===
    //===================================================

    public void gestionarClickBoton(Boton botonPulsado){
        //1. El juego se acabo o esperamos a la verificación de la pareja
        if (tablero.isJuegoTerminado() || esperandoVerificacion){
            return;
        }
        //2. Evitar las trampas haciendo que el jugador no pueda seleccionar 2 veces la misma carta
        if (botonPulsado == primerBotonSeleccionado){
            return;
        }
        //3. Pedimos el simbolo del tablero para ponerlo en el tablero
        char simbolo = tablero.getSimbolo(botonPulsado.getFila(), botonPulsado.getColumna());
        botonPulsado.setText(String.valueOf(simbolo));
        if(primerBotonSeleccionado == null){
            primerBotonSeleccionado = botonPulsado;
        }else{
            segundoBotonSeleccionado = botonPulsado;

            Boton botonEsconderUno = primerBotonSeleccionado;
            Boton botonEsconderDos = segundoBotonSeleccionado;
            //Para verificar si son pareja volvemos a llamar a mi querida función el modelo
            boolean esPareja = tablero.VerificarParejas(botonEsconderUno.getFila(), botonEsconderUno.getColumna(), botonEsconderDos.getFila(), botonEsconderDos.getColumna());

            //Limpiamos las selecciones en los dos casos
            primerBotonSeleccionado = null;
            segundoBotonSeleccionado = null;

            if(esPareja) {
                mensaje.setText("¡Has encontrado una pareja! Has encontrado " + tablero.getParejasEncontradas());
                Timer tempo = new Timer(1500, ae ->{
                    if(tablero.getParejasEncontradas() == 1) {
                        mensaje.setText("Has encontrado " + tablero.getParejasEncontradas() + " pareja");
                    }else{
                        mensaje.setText("Has encontrado " + tablero.getParejasEncontradas() + "parejas");
                    }
                });
                tempo.setRepeats(false);
                tempo.start();
                botonEsconderUno.setEnabled(false);
                botonEsconderDos.setEnabled(false);
                //Ahora verificamos si ganamos
                if (tablero.isJuegoTerminado()) {
                    reloj.DetenerReloj();
                    relojVisual.stop(); //Paramos también el reloj visual
                    mensaje.setText("¡Has encontrado todas las parejas!");
                    btnReiniciar.setVisible(true);
                }

            }else{
                mensaje.setText("¡No eran parejas!");
                esperandoVerificacion = true;
                //Temporizador para evitar de que se quiten las parejas al instante
                Timer tempo = new Timer(1500, ae ->{
                    botonEsconderDos.setText(" ");
                    botonEsconderUno.setText(" ");
                    esperandoVerificacion = false;
                    mensaje.setText("Has encontrado " + tablero.getParejasEncontradas() + " parejas");
                });
                tempo.setRepeats(false);
                tempo.start();
            }
        }
    }

    //Inicia el reloj de la pantalla del JFrame
    private void IniciarRelojVisual(){
        //Si ya había un reloj visual lo paramos para no acumular Timers
        if (relojVisual != null) {
            relojVisual.stop();
        }
        reloj.IniciarReloj();
        relojVisual = new Timer(1000, e -> {
            tiempo.setText("Han pasado " + reloj.getSegundosTranscurridos() + " s");
        });
        relojVisual.start();
    }

    //Quita el texto y los pone en tue el enabled
    private void reiniciarBotones(){
        for (int i = 0; i < botonesTablero.length; i++) {
            for (int j = 0; j < botonesTablero[i].length; j++) {
                botonesTablero[i][j].setEnabled(true);
                botonesTablero[i][j].setText(" ");
            }
        }
    }

    //Función para reiniciar el juego
    private void ReiniciarJuego(){
        reiniciarBotones();
        tablero.IniciarTablero();
        reloj.ReiniciarReloj();
        tiempo.setText("Han pasado 0 s");
        IniciarRelojVisual();
        mensaje.setText("¡Bienvenido de nuevo!");
        primerBotonSeleccionado = null;
        segundoBotonSeleccionado = null;
        esperandoVerificacion = false;
        btnReiniciar.setVisible(false); //Ocultamos otra vez el botón de reiniciar
    }
}