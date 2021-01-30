package it.unicam.cs.ids.C3.TeamMGC.javaFX.cliente;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.negozio.CategoriaNegozio;
import it.unicam.cs.ids.C3.TeamMGC.negozio.GestoreNegozi;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.ArrayList;

public class JavaFXFiltrarePuntiVendita implements JavaFXController {

    private final GestoreNegozi gestoreNegozi = GestoreNegozi.getInstance();

    /**
     * Tabella dei negozi
     */
    @FXML
    TableView<ArrayList<String>> negoziTable;
    @FXML
    TableColumn<ArrayList<String>, String> IDNegozio;
    @FXML
    TableColumn<ArrayList<String>, String> nomeNegozio;
    @FXML
    TableColumn<ArrayList<String>, String> orarioA;
    @FXML
    TableColumn<ArrayList<String>, String> orarioC;
    @FXML
    TableColumn<ArrayList<String>, String> indirizzoNegozio;
    @FXML
    TableColumn<ArrayList<String>, String> telefonoNegozio;

    @FXML
    ChoiceBox<CategoriaNegozio> categorieChoiceBox = new ChoiceBox<>();

    /**
     * Collega i campi del Negozio alle colonne della tabella.
     */
    private void setNegozioCellValueFactory() {
        negoziTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        IDNegozio.setCellValueFactory(negozio -> new SimpleObjectProperty<>(negozio.getValue().get(0)));
        nomeNegozio.setCellValueFactory(negozio -> new SimpleObjectProperty<>(negozio.getValue().get(1)));
        orarioA.setCellValueFactory(negozio -> new SimpleObjectProperty<>(negozio.getValue().get(3)));
        orarioC.setCellValueFactory(negozio -> new SimpleObjectProperty<>(negozio.getValue().get(4)));
        indirizzoNegozio.setCellValueFactory(negozio -> new SimpleObjectProperty<>(negozio.getValue().get(5)));
        telefonoNegozio.setCellValueFactory(negozio -> new SimpleObjectProperty<>(negozio.getValue().get(6)));
    }

    @FXML
    public void showCategorie() {
        categorieChoiceBox.getItems().clear();
        categorieChoiceBox.setItems(FXCollections.observableArrayList(CategoriaNegozio.values()));
    }

    @FXML
    public void visualizzaNegozi() {
        try {
            if (categorieChoiceBox.getValue() == null)
                throw new NullPointerException("Categoria non presente");
            if (gestoreNegozi.getDettagliItems(categorieChoiceBox.getValue()).isEmpty())
                throw new NullPointerException("No negozi");
            setNegozioCellValueFactory();
            negoziTable.getItems().clear();
            negoziTable.getItems().addAll(gestoreNegozi.getDettagliItems(categorieChoiceBox.getValue()));
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (NullPointerException exception) {
            if (exception.getMessage().equals("No negozi")) {
                alertWindow("Non ci sono negozi con questa categoria.", "Effettuare una nuova ricerca.");
                categorieChoiceBox.getItems().clear();
                negoziTable.getItems().clear();
            }
            if (exception.getMessage().equals("Categoria non presente"))
                alertWindow("Impossibile procedere!", "Selezionare la categoria.");
        }
    }
}
