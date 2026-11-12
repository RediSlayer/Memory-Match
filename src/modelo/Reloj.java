package src.modelo;

import javax.swing.*;

public class Reloj {
    //Se encargara de la logica del reloj
    private int segundosTranscurridos = 0; //Tiempo en sabeis
    private Timer relojGlobal; //Timer para pararlo cuando gane alguien

    //Iniciador del reloj
    public void IniciarReloj(){
        relojGlobal = new Timer(1000, e ->{
            segundosTranscurridos++;
        });

        relojGlobal.start();
        relojGlobal.setRepeats(true);
    }

    //Para obtener los segundos
    public int getSegundosTranscurridos(){
        return this.segundosTranscurridos;
    }

    //Para detener el reloj
    public void DetenerReloj(){
        if(relojGlobal != null){
            relojGlobal.stop();
        }
    }

    //Para reiniciar el reloj
    public void ReiniciarReloj(){
        segundosTranscurridos = 0;
        if(relojGlobal != null){
            relojGlobal.restart();
        }
    }
}
