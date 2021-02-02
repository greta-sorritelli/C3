package it.unicam.cs.ids.C3.TeamMGC.javaFX.magazziniere;

import it.unicam.cs.ids.C3.TeamMGC.corriere.Corriere;
import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.negozio.GestoreNegozi;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controller della pagina per comunicare con il Corriere.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class JavaFXComunicareConCorriere implements JavaFXController {
    private final GestoreCorrieri gestoreCorrieri = GestoreCorrieri.getInstance();
    private final GestoreNegozi gestoreNegozi = GestoreNegozi.getInstance();
    private final GestoreOrdini gestoreOrdini = GestoreOrdini.getInstance();
    private final int IDPuntoPrelievo;
    private final ArrayList<Negozio> negoziSelezionati = new ArrayList<>();

    @FXML
    TabPane tab;
    @FXML
    Tab tabCorriere;
    @FXML
    Tab tabNegozi;

    /**
     * Tabella dei corrieri
     */
    @FXML
    TableView<ArrayList<String>> corrieriTable;
    @FXML
    TableColumn<ArrayList<String>, String> IDCorriere;
    @FXML
    TableColumn<ArrayList<String>, String> NomeCorriere;
    @FXML
    TableColumn<ArrayList<String>, String> CognomeCorriere;

    /**
     * Tabella dei negozi
     */
    @FXML
    TableView<ArrayList<String>> negoziTable;
    @FXML
    TableColumn<ArrayList<String>, String> IDNegozio;
    @FXML
    TableColumn<ArrayList<String>, String> NomeNegozio;
    @FXML
    TableColumn<ArrayList<String>, String> CategoriaNegozio;
    @FXML
    TableColumn<ArrayList<String>, String> AperturaNegozio;
    @FXML
    TableColumn<ArrayList<String>, String> ChiusuraNegozio;
    @FXML
    TableColumn<ArrayList<String>, String> IndirizzoNegozio;
    @FXML
    TableColumn<ArrayList<String>, String> TelefonoNegozio;

    private Corriere corriereSelezionato;

    public JavaFXComunicareConCorriere(int IDPuntoPrelievo) {
        this.IDPuntoPrelievo = IDPuntoPrelievo;
    }

    @FXML
    public void backToCorrieri() {
        corrieriTable.getSelectionModel().select(null);
        tabCorriere.setDisable(false);
        tab.getSelectionModel().select(tabCorriere);
        tabNegozi.setDisable(true);
    }

    @FXML
    public void conferma() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Attendere...");
        alert.setContentText("Invio Alert in corso.");
        selezionaNegozio(alert);
        confermaAssegnazione(alert);
    }

    private void confermaAssegnazione(Alert alert) {
        try {
            if (!negoziTable.getSelectionModel().isEmpty()) {
                gestoreCorrieri.mandaAlert(corriereSelezionato.getID(), negoziSelezionati);
                ArrayList<ArrayList<String>> merci = new ArrayList<>();
                for (Negozio negozio : negoziSelezionati)
                    merci.addAll(gestoreOrdini.getMerciFromNegozioToMagazzino(negozio.getID(), IDPuntoPrelievo));
                for (ArrayList<String> m : merci)
                    gestoreOrdini.setStatoMerce(Integer.parseInt(m.get(0)), StatoOrdine.CORRIERE_SCELTO);
                alert.close();
                successWindow("Alert mandato con successo", "Il corriere e' stato avvisato.");
                visualizzaNegozi();
            }
        } catch (SQLException e) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    @FXML
    public void selezionaCorriere() {
        try {
            if (!corrieriTable.getSelectionModel().isEmpty()) {
                int id = Integer.parseInt(corrieriTable.getSelectionModel().getSelectedItem().get(0));
                if (gestoreCorrieri.getItem(id) != null) {
                    this.corriereSelezionato = gestoreCorrieri.getItem(id);
                    tabNegozi.setDisable(false);
                    tab.getSelectionModel().select(tabNegozi);
                    tabCorriere.setDisable(true);
                }
            } else {
                alertWindow("Impossibile proseguire", "Selezionare un corriere.");
            }
        } catch (SQLException e) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    private void selezionaNegozio(Alert alert) {
        try {
            if (!negoziTable.getSelectionModel().isEmpty()) {
                alert.show();
                ArrayList<ArrayList<String>> sel = new ArrayList<>(negoziTable.getSelectionModel().getSelectedItems());
                for (ArrayList<String> negozio : sel)
                    if (negozio != null) {
                        int id = Integer.parseInt(negozio.get(0));
                        this.negoziSelezionati.add(gestoreNegozi.getItem(id));
                    }
                sel.clear();
            } else
                alertWindow("Impossibile proseguire", "Selezionare uno o piu' negozi.");
        } catch (SQLException e) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    /**
     * Collega i campi del Corriere alle colonne della tabella.
     */
    private void setCorriereCellValueFactory() {
        corrieriTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        IDCorriere.setCellValueFactory(corriere -> new SimpleObjectProperty<>(corriere.getValue().get(0)));
        NomeCorriere.setCellValueFactory(corriere -> new SimpleObjectProperty<>(corriere.getValue().get(1)));
        CognomeCorriere.setCellValueFactory(corriere -> new SimpleObjectProperty<>(corriere.getValue().get(2)));
    }

    /**
     * Collega i campi del Negozio alle colonne della tabella.
     */
    private void setNegozioCellValueFactory() {
        negoziTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        IDNegozio.setCellValueFactory(negozio -> new SimpleObjectProperty<>(negozio.getValue().get(0)));
        NomeNegozio.setCellValueFactory(negozio -> new SimpleObjectProperty<>(negozio.getValue().get(1)));
        CategoriaNegozio.setCellValueFactory(negozio -> new SimpleObjectProperty<>(negozio.getValue().get(2)));
        AperturaNegozio.setCellValueFactory(negozio -> new SimpleObjectProperty<>(negozio.getValue().get(3)));
        ChiusuraNegozio.setCellValueFactory(negozio -> new SimpleObjectProperty<>(negozio.getValue().get(4)));
        IndirizzoNegozio.setCellValueFactory(negozio -> new SimpleObjectProperty<>(negozio.getValue().get(5)));
        TelefonoNegozio.setCellValueFactory(negozio -> new SimpleObjectProperty<>(negozio.getValue().get(6)));
    }

    @FXML
    public void visualizzaCorrieri() {
        try {
            setCorriereCellValueFactory();
            corrieriTable.getItems().clear();
            corrieriTable.getItems().addAll(gestoreCorrieri.getDettagliCorrieriDisponibili());
        } catch (SQLException e) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    @FXML
    public void visualizzaNegozi() {
        try {
            setNegozioCellValueFactory();
            negoziTable.getItems().clear();
            negoziSelezionati.clear();
            negoziTable.getItems().addAll(gestoreNegozi.getDettagliItemsConOrdini(IDPuntoPrelievo));
            if (negoziTable.getItems().isEmpty()) {
                alertWindow("Alert!", "Non ci sono negozi in cui prelevare merci.");
                closeWindow((Stage) negoziTable.getScene().getWindow());
            }
        } catch (SQLException e) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }
}
