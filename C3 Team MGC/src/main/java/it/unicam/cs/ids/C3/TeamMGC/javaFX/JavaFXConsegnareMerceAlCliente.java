package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
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

    public void setStatoMerce(int IDMerce, StatoOrdine statoOrdine) {
        //todo stato ritirato
    }

    @FXML
    public void verificaCodice() throws SQLException {
        if (gestoreClienti.verificaCodice(Integer.parseInt(IDCliente.getText()), codiceRitiro.getText())) {
            ArrayList<ArrayList<String>> merci = gestoreOrdini.getInDepositMerci(puntoPrelievo.getOrdini(Integer.parseInt(IDCliente.getText())));
            setMerceOrdineCellValueFactory();
            merceOrdineTable.getItems().clear();
            for (ArrayList<String> m : merci) {
                merceOrdineTable.getItems().add(m);
            }
        }

    }


}
