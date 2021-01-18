package it.unicam.cs.ids.C3.TeamMGC.javaFX.addettoMagazzino;

import it.unicam.cs.ids.C3.TeamMGC.corriere.Corriere;
import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.MerceOrdine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.SimpleMerceOrdine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class JavaFXAssegnaMerceCorriere implements JavaFXController {

    private final GestoreCorrieri gestoreCorrieri = GestoreCorrieri.getInstance();
    private final GestoreMagazzini gestoreMagazzini = GestoreMagazzini.getInstance();
    private final GestoreOrdini gestoreOrdini = GestoreOrdini.getInstance();
    /**
     * TabPane della finestra
     */
    @FXML
    TabPane tab = new TabPane();
    @FXML
    Tab corrieri = new Tab();
    @FXML
    Tab destinazione = new Tab();
    @FXML
    Tab merceOrdine = new Tab();
    /**
     * Tabella dei corrieri
     */
    @FXML
    TableView<ArrayList<String>> corriereTable;
    @FXML
    TableColumn<ArrayList<String>, String> IDCorriere;
    @FXML
    TableColumn<ArrayList<String>, String> NomeCorriere;
    @FXML
    TableColumn<ArrayList<String>, String> CognomeCorriere;
    /**
     * Tabella dei puntiPrelievo
     */
    @FXML
    TableView<ArrayList<String>> magazzinoTable;
    @FXML
    TableColumn<ArrayList<String>, String> IDMagazzino;
    @FXML
    TableColumn<ArrayList<String>, String> NomeMagazzino;
    @FXML
    TableColumn<ArrayList<String>, String> IndirizzoMagazzino;
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

    @FXML
    TextField residenzaTextField;

    private Corriere selectedSimpleCorriere = null;
    private PuntoPrelievo selectedMagazzino = null;
    private String selectedResidenza;
    private final ArrayList<MerceOrdine> selectedMerce = new ArrayList<>();


    @FXML
    public void backToCorrieri() {
        corriereTable.getSelectionModel().select(null);
        corrieri.setDisable(false);
        tab.getSelectionModel().select(corrieri);
        merceOrdine.setDisable(true);
        destinazione.setDisable(true);
    }

    @FXML
    public void backToMagazzini() {
        corriereTable.getSelectionModel().select(null);
        magazzinoTable.getSelectionModel().select(null);
        destinazione.setDisable(false);
        tab.getSelectionModel().select(destinazione);
        residenzaTextField.clear();
        merceOrdine.setDisable(true);
        corrieri.setDisable(true);
    }

    @FXML
    public void clearPuntiPrelievo() {
        magazzinoTable.getSelectionModel().select(null);
        this.selectedMagazzino = null;
    }

    @FXML
    public void clearResidenza() {
        residenzaTextField.clear();
        this.selectedResidenza = null;
    }

    private void confermaAssegnazioneMerce(Alert alert) {
        try {
            if (!merceOrdineTable.getSelectionModel().isEmpty()) {
                for (MerceOrdine merce : selectedMerce) {
                    if (merce != null) {
                        gestoreOrdini.associaMerceCorriere(selectedSimpleCorriere.getID(), merce.getID());
                    }
                }
                alert.close();
                successWindow("Assegnazione eseguita con successo", "La merce selezionata e' stata affidata al corriere.");
                visualizzaMerce();
            }
        } catch (SQLException e) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    @FXML
    public void confermaButton() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Attendere...");
        alert.setContentText("L'affidamento della merce e' in corso.");
        selezionaMerce(alert);
        confermaAssegnazioneMerce(alert);
    }

    @FXML
    public void selezionaCorriere() {
        try {
            if (!corriereTable.getSelectionModel().isEmpty()) {
                int id = Integer.parseInt(corriereTable.getSelectionModel().getSelectedItem().get(0));
                if (gestoreCorrieri.getItem(id) != null) {
                    this.selectedSimpleCorriere = gestoreCorrieri.getItem(id);
                    destinazione.setDisable(false);
                    tab.getSelectionModel().select(destinazione);
                    corrieri.setDisable(true);
                    merceOrdine.setDisable(true);
                }
            } else {
                alertWindow("Impossibile proseguire", "Selezionare un corriere.");
            }
        } catch (SQLException e) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    @FXML
    public void selezionaDestinazione() {
        try {
            if (residenzaTextField.getText().isEmpty())
                selezionaPuntoPrelievo();
            else
                selezionaResidenza();
            merceOrdine.setDisable(false);
            tab.getSelectionModel().select(merceOrdine);
            corrieri.setDisable(true);
            destinazione.setDisable(true);
        } catch (NullPointerException e) {
            errorWindow("Selezionare una destinazione!", "Seleziona un punto di prelievo o inserisci una residenza.");
        }
    }

    private void selezionaMerce(Alert alert) {
        try {
            if (!merceOrdineTable.getSelectionModel().isEmpty()) {
                alert.show();
                ArrayList<ArrayList<String>> sel = new ArrayList<>(merceOrdineTable.getSelectionModel().getSelectedItems());
                for (ArrayList<String> merce : sel) {
                    if (merce != null) {
                        int id = Integer.parseInt(merce.get(0));
                        if (gestoreOrdini.getMerceOrdine(id) != null) {
                            this.selectedMerce.add(gestoreOrdini.getMerceOrdine(id));
                        }
                    }
                }
                sel.clear();
            } else
                alertWindow("Impossibile proseguire", "Selezionare la merce da affidare al corriere.");
        } catch (SQLException e) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    private void selezionaPuntoPrelievo() {
        try {
            if (!magazzinoTable.getSelectionModel().isEmpty()) {
                int id = Integer.parseInt(magazzinoTable.getSelectionModel().getSelectedItem().get(0));
                PuntoPrelievo puntoPrelievo = gestoreMagazzini.getItem(id);
                if (puntoPrelievo != null) {
                    this.selectedMagazzino = puntoPrelievo;
                }
            } else
                throw new NullPointerException();
        } catch (SQLException e) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    private void selezionaResidenza() {
            if (!residenzaTextField.getText().isEmpty()) {
                this.selectedResidenza = residenzaTextField.getText();
            } else
                throw new NullPointerException();
    }

    /**
     * Collega i campi del Corriere alle colonne della tabella.
     */
    private void setCorriereCellValueFactory() {
        corriereTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        IDCorriere.setCellValueFactory(corriere -> new SimpleObjectProperty<>(corriere.getValue().get(0)));
        NomeCorriere.setCellValueFactory(corriere -> new SimpleObjectProperty<>(corriere.getValue().get(1)));
        CognomeCorriere.setCellValueFactory(corriere -> new SimpleObjectProperty<>(corriere.getValue().get(2)));
    }

    /**
     * Collega i campi della MerceOrdine alle colonne della tabella.
     */
    private void setMerceOrdineCellValueFactory() {
        merceOrdineTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        IDMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(0)));
        IDOrdineMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(1)));
        PrezzoMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(2)));
        DescrizioneMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(3)));
        QuantitaMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(4)));
        StatoMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(5)));
    }

    /**
     * Collega i campi del PuntoPrelievo alle colonne della tabella.
     */
    private void setPuntoPrelievoCellValueFactory() {
        magazzinoTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        IDMagazzino.setCellValueFactory(puntoPrelievo -> new SimpleObjectProperty<>(puntoPrelievo.getValue().get(0)));
        NomeMagazzino.setCellValueFactory(puntoPrelievo -> new SimpleObjectProperty<>(puntoPrelievo.getValue().get(1)));
        IndirizzoMagazzino.setCellValueFactory(puntoPrelievo -> new SimpleObjectProperty<>(puntoPrelievo.getValue().get(2)));
    }

    /**
     * Inserisce i dati dei corrieri nella tabella
     */
    @FXML
    public void visualizzaCorrieri() {
        try {
            setCorriereCellValueFactory();
            corriereTable.getItems().clear();
            corriereTable.getItems().addAll(gestoreCorrieri.getDettagliCorrieriDisponibili());
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalArgumentException e) {
            alertWindow("Corrieri non disponibili.", "Aggiorna piu' tardi.");
        }
    }

    @FXML
    public void visualizzaMerce() {
        try {
            setMerceOrdineCellValueFactory();
            merceOrdineTable.getItems().clear();
            if (selectedMagazzino != null)
                merceOrdineTable.getItems().addAll(gestoreOrdini.getMerciMagazzino(selectedMagazzino.getID()));
            if (selectedResidenza != null)
                merceOrdineTable.getItems().addAll(gestoreOrdini.getDettagliMerciResidenza(residenzaTextField.getText()));
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    /**
     * Inserisce i dati dei punti di prelievo nella tabella
     */
    @FXML
    public void visualizzaPuntiPrelievo() {
        try {
            setPuntoPrelievoCellValueFactory();
            magazzinoTable.getItems().clear();
            magazzinoTable.getItems().addAll(gestoreMagazzini.getDettagliItems());
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }
}