package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.negozio.GestoreNegozi;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;

import java.util.ArrayList;

public class JavaFXComunicareConCorriere {
    private final GestoreCorrieri gestoreCorrieri;
    private final GestoreNegozi gestoreNegozi;


    public JavaFXComunicareConCorriere(GestoreCorrieri gestoreCorrieri, GestoreNegozi gestoreNegozi) {
        this.gestoreCorrieri = gestoreCorrieri;
        this.gestoreNegozi = gestoreNegozi;
    }

    public void getDettagliCorrieriDisponibili(){
        //todo
    }

    public void selezionaCorriere(int ID){
        //todo (metodo di gestoreCorrieri)
    }

    public void selezionaNegozio(int ID){
        //todo (metodo di gestoreNegozi)
    }

    public void getDettagliNegoziConOrdini(){
        //todo
    }

    public void mandaAlert(int IDCorriere, ArrayList<Negozio> negozi){
        //todo manda alert al corriere con i negozi in cui prelevare merce
    }
}
