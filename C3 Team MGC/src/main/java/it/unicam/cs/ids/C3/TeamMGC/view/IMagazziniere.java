package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;

public class IMagazziniere {

    private GestoreClienti gestoreClienti = new GestoreClienti();

    //todo essendo un' interfaccia forse il cerca cliente non deve creare un Cliente come oggetto ma deve
    //todo semplicemente visualizzare nella view le informazioni.
    public Cliente cercaCliente(int IDCliente) {
        try {
            ResultSet rs = executeQuery("SELECT * from clienti\n" +
                    "WHERE ID = " + IDCliente + ";");
            if (rs.next()) {
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String codice = rs.getString("codiceRitiro");
                String data = rs.getString("dataCreazione");
                return new Cliente(IDCliente, nome, cognome, codice, data);
            } else return null;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * @param IDOrdine
     * @return
     */
    public ArrayList<String> getDettagliMerceMagazzino(int IDOrdine) {
        //todo
        return null;
    }

    /**
     * @param IDOrdine
     * @return
     */
    public ArrayList<String> getDettagliMerceTotale(int IDOrdine) {
        //todo
        return null;
    }

    /**
     * @param IDCliente
     * @return
     */
    public ArrayList<String> getDettagliOrdine(int IDCliente) {
        //todo
        return null;
    }

    public void mandaAlert() {
        // TODO - implement Magazziniere.mandaAlert
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param IDMerce
     * @param statoOrdine
     */
    //todo
    public void setStato(int IDMerce, StatoOrdine statoOrdine){
    }

    /**
     * @param IDCliente
     * @param codiceRitiro
     * @return
     */
    //todo test
    public void verificaCodice(int IDCliente, String codiceRitiro) {
      gestoreClienti.verificaCodice(IDCliente,codiceRitiro);
    }

    public void avvisaCorriere(){
        //todo
    }
}