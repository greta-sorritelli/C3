package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.negozio.Merce;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    ChoiceBox<Integer> choiceIDMerce = new ChoiceBox<>();


    @FXML
    private void choiceBox() throws SQLException {
        List<Integer> ID = new ArrayList<>();
        for (Merce m: negozio.getMerceDisponibile()) {
               ID.add(m.getID());
        }
        ObservableList<Integer> IDMerci = FXCollections.observableList(ID);
        choiceIDMerce.setItems(IDMerci);
    }

    @FXML
    public void choiceBoxAppear() throws SQLException {
        if (Objects.isNull(choiceIDMerce.getValue()))
            choiceBox();
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
        negozio.inserisciNuovaMerce(Double.parseDouble(prezzo.getText()),descrizione.getText(), Integer.parseInt(quantita.getText()));
    }

    public void selezionaMerce(int ID) {
        //todo
    }

    public void setQuantita() throws SQLException {
        negozio.setQuantita(choiceIDMerce.getValue(), Integer.parseInt(quantita1.getText()));
    }

    public void removeMerce(int IDMerce){
       //todo
    }

}
