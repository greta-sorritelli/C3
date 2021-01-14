package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.magazziniere.JavaFXComunicareConCorriere;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.magazziniere.JavaFXConsegnareMerceAlCliente;
import it.unicam.cs.ids.C3.TeamMGC.negozio.GestoreNegozi;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class IMagazziniere implements JavaFXController {
    //todo
    private final Negozio negozio = new Negozio(1);
    //todo
    private final PuntoPrelievo puntoPrelievo = new PuntoPrelievo(1);

    private final GestoreClienti gestoreClienti = new GestoreClienti();
    private final GestoreNegozi gestoreNegozi = new GestoreNegozi();
    private final GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
    private final GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);
    private int IDMazziniere;

    public IMagazziniere(int ID) throws SQLException {
        this.IDMazziniere = ID;
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
        openWindow("/ComunicareConCorriere.fxml", "ComunicareConCorriere", new JavaFXComunicareConCorriere(gestoreCorrieri, gestoreNegozi));
    }

    /**
     * Apre la finestra per consegnare la merce al cliente.
     */
    @FXML
    public void consegnaMerceCliente(){
        openWindow("/ConsegnareMerceCliente.fxml", "ConsegnareMerceCliente", new JavaFXConsegnareMerceAlCliente(puntoPrelievo,gestoreOrdini,gestoreClienti));
    }

}