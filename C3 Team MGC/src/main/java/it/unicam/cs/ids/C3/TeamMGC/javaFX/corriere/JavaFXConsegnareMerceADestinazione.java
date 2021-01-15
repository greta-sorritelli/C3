package it.unicam.cs.ids.C3.TeamMGC.javaFX.corriere;

import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.negozio.SimpleMerce;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.SimpleMerceOrdine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.SimplePuntoPrelievo;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class JavaFXConsegnareMerceADestinazione implements JavaFXController {

    private final GestoreOrdini gestoreOrdini = GestoreOrdini.getInstance();
    private final GestoreMagazzini gestoreMagazzini = GestoreMagazzini.getInstance();
    private final GestoreCorrieri gestoreCorrieri = GestoreCorrieri.getInstance();
    private int IDCorriere;

    public JavaFXConsegnareMerceADestinazione(int IDCorriere) {
        this.IDCorriere = IDCorriere;
    }

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
    ChoiceBox<SimplePuntoPrelievo> magazziniChoiceBox = new ChoiceBox<>();

    /**
     * todo
     */
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
        IDMerce.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(0)));
        IDOrdineMerce.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(1)));
        PrezzoMerce.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(2)));
        DescrizioneMerce.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(3)));
        QuantitaMerce.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(4)));
    }

    public void setStatoMerce(int IDMerce, StatoOrdine statoOrdine){
        //todo stato in deposito se la destinazione è PP
        //todo stato ritirato se nella residenza il cliente ha ririrato
        //todo stato in transito quando il corriere la trasporta
        //todo stato in deposito se il cliente non c'era a casa e lo portiamo al magazzino vicino
    }

    public void confermaConsegna(int IDMerce){
        //todo
    }

    /**
     * Apre la finestra registrarsi sulla piattaforma.
     */
    @FXML
    public void trasportoMerce(){
        openWindow("/TrasportareMerce.fxml", "TrasportareMerce", new JavaFXTrasportareMerce(IDCorriere));
    }

    /**
     * Inserisce i dati delle {@link SimpleMerceOrdine merci}  nella tabella.
     */
    @FXML
    public void visualizzaMerci() {
//        try {
//            setMerceCellValueFactory();
//            merceTable.getItems().clear();
//            for (SimpleMerceOrdine m : gestoreCorrieri.getMerceAffidata(IDCorriere))
//                merceTable.getItems().add(m.getDettagli());
//        } catch (SQLException exception) {
//            errorWindow("Error!", "Errore nel DB.");
//        }
//    }
    }


}
