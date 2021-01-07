package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.negozio.GestoreNegozi;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class IMagazziniere {

    private GestoreClienti gestoreClienti = new GestoreClienti();
    private final GestoreNegozi gestoreNegozi = new GestoreNegozi();
    private final GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();

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
//
//    /**
//     * @param IDOrdine
//     * @return
//     */
//    public ArrayList<String> getDettagliMerceMagazzino(int IDOrdine) {
//        //todo
//        return null;
//    }
//
//    /**
//     * @param IDOrdine
//     * @return
//     */
//    public ArrayList<String> getDettagliMerceTotale(int IDOrdine) {
//        //todo
//        return null;
//    }
//
//    /**
//     * @param IDCliente
//     * @return
//     */
//    public ArrayList<String> getDettagliOrdine(int IDCliente) {
//        //todo
//        return null;
//    }
//
//    public void mandaAlert() {
//        // TODO - implement Magazziniere.mandaAlert
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     *
//     * @param IDMerce
//     * @param statoOrdine
//     */
//    //todo
//    public void setStato(int IDMerce, StatoOrdine statoOrdine){
//    }
//
//    /**
//     * @param IDCliente
//     * @param codiceRitiro
//     * @return
//     */
//    //todo test
//    public void verificaCodice(int IDCliente, String codiceRitiro) throws SQLException {
//      gestoreClienti.verificaCodice(IDCliente,codiceRitiro);
//    }

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


    //todo creare fxml
//    public void avvisaCorriere(){
//        openWindow("/ComunicareConCorriere.fxml", "ComunicareConCorriere", new JavaFXComunicareConCorriere(gestoreCorrieri,gestoreNegozi));
//
//    }
}