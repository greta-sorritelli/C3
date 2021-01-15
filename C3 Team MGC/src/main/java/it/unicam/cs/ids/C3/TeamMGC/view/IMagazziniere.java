package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.magazziniere.JavaFXComunicareConCorriere;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.magazziniere.JavaFXConsegnareMerceAlCliente;
import it.unicam.cs.ids.C3.TeamMGC.negozio.GestoreNegozi;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.SimplePuntoPrelievo;
import javafx.fxml.FXML;

import java.sql.SQLException;

public class IMagazziniere implements JavaFXController {
    //todo
    private final Negozio negozio = new Negozio(1);
    //todo
    private final SimplePuntoPrelievo simplePuntoPrelievo = new SimplePuntoPrelievo(1);

    private final GestoreClienti gestoreClienti = GestoreClienti.getInstance();
    private final GestoreNegozi gestoreNegozi = GestoreNegozi.getInstance();
    private final GestoreCorrieri gestoreCorrieri = GestoreCorrieri.getInstance();
    private final GestoreOrdini gestoreOrdini = GestoreOrdini.getInstance();
    private int IDMazziniere;

    public IMagazziniere() throws SQLException {
    }

//    //todo essendo un' interfaccia forse il cerca cliente non deve creare un Cliente come oggetto ma deve
//    //todo semplicemente visualizzare nella view le informazioni.
//    public Cliente cercaCliente(int IDCliente) {
//        try {
//            ResultSet rs = executeQuery("SELECT * from clienti\n" +
//                    "WHERE ID = " + IDCliente + ";");
//            if (rs.next()) {
//                String nome = rs.getString("nome");
//                String cognome = rs.getString("cognome");
//                String codice = rs.getString("codiceRitiro");
//                String data = rs.getString("dataCreazione");
//                return new Cliente(IDCliente, nome, cognome, codice, data);
//            } else return null;
//        } catch (SQLException exception) {
//            //todo
//            exception.printStackTrace();
//            return null;
//        }
//    }

    /**
     * Apre la finestra per comunicare con il corriere.
     */
    @FXML
    //todo creare fxml
    public void avvisaCorriere() {
        openWindow("/ComunicareConCorriere.fxml", "Comunicare Con il Corriere", new JavaFXComunicareConCorriere(simplePuntoPrelievo.getID()));
    }

    /**
     * Apre la finestra per consegnare la merce al cliente.
     */
    @FXML
    public void consegnaMerceCliente() {
        openWindow("/ConsegnareMerceCliente.fxml", "ConsegnareMerceCliente", new JavaFXConsegnareMerceAlCliente(simplePuntoPrelievo, gestoreOrdini, gestoreClienti));
    }

}