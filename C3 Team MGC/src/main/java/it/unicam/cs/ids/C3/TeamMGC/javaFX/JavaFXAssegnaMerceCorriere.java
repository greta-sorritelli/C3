package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.corriere.Corriere;
import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.MerceOrdine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class JavaFXAssegnaMerceCorriere {

    private final GestoreCorrieri gestoreCorrieri;
    private final GestoreMagazzini gestoreMagazzini;
    private final GestoreOrdini gestoreOrdini;
    private Corriere selectedCorriere;
    private PuntoPrelievo selectedMagazzino;
    private ArrayList<MerceOrdine> selectedMerce;

    public JavaFXAssegnaMerceCorriere(GestoreCorrieri gestoreCorrieri, GestoreMagazzini gestoreMagazzini, GestoreOrdini gestoreOrdini) {
        this.gestoreCorrieri = gestoreCorrieri;
        this.gestoreMagazzini = gestoreMagazzini;
        this.gestoreOrdini = gestoreOrdini;
        this.selectedCorriere = null;
        this.selectedMagazzino = null;
    }


    /**
     * TabPane della finestra
     */
    @FXML
    TabPane tab;

    @FXML
    Tab corrieri;

    @FXML
    Tab puntiPrelievo;

    @FXML
    Tab merceOrdine;

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
    Label capienza;

    /**
     * Collega i campi del Corriere alle colonne della tabella.
     */
    private void setCorriereCellValueFactory() {
        IDCorriere.setCellValueFactory(corriere -> new SimpleObjectProperty<>(corriere.getValue().get(0)));
        NomeCorriere.setCellValueFactory(corriere -> new SimpleObjectProperty<>(corriere.getValue().get(1)));
        CognomeCorriere.setCellValueFactory(corriere -> new SimpleObjectProperty<>(corriere.getValue().get(2)));
    }

    /**
     * Collega i campi del PuntoPrelievo alle colonne della tabella.
     */
    private void setPuntoPrelievoCellValueFactory() {
        IDMagazzino.setCellValueFactory(puntoPrelievo -> new SimpleObjectProperty<>(puntoPrelievo.getValue().get(0)));
        NomeMagazzino.setCellValueFactory(puntoPrelievo -> new SimpleObjectProperty<>(puntoPrelievo.getValue().get(1)));
        IndirizzoMagazzino.setCellValueFactory(puntoPrelievo -> new SimpleObjectProperty<>(puntoPrelievo.getValue().get(2)));
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
            exception.printStackTrace();
        }
    }


    @FXML
    public void setSelectedCorriere() throws SQLException {
        if (!corriereTable.getSelectionModel().isEmpty()) {
            int id = Integer.parseInt(corriereTable.getSelectionModel().getSelectedItem().get(0));
            if (gestoreCorrieri.getItem(id) != null) {
                this.selectedCorriere = gestoreCorrieri.getItem(id);
                puntiPrelievo.setDisable(false);
                tab.getSelectionModel().select(puntiPrelievo);
                corrieri.setDisable(true);
                merceOrdine.setDisable(true);
            }
        } else {
            //todo rivedere
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Impossibile proseguire");
            alert.setContentText("Selezionare un corriere.");
            alert.showAndWait();
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
            exception.printStackTrace();
        }
    }

    //todo
    public void selezionaCorriere(int ID) throws SQLException {
        gestoreCorrieri.selezionaCorriere(ID);
    }

    //todo
    public void sceltaPuntoPrelievo(int ID) throws SQLException {
        gestoreMagazzini.sceltaPuntoPrelievo(ID);
    }


    @FXML
    public void setSelectedPuntoPrelievo() throws SQLException {
        if (!magazzinoTable.getSelectionModel().isEmpty()) {
            int id = Integer.parseInt(magazzinoTable.getSelectionModel().getSelectedItem().get(0));
            if (gestoreMagazzini.getItem(id) != null) {
                this.selectedMagazzino = gestoreMagazzini.getItem(id);
                merceOrdine.setDisable(false);
                tab.getSelectionModel().select(merceOrdine);
                corrieri.setDisable(true);
                puntiPrelievo.setDisable(true);
            }
        } else {
            //todo rivedere
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Impossibile proseguire");
            alert.setContentText("Selezionare un punto di prelievo.");
            alert.showAndWait();
        }
    }

    @FXML
    public void backToCorrieri() {
        corriereTable.getSelectionModel().select(null);
        corrieri.setDisable(false);
        tab.getSelectionModel().select(corrieri);
        merceOrdine.setDisable(true);
        puntiPrelievo.setDisable(true);
    }


    public void visualizzaMerce() throws SQLException {
        try {
            setMerceOrdineCellValueFactory();
            merceOrdineTable.getItems().clear();
            merceOrdineTable.getItems().addAll(gestoreOrdini.getDettagliMerce(StatoOrdine.PAGATO));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void backToMagazzini() {
        corriereTable.getSelectionModel().select(null);
        magazzinoTable.getSelectionModel().select(null);
        puntiPrelievo.setDisable(false);
        tab.getSelectionModel().select(puntiPrelievo);
        merceOrdine.setDisable(true);
        corrieri.setDisable(true);
    }


    @FXML
    public void confermaButton() throws SQLException {
        selezionaMerce();
        confermaAssegnazioneMerce();
    }


    private void selezionaMerce() throws SQLException {
        if (!merceOrdineTable.getSelectionModel().isEmpty()) {
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
        } else {
            //todo rivedere
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Impossibile proseguire");
            alert.setContentText("Selezionare la merce da affidare al corriere.");
            alert.showAndWait();
        }
    }


    //todo
    private void confermaAssegnazioneMerce() throws SQLException {
        for (MerceOrdine merce : selectedMerce) {
            if (merce != null) {
                gestoreOrdini.setStatoMerce(merce.getID(), StatoOrdine.AFFIDATO_AL_CORRIERE);
            }
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Assegnazione eseguita con successo");
        alert.setContentText("La merce selezionata è stata affidata al corriere.");
        alert.showAndWait();

        //todo aggiorna tabella
//        visualizzaMerce();
    }
}