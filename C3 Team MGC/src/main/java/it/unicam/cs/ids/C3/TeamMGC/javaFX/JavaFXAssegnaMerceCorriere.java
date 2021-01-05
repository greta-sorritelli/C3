package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.ArrayList;

public class JavaFXAssegnaMerceCorriere {

    private final GestoreCorrieri gestoreCorrieri;
    private final GestoreMagazzini gestoreMagazzini ;
    private final GestoreOrdini gestoreOrdini ;

    public JavaFXAssegnaMerceCorriere(GestoreCorrieri gestoreCorrieri, GestoreMagazzini gestoreMagazzini, GestoreOrdini gestoreOrdini) {
        this.gestoreCorrieri = gestoreCorrieri;
        this.gestoreMagazzini = gestoreMagazzini;
        this.gestoreOrdini = gestoreOrdini;
    }

    /**
     * Tabella dei corrieri
     */
    @FXML
    TableView<ArrayList<String>> corriereTable;

    @FXML
    TableColumn<ArrayList<String>,String> IDCorriere;

    @FXML
    TableColumn<ArrayList<String>,String> NomeCorriere;

    @FXML
    TableColumn<ArrayList<String>,String> CognomeCorriere;

    @FXML
    TableColumn<ArrayList<String>,String> Capienza;

    /**
     * Tabella dei puntiPrelievo
     */
    @FXML
    TableView<ArrayList<String>> magazzinoTable;

    @FXML
    TableColumn<ArrayList<String>,String> IDMagazzino;

    @FXML
    TableColumn<ArrayList<String>,String> NomeMagazzino;

    @FXML
    TableColumn<ArrayList<String>,String> IndirizzoMagazzino;

    /**
     * Tabella della merceOrdine
     */
    @FXML
    TableView<ArrayList<String>> merceOrdineTable;

    @FXML
    TableColumn<ArrayList<String>,String> IDMerce;

    @FXML
    TableColumn<ArrayList<String>,String> IDOrdineMerce;

    @FXML
    TableColumn<ArrayList<String>,String> PrezzoMerce;

    @FXML
    TableColumn<ArrayList<String>,String> DescrizioneMerce;

    @FXML
    TableColumn<ArrayList<String>,String> QuantitaMerce;

    @FXML
    TableColumn<ArrayList<String>, String> StatoMerce;

    /**
     *  Collega i campi del Corriere alle colonne della tabella.
     */
    private void setCorriereCellValueFactory() {
        IDCorriere.setCellValueFactory(corriere -> new SimpleObjectProperty<>(corriere.getValue().get(0)));
        NomeCorriere.setCellValueFactory(corriere -> new SimpleObjectProperty<>(corriere.getValue().get(1)));
        CognomeCorriere.setCellValueFactory(corriere -> new SimpleObjectProperty<>(corriere.getValue().get(2)));
        Capienza.setCellValueFactory(corriere -> new SimpleObjectProperty<>(corriere.getValue().get(4)));
    }

    /**
     *  Collega i campi del PuntoPrelievo alle colonne della tabella.
     */
    private void setPuntoPrelievoCellValueFactory() {
        IDMagazzino.setCellValueFactory(puntoPrelievo -> new SimpleObjectProperty<>(puntoPrelievo.getValue().get(0)));
        NomeMagazzino.setCellValueFactory(puntoPrelievo -> new SimpleObjectProperty<>(puntoPrelievo.getValue().get(1)));
        IndirizzoMagazzino.setCellValueFactory(puntoPrelievo -> new SimpleObjectProperty<>(puntoPrelievo.getValue().get(2)));
    }

    /**
     *  Collega i campi della MerceOrdine alle colonne della tabella.
     */
    private void setMerceOrdineCellValueFactory() {
        IDMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(0)));
        IDOrdineMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(1)));
        PrezzoMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(2)));
        DescrizioneMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(3)));
        QuantitaMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(4)));
        StatoMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(5)));
    }

    @FXML
    public void visualizzaCorrieri(){
        try {
            setCorriereCellValueFactory();
            corriereTable.getItems().clear();
            corriereTable.getItems().addAll(gestoreCorrieri.getDettagliCorrieriDisponibili());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void visualizzaPuntiPrelievo(){
        try {
            setPuntoPrelievoCellValueFactory();
            magazzinoTable.getItems().clear();
            magazzinoTable.getItems().addAll(gestoreMagazzini.getDettagliItems());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }









}
