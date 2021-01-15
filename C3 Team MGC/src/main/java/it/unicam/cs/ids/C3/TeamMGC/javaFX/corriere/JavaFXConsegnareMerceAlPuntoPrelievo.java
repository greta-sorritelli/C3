package it.unicam.cs.ids.C3.TeamMGC.javaFX.corriere;

import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;

public class JavaFXConsegnareMerceAlPuntoPrelievo {

    private final GestoreOrdini gestoreOrdini = GestoreOrdini.getInstance();
    private final GestoreMagazzini gestoreMagazzini = GestoreMagazzini.getInstance();
    private final GestoreCorrieri gestoreCorrieri = GestoreCorrieri.getInstance();

    public void getDettagliMerce(StatoOrdine statoOrdine){
        // todo stato ritirato
    }

    public void setStatoMerce(int IDMerce, StatoOrdine statoOrdine){
        //todo stato in deposito se la destinazione è PP
        //todo stato ritirato se nella residenza il cliente ha ririrato
        //todo stato in transito quando il corriere la trasporta
        //todo stato in deposito se il cliente non c'era a casa e lo portiamo al magazzino vicino
    }

    public void ricercaMagazzino(String indirizzo){
        //todo
    }

    // todo reference Trasportare la merce
//    public void trasportoMerce(){
//        //usare getDettagliMerce con stato affidato al corriere
//    }

    public void confermaConsegna(int IDMerce){
        //todo
    }

    public void setCapienza(int capienza){
        //todo
    }



}
