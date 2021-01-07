
package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;

import java.sql.SQLException;

public class JavaFXSelezionaPuntoPrelievo {
    private final GestoreOrdini gestoreOrdini;
    private final GestoreMagazzini gestoreMagazzini;

    public JavaFXSelezionaPuntoPrelievo(GestoreOrdini gestoreOrdini, GestoreMagazzini gestoreMagazzini) {
        this.gestoreOrdini = gestoreOrdini;
        this.gestoreMagazzini = gestoreMagazzini;
    }

    //todo inserire IDOrdine per selezionare il PP

    public void getDettagliOrdine(){
        //todo
    }

    //todo se sceglie una residenza
    public void addResidenza(int IDOrdine, String indirizzo) throws  SQLException {
        gestoreOrdini.addResidenza(IDOrdine,indirizzo);
    }

    //todo riferimento caso d'uso seleziona corriere

    public void getDettagliMagazzini(){
        //todo se sceglie un PP
    }

    public void sceltaPuntoPrelievo(int ID) {
        //todo
    }

    public void setPuntoPrelievo(int IDOrdine, int IDPuntoPrelievo) throws SQLException {
        gestoreOrdini.setPuntoPrelievo(IDOrdine, IDPuntoPrelievo);
    }

    public void mandaAlert(){
        // todo alert al magazziniere
    }

    //todo ritirato
    public void setStatoOrdine(int IDOrdine, StatoOrdine statoOrdine) throws SQLException {
        gestoreOrdini.setStatoOrdine(IDOrdine, statoOrdine);
    }

}
