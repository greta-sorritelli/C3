package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXComunicareCodiceRitiro;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ICommesso {
    //todo
    private Negozio negozio = new Negozio(1);

    private final GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);
    private final GestoreMagazzini gestoreMagazzini = new GestoreMagazzini();
    private final GestoreClienti gestoreClienti = new GestoreClienti();


    /**
     * Apre una nuova finestra
     *
     * @param a Fxml path
     * @param b Titolo della finestra.
     */
    @FXML
    public void openWindow(String a, String b, Object controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(a));
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(b);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
//            Image icon = new Image("/icon.png");
//            stage.getIcons().add(icon);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //todo
    public void comunicaCodiceRitiro() throws SQLException {
        openWindow("/ComunicareCodiceRitiro.fxml", "ComunicareCodiceRitiro", new JavaFXComunicareCodiceRitiro(gestoreClienti));
    }

//    /**
//     * @param IDOrdine
//     * @param indirizzo
//     */
//    public void addResidenza(int IDOrdine, String indirizzo) throws SQLException {
//        gestoreOrdini.addResidenza(IDOrdine,indirizzo);
//    }
//
//    /**
//     * @return ArrayList<String> dei dettagli dei magazzini.
//     */
//    public ArrayList<String> getDettagliItems() throws SQLException {
//        gestoreMagazzini.getDettagliItems();
//        //todo
//        return null;
//    }
//
//    /**
//     * @param ID       Descrizione della merce
//     * @param quantita Quantita della merce
//     * @param IDOrdine   Ordine in cui registrare la merce
//     */
//    //todo IDOrdine
//    public void registraMerce(int ID, int quantita, int IDOrdine) throws SQLException {
//        gestoreOrdini.registraMerce(ID, quantita, IDOrdine);
//    }
//

//
//    public void riceviPagamento() {
//        //todo
//    }
//
//    public void sceltaCorriere() {
//        //todo
//    }
//
//    public void sceltaPuntoPrelievo(int ID) {
//        //todo
//    }
//
//    public void selezionaCorriere(int ID) {
//        //todo
//    }
//
//    public void selezionaPuntoPrelievo(int IDOrdine) {
//        //todo
//    }
//
//    /**
//     * Imposta il negozio collegato all' interfaccia.
//     *
//     * @param negozio Negozio da impostare
//     */
//    public void setNegozio(Negozio negozio) {
//
//        this.negozio = negozio;
//    }
//
//    /**
//     * @param IDOrdine
//     * @param IDPuntoPrelievo
//     */
//    //todo test
//    public void setPuntoPrelievo(int IDOrdine, int IDPuntoPrelievo) throws SQLException {
//        gestoreOrdini.setPuntoPrelievo(IDOrdine,IDPuntoPrelievo);
//    }
//
//    /**
//     * @param IDOrdine
//     * @param statoOrdine
//     */
//    //todo test
//    public void setStatoOrdine(int IDOrdine, StatoOrdine statoOrdine) throws SQLException {
//        gestoreOrdini.setStatoOrdine(IDOrdine,statoOrdine);
//    }
//
//    /**
//     * @param IDOrdine
//     */
//    //todo test
//    public void terminaOrdine(int IDOrdine) throws SQLException {
//        gestoreOrdini.terminaOrdine(IDOrdine);
//    }




}