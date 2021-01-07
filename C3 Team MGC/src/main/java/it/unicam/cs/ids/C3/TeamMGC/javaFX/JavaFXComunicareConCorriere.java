package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.negozio.GestoreNegozi;

public class JavaFXComunicareConCorriere {
    private final GestoreCorrieri gestoreCorrieri;
    private final GestoreNegozi gestoreNegozi;


    public JavaFXComunicareConCorriere(GestoreCorrieri gestoreCorrieri, GestoreNegozi gestoreNegozi) {
        this.gestoreCorrieri = gestoreCorrieri;
        this.gestoreNegozi = gestoreNegozi;
    }

    public void selezionaCorriere(int ID){
        //todo (metodo di gestoreCorrieri)
    }

    public void getDettagliNegozi(){
        //todo
    }
}
