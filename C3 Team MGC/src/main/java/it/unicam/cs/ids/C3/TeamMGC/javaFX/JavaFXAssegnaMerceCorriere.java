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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    TableView<Corriere> corriereTable;

    @FXML
    TableColumn<Corriere,Integer> IDCorriere;

    @FXML
    TableColumn<Corriere,String> NomeCorriere;

    @FXML
    TableColumn<Corriere,String> CognomeCorriere;

    @FXML
    TableColumn<Corriere,Integer> Capienza;

    /**
     * Tabella dei puntiPrelievo
     */
    @FXML
    TableView<PuntoPrelievo> magazzinoTable;

    @FXML
    TableColumn<PuntoPrelievo,Integer> IDMagazzino;

    @FXML
    TableColumn<PuntoPrelievo,String> NomeMagazzino;

    @FXML
    TableColumn<PuntoPrelievo,String> IndirizzoMagazzino;

    /**
     * Tabella della merceOrdine
     */
    @FXML
    TableView<MerceOrdine> merceOrdineTable;

    @FXML
    TableColumn<MerceOrdine,Integer> IDMerce;

    @FXML
    TableColumn<MerceOrdine,Integer> IDOrdineMerce;

    @FXML
    TableColumn<MerceOrdine,Double> PrezzoMerce;

    @FXML
    TableColumn<MerceOrdine,String> DescrizioneMerce;

    @FXML
    TableColumn<MerceOrdine,Integer> QuantitaMerce;

    @FXML
    TableColumn<MerceOrdine, StatoOrdine> StatoMerce;

    /**
     *  Collega i campi del Corriere alle colonne della tabella.
     */
    private void setCorriereCellValueFactory() {
        IDCorriere.setCellValueFactory(corriere -> new SimpleObjectProperty<>(corriere.getValue().getID()));
        NomeCorriere.setCellValueFactory(corriere -> new SimpleObjectProperty<>(corriere.getValue().getNome()));
        CognomeCorriere.setCellValueFactory(corriere -> new SimpleObjectProperty<>(corriere.getValue().getCognome()));
        Capienza.setCellValueFactory(corriere -> new SimpleObjectProperty<>(corriere.getValue().getCapienza()));
    }

    /**
     *  Collega i campi del PuntoPrelievo alle colonne della tabella.
     */
    private void setPuntoPrelievoCellValueFactory() {
        IDMagazzino.setCellValueFactory(puntoPrelievo -> new SimpleObjectProperty<>(puntoPrelievo.getValue().getID()));
        NomeMagazzino.setCellValueFactory(puntoPrelievo -> new SimpleObjectProperty<>(puntoPrelievo.getValue().getNome()));
        IndirizzoMagazzino.setCellValueFactory(puntoPrelievo -> new SimpleObjectProperty<>(puntoPrelievo.getValue().getIndirizzo()));
    }

    /**
     *  Collega i campi della MerceOrdine alle colonne della tabella.
     */
    private void setMerceOrdineCellValueFactory() {
        IDMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().getID()));
        IDOrdineMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().getIDOrdine()));
        PrezzoMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().getPrezzo()));
        DescrizioneMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().getDescrizione()));
        QuantitaMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().getQuantita()));
        StatoMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().getStato()));
    }




}
