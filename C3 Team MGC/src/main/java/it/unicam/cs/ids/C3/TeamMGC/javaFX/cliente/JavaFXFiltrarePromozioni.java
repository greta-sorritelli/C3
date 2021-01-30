package it.unicam.cs.ids.C3.TeamMGC.javaFX.cliente;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.negozio.CategoriaNegozio;
import it.unicam.cs.ids.C3.TeamMGC.negozio.GestoreNegozi;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class JavaFXFiltrarePromozioni implements JavaFXController {

    private final GestoreNegozi gestoreNegozi = GestoreNegozi.getInstance();

    @FXML
    TabPane tab = new TabPane();
    @FXML
    Tab seleziona = new Tab();
    @FXML
    Tab promozioni = new Tab();

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

    /**
     * Tabella delle promozioni
     */
    @FXML
    TableView<ArrayList<String>> promozioniTable;
    @FXML
    TableColumn<ArrayList<String>, String> prodotto;
    @FXML
    TableColumn<ArrayList<String>, String> messaggio;
    @FXML
    TableColumn<ArrayList<String>, String> prezzoAttuale;
    @FXML
    TableColumn<ArrayList<String>, String> prezzoPrecedente;

    @FXML
    ChoiceBox<CategoriaNegozio> categorieChoiceBox = new ChoiceBox<>();

    private Negozio selectedSimpleNegozio = null;

    @FXML
    public void backToNegozi() {
        seleziona.setDisable(false);
        tab.getSelectionModel().select(seleziona);
        promozioni.setDisable(true);
    }

    public void getPromozioni() {
        try {
            setPromozioneCellValueFactory();
            promozioniTable.getItems().clear();
            promozioniTable.getItems().addAll(selectedSimpleNegozio.getDettagliPromozioni());
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    @FXML
    public void reset() {
        categorieChoiceBox.getItems().clear();
        negoziTable.getItems().clear();
    }

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

    /**
     * Collega i campi della Promozione alle colonne della tabella.
     */
    private void setPromozioneCellValueFactory() {

        prodotto.setCellValueFactory(p -> {
            try {
                return new SimpleObjectProperty<>(selectedSimpleNegozio.getItem(Integer.parseInt(p.getValue().get(0))).getDescrizione());
            } catch (SQLException exception) {
                errorWindow("Error!", "Errore nel DB.");
            }
            return null;
        });
        messaggio.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().get(1)));
        prezzoAttuale.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().get(2)));
        prezzoPrecedente.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().get(3)));
    }

    @FXML
    public void showCategorie() {
        categorieChoiceBox.getItems().clear();
        categorieChoiceBox.setItems(FXCollections.observableArrayList(CategoriaNegozio.values()));
    }

    @FXML
    public void updateCategorieChoiceBox() {
        showCategorie();
        categorieChoiceBox.getItems().clear();
        negoziTable.getItems().clear();
    }

    @FXML
    public void visualizzaNegozi() {
        try {
            if (categorieChoiceBox.getValue() == null)
                throw new NullPointerException("Categoria non presente");
            setNegozioCellValueFactory();
            negoziTable.getItems().clear();
            negoziTable.getItems().addAll(gestoreNegozi.getDettagliItemsWithPromozioni(categorieChoiceBox.getValue()));
            if (negoziTable.getItems().isEmpty()) {
                alertWindow("Non ci sono negozi con questa categoria e delle promozioni.", "Effettuare una nuova ricerca.");
                reset();
            }
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (NullPointerException exception) {
            alertWindow("Impossibile procedere!", "Selezionare la categoria.");
        }
    }

    @FXML
    public void visualizzaPromozioni() {
        try {
            if (!negoziTable.getSelectionModel().isEmpty()) {
                int id = Integer.parseInt(negoziTable.getSelectionModel().getSelectedItem().get(0));
                if (gestoreNegozi.getItem(id) != null) {
                    this.selectedSimpleNegozio = gestoreNegozi.getItem(id);
                    seleziona.setDisable(true);
                    promozioni.setDisable(false);
                    tab.getSelectionModel().select(promozioni);
                    getPromozioni();
                }
            } else {
                alertWindow("Impossibile proseguire", "Selezionare un negozio.");
            }
        } catch (SQLException e) {
            errorWindow("Error!", "Errore nel DB.");
        }

    }
}