package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.ArrayList;

public class JavaFXConsegnareMerceAlCliente {

    private final PuntoPrelievo puntoPrelievo;
    private final GestoreOrdini gestoreOrdini;
    private final GestoreClienti gestoreClienti;

    @FXML
    TextField IDCliente;
    @FXML
    TextField codiceRitiro;

    /**
     * Tabella della merceOrdine
     */
    @FXML
    TableView<ArrayList<String>> merceOrdineTable;
    @FXML
    TableColumn<ArrayList<String>, String> IDMerce;
    @FXML
    TableColumn<ArrayList<String>, String> IDOrdineMerce;
    @FXML
    TableColumn<ArrayList<String>, String> PrezzoMerce;
    @FXML
    TableColumn<ArrayList<String>, String> DescrizioneMerce;
    @FXML
    TableColumn<ArrayList<String>, String> QuantitaMerce;
    @FXML
    TableColumn<ArrayList<String>, String> StatoMerce;

    public JavaFXConsegnareMerceAlCliente(PuntoPrelievo puntoPrelievo, GestoreOrdini gestoreOrdini, GestoreClienti gestoreClienti) {
        this.puntoPrelievo = puntoPrelievo;
        this.gestoreOrdini = gestoreOrdini;
        this.gestoreClienti = gestoreClienti;
    }

    /**
     * Collega i campi della MerceOrdine alle colonne della tabella.
     */
    private void setMerceOrdineCellValueFactory() {
        IDMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(0)));
        IDOrdineMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(1)));
        PrezzoMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(2)));
        DescrizioneMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(3)));
        QuantitaMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(4)));
        StatoMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(5)));
    }

    public void setStatoMerce() {
        try {
            if (!merceOrdineTable.getItems().isEmpty()) {
                for (ArrayList<String> merce : merceOrdineTable.getItems())
                    gestoreOrdini.setStatoMerce(Integer.parseInt(merce.get(0)), StatoOrdine.RITIRATO);
                merceOrdineTable.getItems().clear();
                IDCliente.clear();
                codiceRitiro.clear();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Consegna merce eseguita con successo!");
                alert.setContentText("La merce e' stata consegnata al cliente.");
                alert.showAndWait();
            } else
                throw new IllegalArgumentException("Merce non presente nella tabella.");
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error!");
            alert.showAndWait();
        }
    }

    @FXML
    public void verificaCodice() {
        try {
            if (IDCliente.getText().equals("") ||  codiceRitiro.getText().equals(""))
                throw new NullPointerException("Codice non valido");

            if (gestoreClienti.verificaCodice(Integer.parseInt(IDCliente.getText()), codiceRitiro.getText())) {
                ArrayList<ArrayList<String>> merci = gestoreOrdini.getInDepositMerci(puntoPrelievo.getOrdini(Integer.parseInt(IDCliente.getText())));
                setMerceOrdineCellValueFactory();
                merceOrdineTable.getItems().clear();
                for (ArrayList<String> m : merci) {
                    merceOrdineTable.getItems().add(m);
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Verifica codice eseguita con successo!");
                alert.setContentText("Il codice inserito appartiene al cliente.");
                alert.showAndWait();
                if(merci.isEmpty()) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setContentText("Il cliente non ha merci da ritirare.");
                    alert1.showAndWait();
                    IDCliente.clear();
                    codiceRitiro.clear();
                }
            } else
                throw new IllegalArgumentException("Parametri non presenti.");
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Inserisci tutti i dati richiesti!");
            alert.showAndWait();
            IDCliente.clear();
            codiceRitiro.clear();
        } catch (IllegalArgumentException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Il codice non appartiene al cliente!");
            alert.showAndWait();
            IDCliente.clear();
            codiceRitiro.clear();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }


}
