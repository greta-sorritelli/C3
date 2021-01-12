package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.negozio.Merce;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class JavaFXGestioneInventario {

    private final Negozio negozio;

    public JavaFXGestioneInventario(Negozio negozio) {
        this.negozio = negozio;
    }

    @FXML
    TextField prezzo;

    @FXML
    TextField descrizione;

    @FXML
    TextField quantita;

    @FXML
    TextField quantita1;

    @FXML
    ChoiceBox<Merce> merceChoiceBox = new ChoiceBox<>();


    @FXML
    public void showMerce() throws SQLException {
        merceChoiceBox.getItems().clear();
        merceChoiceBox.setItems(FXCollections.observableArrayList(negozio.getMerceDisponibile()));
    }

    @FXML
    public void updateMerceChoiceBox() throws SQLException {
        if (Objects.isNull(merceChoiceBox.getValue()))
            showMerce();
    }


    /**
     * Tabella della merce.
     */
    @FXML
    TableView<ArrayList<String>> merceTable;

    @FXML
    TableColumn<ArrayList<String>, String> IDMerce;

    @FXML
    TableColumn<ArrayList<String>, String> IDNegozioMerce;

    @FXML
    TableColumn<ArrayList<String>, String> PrezzoMerce;

    @FXML
    TableColumn<ArrayList<String>, String> DescrizioneMerce;

    @FXML
    TableColumn<ArrayList<String>, String> QuantitaMerce;


    public void inserisciNuovaMerce() throws SQLException {
        negozio.inserisciNuovaMerce(Double.parseDouble(prezzo.getText()), descrizione.getText(), Integer.parseInt(quantita.getText()));
    }

    public void selezionaMerce(int ID) {
        //todo
    }

    public void setQuantita() throws SQLException {
//        negozio.setQuantita(choiceIDMerce.getValue(), Integer.parseInt(quantita1.getText()));
    }

    public void removeMerce(int IDMerce) {
        //todo
    }

}
