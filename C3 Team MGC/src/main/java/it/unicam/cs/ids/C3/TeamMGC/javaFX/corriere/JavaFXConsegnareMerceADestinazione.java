package it.unicam.cs.ids.C3.TeamMGC.javaFX.corriere;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.ordine.*;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Controller della pagina per confermare la consegna della Merce.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class JavaFXConsegnareMerceADestinazione implements JavaFXController {
    private final GestoreOrdini gestoreOrdini = GestoreOrdini.getInstance();
    private final GestoreMagazzini gestoreMagazzini = GestoreMagazzini.getInstance();
    private final GestoreClienti gestoreClienti = GestoreClienti.getInstance();
    private final ArrayList<ArrayList<String>> merceSelezionata = new ArrayList<>();
    private final int IDCorriere;

    public JavaFXConsegnareMerceADestinazione(int IDCorriere) {
        this.IDCorriere = IDCorriere;
    }

    /**
     * TabPane della finestra
     */
    @FXML
    TabPane tab = new TabPane();

    @FXML
    Tab ricerca = new Tab();

    @FXML
    Tab merce = new Tab();

    /**
     * Tabella della merce.
     */
    @FXML
    TableView<ArrayList<String>> merceTable;

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
    ChoiceBox<PuntoPrelievo> magazziniChoiceBox = new ChoiceBox<>();

    private MerceOrdine selectedMerce;

    @FXML
    public void showMagazzini() {
        try {
            magazziniChoiceBox.getItems().clear();
            magazziniChoiceBox.setItems(FXCollections.observableArrayList(gestoreMagazzini.ricercaMagazziniVicini()));
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    @FXML
    public void updateMagazziniChoiceBox() {
        if (Objects.isNull(magazziniChoiceBox.getValue())) {
            showMagazzini();
        }
    }

    /**
     * Collega i campi della merce alle colonne della tabella.
     */
    private void setMerceCellValueFactory() {
        merceTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        IDMerce.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(0)));
        IDOrdineMerce.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(1)));
        PrezzoMerce.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(2)));
        DescrizioneMerce.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(3)));
        QuantitaMerce.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(4)));
    }

    public void confermaConsegna() {
        selezionaMerce();
        try {
            if (selectedMerce != null) {
                if (gestoreOrdini.getIDPuntoPrelievoOrdine(selectedMerce.getIDOrdine()) != -1) {
                    mandaAlertPuntoPrelievo();
                    successWindow("Merce consegnata con successo!", "La merce e' stata consegnata al punto di prelievo.", 2);
                } else {
                    gestoreOrdini.setStatoMerce(selectedMerce.getID(), StatoOrdine.RITIRATO);
                    successWindow("Merce consegnata con successo!", "La merce e' stata consegnata al cliente.", 2);
                }
                merceSelezionata.add(selectedMerce.getDettagli());
                selectedMerce = null;
                merceTable.getSelectionModel().select(null);
                visualizzaMerci();
            }
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    private void mandaAlertPuntoPrelievo() throws SQLException {
        Ordine ordine = gestoreOrdini.getOrdine(selectedMerce.getIDOrdine());
        int IDCliente = gestoreOrdini.getIDClienteOrdine(ordine.getID());
        int IDPuntoPrelievo = gestoreOrdini.getIDPuntoPrelievoOrdine((ordine.getID()));
        gestoreOrdini.setStatoMerce(selectedMerce.getID(), StatoOrdine.IN_DEPOSITO);
        gestoreClienti.mandaAlertPuntoPrelievo(IDCliente, gestoreMagazzini.getItem(IDPuntoPrelievo), selectedMerce);
    }

    private void selezionaMerce() {
        try {
            if (!merceTable.getSelectionModel().isEmpty()) {
                int ID = Integer.parseInt(merceTable.getSelectionModel().getSelectedItem().get(0));
                if (gestoreOrdini.getMerceOrdine(ID) != null)
                    this.selectedMerce = gestoreOrdini.getMerceOrdine(ID);
            } else
                alertWindow("Impossibile proseguire", "Selezionare la merce.");
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    public void ricercaMagazzini() {
        selezionaMerce();
        if (selectedMerce != null) {
            tab.getSelectionModel().select(ricerca);
            ricerca.setDisable(false);
            merce.setDisable(true);
        }
    }

    public void mandaAlert() {
        try {
            if (magazziniChoiceBox.getValue() != null) {
                Ordine ordine = gestoreOrdini.getOrdine(selectedMerce.getIDOrdine());
                int IDCliente = gestoreOrdini.getIDClienteOrdine(ordine.getID());
                gestoreClienti.mandaAlertResidenza(IDCliente, magazziniChoiceBox.getValue(), selectedMerce);
                successWindow("Destinazione cambiata con successo!", "La merce dovra' essere consegnata al punto di prelievo.", 2);
                magazziniChoiceBox.getItems().clear();
                merceSelezionata.add(selectedMerce.getDettagli());
                selectedMerce = null;
                merceTable.getSelectionModel().select(null);
                backToMerci();
            } else
                alertWindow("Impossibile proseguire", "Selezionare il punto di prelievo.");
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }

    }

    @FXML
    public void backToMerci() {
        selectedMerce = null;
        merceTable.getSelectionModel().select(null);
        tab.getSelectionModel().select(merce);
        merce.setDisable(false);
        ricerca.setDisable(true);
        magazziniChoiceBox.getItems().clear();

    }

    /**
     * Inserisce i dati delle {@link SimpleMerceOrdine merci}  nella tabella.
     */
    @FXML
    public void visualizzaMerci() {
        try {
            setMerceCellValueFactory();
            merceTable.getItems().clear();
            merceTable.getItems().addAll(gestoreOrdini.getDettagliMerciOfCorriere(IDCorriere, StatoOrdine.IN_TRANSITO));
            merceTable.getItems().removeIf(merceSelezionata::contains);
            if (merceTable.getItems().isEmpty()) {
                successWindow("Merci consegnate con successo!", "Tutte le merci sono state consegnate.", 2);
                closeWindow((Stage) tab.getScene().getWindow());
            }
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }
}



