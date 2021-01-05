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
    TableColumn<Corriere,Integer> IDMagazzino;

    @FXML
    TableColumn<Corriere,String> NomeMagazzino;

    @FXML
    TableColumn<Corriere,String> IndirizzoMagazzino;

    /**
     * Tabella della merceOrdine
     */
    @FXML
    TableView<MerceOrdine> merceOrdineTable;

    @FXML
    TableColumn<Corriere,Integer> IDMerce;

    @FXML
    TableColumn<Corriere,Integer> IDOrdineMerce;

    @FXML
    TableColumn<Corriere,Double> PrezzoMerce;

    @FXML
    TableColumn<Corriere,String> DescrizioneMerce;

    @FXML
    TableColumn<Corriere,Integer> QuantitaMerce;

    @FXML
    TableColumn<Corriere, StatoOrdine> StatoMerce;

    /**
     * This method matches the movement fields with the columns of the table.
     */
    private void setCorriereCellValueFactory() {
        IDCorriere.setCellValueFactory(corriere -> new SimpleObjectProperty<>(corriere.getValue().getID()));
        NomeCorriere.setCellValueFactory(corriere -> new SimpleObjectProperty<>(corriere.getValue().getNome()));
//        movementDescriptionColumn.setCellValueFactory(movement -> new SimpleObjectProperty<>(movement.getValue().getDescription()));
//        movementTypeColumn.setCellValueFactory(movement -> new SimpleObjectProperty<>(movement.getValue().type()));
//        movementAccountColumn.setCellValueFactory(movement -> new SimpleObjectProperty<>(movement.getValue().getAccount().getName()));
//        movementTagColumn.setCellValueFactory(movement -> new SimpleObjectProperty<>(movement.getValue().tags()));
//        movementTransactionColumn.setCellValueFactory(movement -> new SimpleObjectProperty<>(movement.getValue().getTransaction()));
    }





}
