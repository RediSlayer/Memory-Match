package src.vista;
import src.modelo.Reloj;
import src.modelo.Tablero;
import src.vista.Boton;
import javax.swing.*;

public class PanelJuego extends JFrame{
    //Iniciamos los atributos y variables
    private JLabel tiempo, mensaje;//Labels de información
    private Boton botonesTablero = new Boton[4][4];//Creamos los botones del tablero
    private JButton btnReiniciar; //Botón de reiniciar

    //Inicializamos de nuestras clases Reloj y Tablero
    Tablero tablero;//No los instancio ya que lo haremos al constructor para guardar buenos habitos
    Reloj reloj;

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
        tiempo = new JButton("Han pasado 0s");
        tiempo.setBounds(30,15,150,20);
        tiempo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        add(tiempo);

        //Label de mensaje
        mensaje = new JButton("Encuentra las parejas");
        mensaje.setBounds(180,15,250,20);
        mensajes.setFont(new Font("Segoe UI", Font.BOLD, 12));
        add(mensaje);
        
        //Creación y colocación de cada boton en el JFrame
        for (int i = 0; i < botonesTablero.length; i++) {
            for (int j = 0; j < botonesTablero[i].length; j++) {
                int x = i * 90 + 40; //La posición de los botones
                int y = j * 90 + 55; //La posición de los botones

                Boton botonActual = botonesTablero[i][j]; //La referencia del objeto
                //AddActionListener
                botonActual.addActionListener(e -> gestionarClickBoton(botonActual));
                add(botonesTablero[i][j]);
            }
        }

        //-----------Botón Reiniciar------------
        btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.setBounds(165, 430, 110, 35);
        btnReiniciar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnReiniciar.setFocusPainted(false);
        btnReiniciar.setVisible(false);
        btnReiniciar.addActionListener(e -> reiniciarJuego());
        add(btnReiniciar);


        //Inicio del juego
        tablero.IniciarTablero();
        IniciarRelojVisual();
    }

    public void gestionarClickBoton(Boton botonPulsado){

    }

    private void IniciarRelojVisual(){

    }

    private void ReiniciarJuego(){

    }
}