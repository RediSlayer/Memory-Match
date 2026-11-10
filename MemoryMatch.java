import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class MemoryMatch extends JFrame implements ActionListener{
	
	private JLabel tiempo, mensajes; //Label para poner el tiempo o mensajes
	private JButton[][] botonesTablero = new JButton[4][4]; //Primera vez que eso esa forma de botones 
	private JButton btnReiniciar; //boton de reiniciar
	private char[][] tableroConSimbolos = new char[4][4]; //Tablero con los simbolos para las parejas
	private JButton firstButtonSelected = null; //Primer boton seleccionado
	private JButton secondButtonSelected = null; //Segundo boton Seleccionado
	private boolean juegoTerminado = false; //Variable de control
	private boolean esperandoVerificacion = false; //Variable que verifica si botones seleccionados son parejas
	private int parejasEncontradas = 0; //Numero de parejas encontradas ira en el actionPerformed y si parejasEncontradas = 8 entonces juegoTerminado = true
	private int segundosTranscurridos = 0; //Tiempo en sabeis
	private Timer relojGlobal; //Timer para pararlo cuando gane alguien
	
	//Iniciar el tablero con simbolos escondidos
	public void IniciarTablero(){
		//Creación de la lista temporal para meter los elementos en el tablero
		ArrayList<Character> cartas = new ArrayList<>();
		//Agregamos los simbolos del juego
		cartas.add('♥'); 
		cartas.add('♦'); 
		cartas.add('♣'); 
		cartas.add('♠'); 
		cartas.add('★'); 
		cartas.add('☀'); 
		cartas.add('♫'); 
		cartas.add('☯'); 
		//Como necesito 16 simbolos y son parejas pues ggs ya sabeis
		cartas.add('♥'); 
		cartas.add('♦'); 
		cartas.add('♣'); 
		cartas.add('♠'); 
		cartas.add('★'); 
		cartas.add('☀'); 
		cartas.add('♫'); 
		cartas.add('☯');
		//Mezclamos la lista
		Collections.shuffle(cartas);
		//Ahora iniciamos la lista de chars 
		int indiceArray = 0;
		//Al tener la lista barajada, ponemos cada caracter en un espacio de la matriz
		for (int i = 0; i < tableroConSimbolos.length; i++){
			for(int j = 0; j < tableroConSimbolos[i].length; j++){
				tableroConSimbolos[i][j] = cartas.get(indiceArray);
				indiceArray++;
			}
		}
	}
	
	//Función para reiniciar tablero
	public void Reiniciar(){
		//Todas las variables de control las reiniciamos
		juegoTerminado = false;
		mensajes.setText("Bienvenido de nuevo");
		esperandoVerificacion = false;
		parejasEncontradas = 0;
		btnReiniciar.setVisible(false);
		tiempo.setText("Tiempo 0s");
		segundosTranscurridos = 0;
		relojGlobal.start();


		
		//Seteamos un texto invisible para quitar los de antes y los activamos
		for(int i = 0; i < botonesTablero.length; i++){
			for(int j = 0; j < botonesTablero[i].length; j++){
				botonesTablero[i][j].setEnabled(true);
				botonesTablero[i][j].setText(" ");
			}
		}
		
		IniciarTablero();
	}
	
	//Funcion que devuelve true si encuentra pareja y false si no hay pareja
	public boolean VerificamosParejas(){
		char primerSimbolo = ' ';
		char segundoSimbolo = ' ';
		for (int i = 0; i < tableroConSimbolos.length; i++){
			for(int j = 0; j < tableroConSimbolos[i].length; j++){
				//Verificamos si un boton es igual al que seleccione
				if(firstButtonSelected == botonesTablero[i][j]){
					primerSimbolo = tableroConSimbolos[i][j]; //Guardamos el valor en el boton que estaba asignado
				}
				
				//Pero puede que sea el segundo botón7
				if(secondButtonSelected == botonesTablero[i][j]){
					segundoSimbolo = tableroConSimbolos[i][j];	 //Guardamos el valor que estaba en el boton asignado
				}
			}
		}
		
		if(primerSimbolo == segundoSimbolo){
			mensajes.setText("Has encontrado una pareja!");
			firstButtonSelected.setEnabled(false); //Asi ya no pueden hacer click si son pareja
			secondButtonSelected.setEnabled(false);
			//Limpiamos la seleccion antes del RETURN
			firstButtonSelected = null;
			secondButtonSelected = null;
			return true;
		} else {
			mensajes.setText("No eran parejas");
			//Hay que limpiar la seleccion siempre antes del RETURN importante
			firstButtonSelected = null;
			secondButtonSelected = null;
			return false;
		}
		
	}
	
	//El reloj va aqui!
	public void IniciarReloj(){
		//Como la libreria javax.swing.Timer  funciona por ms habra que adaptarnos
		relojGlobal = new Timer(1000, e ->{ // Asignamos una funcion lambda que me agiliza el codigo
			segundosTranscurridos++;
			tiempo.setText("Han pasado: " + segundosTranscurridos + " s"); //Lo pongo en el label correspondiente
		});
		
		relojGlobal.setRepeats(true); //Lo pongo para que se repita, si pusiera false lo que haria seria hasta q no llegue a x, no voy a parar y si llego paro
		relojGlobal.start(); //Starteamos el programa super importante para que inicie y podamos empezar el reloj 	
	}
	
	//Toda la parte de eventos
	public void actionPerformed(ActionEvent e){
		//Para evitar bugs o trampas de seleccionar el mismo botón usaremos
		JButton botonPulsado = (JButton) e.getSource(); //Lo que hacemos es crear un boton que almacena el boton clickado
		
		
		//Boton Reiniciar, al principio de todo para evitar el bloqueo recuerda el orden!!
		if(botonPulsado == btnReiniciar){
			Reiniciar();
			return;
		}
		
		//Bloqueamos los click mientras verifique o el juego se ha acabado, asi evitamos clicks tontos
		if (juegoTerminado || esperandoVerificacion){
			return;
		}
		
		
		//Aqui para evitar tonterias lo que hago simplemente es ignorar el click
		if(botonPulsado == firstButtonSelected){
			return;
		}
		
		//Mostramos el simbolo del boton seleccionado
		for (int i = 0; i < botonesTablero.length; i++){
			for(int j = 0; j < botonesTablero[i].length; j++){
				if(botonesTablero[i][j] == botonPulsado){
					botonPulsado.setText(String.valueOf(tableroConSimbolos[i][j]));
				}
			}
		}
		
		//Tenemos que tener en cuenta (me olvide) de saber si es el first o secondButtonSelected
		if(firstButtonSelected == null){
			//Guardamos boton Pulsado en firstButtonSelected!!!
			firstButtonSelected = botonPulsado;
		} else{
			//En el dado caso que no es el primero, va al Segundo
			secondButtonSelected = botonPulsado;
			
			//Como en mi funcion he quitado lo de eliminar el texto, vamos a crear copias para evitar de que se quita casi al instante
			JButton botonEsconderUno = firstButtonSelected;
			JButton botonEsconderDos = secondButtonSelected;
			
			//Ahora nos encargamos de si son pareja y si ha ganado
			if(VerificamosParejas()){
				parejasEncontradas++;
				if(parejasEncontradas == 8){
					//Caso que ganamos
					relojGlobal.stop();
					juegoTerminado = true;
					mensajes.setText("Has ganado en: " + segundosTranscurridos + " segundos.");
					btnReiniciar.setVisible(true);
				}
					
			//Nos encargamos de si no ganamos 	
			} else {
				esperandoVerificacion = true; //Bloqueamos los clicks
				
				//Creamos temporizador para no quitar las fichas directamente utilizando lambda como la ultima vez
				Timer temporizadorFallo = new Timer(1500, ae ->{
					//borramos los textos
					botonEsconderUno.setText(" ");
					botonEsconderDos.setText(" ");
					esperandoVerificacion = false; //Volvemos a poder jugar!!
				});
				
				temporizadorFallo.setRepeats(false); //No quiero que lo repita, que haga la accion y ya, como un do while raro 
				temporizadorFallo.start(); //Activamos la cuenta atras para que lo cuente y se acabe
				
			}
			
		}
		
		
	}
	
	//Constructor
	public MemoryMatch(){
		setLayout(null);
		setTitle("Memory Match");
		
		//Label Tiempo
		tiempo = new JLabel("Han pasado 0s");
		tiempo.setBounds(30,15,150,20);
		add(tiempo);
		
		//Label mensajes
		mensajes = new JLabel("¡Encuentra las parejas!");
		mensajes.setBounds(180,15,250,20);
		add(mensajes);
		
		//botones
		for(int i = 0; i < botonesTablero.length; i++){
			for(int j = 0; j < botonesTablero[i].length; j++){
				//Vamos a colocar 1 por 1 cada boton
				botonesTablero[i][j] = new JButton(" "); //Son strings recuerda
				botonesTablero[i][j].addActionListener(this); //Es el mismo sistema de siempre 
				
				//Colocamos sobre la ventana
				int x = j * 90 + 40; //Espaciado horziontal -
				int y = i * 90 + 60; //Espaciado vertical |
				botonesTablero[i][j].setBounds(x,y,80,80);
				add(botonesTablero[i][j]); 
				//Haciendo esto nos ahorra 16x4 lineas asi que hay q aprender
			}
		}
		
		//Boton reiniciar
		btnReiniciar = new JButton("Reiniciar");
		btnReiniciar.setBounds(170, 430, 100, 30);
		btnReiniciar.addActionListener(this);
		btnReiniciar.setVisible(false);
		add(btnReiniciar);
		
		//Llamamos funciones logicas
		IniciarTablero();
		IniciarReloj();
	}
	
	//Main de toda la vida
	public static void main(String args[]){
		MemoryMatch interfaz = new MemoryMatch();
		interfaz.setBounds(0,0,450,550);
		interfaz.setVisible(true);
		interfaz.setLocationRelativeTo(null);
		interfaz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		interfaz.setResizable(false);
	}
}