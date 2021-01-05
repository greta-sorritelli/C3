package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;

import java.sql.SQLException;

public class IAddettoMagazzino {

    //todo
    private Negozio negozio;

    private final GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
    private final GestoreMagazzini gestoreMagazzini = new GestoreMagazzini();
    private final GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);

    public void assegnaMerceCorriere() {
        //todo
    }

    public void selezionaCorriere(int ID) throws SQLException {
        gestoreCorrieri.selezionaCorriere(ID);
    }

    public void getDettagliMagazziniDisponibili() throws SQLException {
        gestoreMagazzini.getDettagliItems();
    }

    public void sceltaPuntoPrelievo(int ID) throws SQLException {
        gestoreMagazzini.sceltaPuntoPrelievo(ID);
    }

    public void visualizzaMerce(int capienza) throws SQLException {
        gestoreOrdini.visualizzaMerce(capienza);
    }

    public void confermaAssegnazioneMerce(int IDCorriere, int IDMerce, int capienza) throws SQLException {
        gestoreCorrieri.setCapienza(IDCorriere,capienza);
        gestoreOrdini.setStatoMerce(IDMerce, StatoOrdine.AFFIDATO_AL_CORRIERE);
    }
}
