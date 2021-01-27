package it.unicam.cs.ids.C3.TeamMGC.javaFX.amministratore;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class JavaFXGestionePuntiPrelievo implements JavaFXController {
    private final GestoreMagazzini gestoreMagazzini = GestoreMagazzini.getInstance();
    private final ArrayList<PuntoPrelievo> puntiSelezionati = new ArrayList<>();

    @FXML
    TabPane tab = new TabPane();
    @FXML
    Tab registra = new Tab();
    @FXML
    Tab elimina = new Tab();
    @FXML
    TextField nome;
    @FXML
    TextField indirizzo;

    /**
     * Tabella dei punti prelievo
     */
    @FXML
    TableView<ArrayList<String>> puntiPrelievoTable;
    @FXML
    TableColumn<ArrayList<String>, String> IDPuntoPrelievo;
    @FXML
    TableColumn<ArrayList<String>, String> nomePuntoPrelievo;
    @FXML
    TableColumn<ArrayList<String>, String> indirizzoPuntoPrelievo;

    /**
     * Collega i campi del Punto di prelievo alle colonne della tabella.
     */
    private void setPuntoPrelievoCellValueFactory() {
        puntiPrelievoTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        IDPuntoPrelievo.setCellValueFactory(puntoPrelievo -> new SimpleObjectProperty<>(puntoPrelievo.getValue().get(0)));
        nomePuntoPrelievo.setCellValueFactory(puntoPrelievo -> new SimpleObjectProperty<>(puntoPrelievo.getValue().get(1)));
        indirizzoPuntoPrelievo.setCellValueFactory(puntoPrelievo -> new SimpleObjectProperty<>(puntoPrelievo.getValue().get(2)));
    }

    @FXML
    public void registraPuntoPrelievo() {
        try {
            if (nome.getText().isEmpty() || indirizzo.getText().isEmpty())
                throw new IllegalArgumentException("Dati non presenti");
            gestoreMagazzini.inserisciPuntoPrelievo(nome.getText(), indirizzo.getText());
            successWindow("Success!", "Punto prelievo registrato con successo.");
            nome.clear();
            indirizzo.clear();
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalArgumentException exception) {
            alertWindow("Alert!", "Inserire tutti i dati richiesti.");
        }
    }

    @FXML
    private void eliminaPuntiPrelievo() {
        try {
            if (!puntiPrelievoTable.getSelectionModel().isEmpty()) {
                ArrayList<ArrayList<String>> sel = new ArrayList<>(puntiPrelievoTable.getSelectionModel().getSelectedItems());
                for (ArrayList<String> puntoP : sel)
                    if (puntoP != null) {
                        int id = Integer.parseInt(puntoP.get(0));
                        this.puntiSelezionati.add(gestoreMagazzini.getItem(id));
                    }
                for (PuntoPrelievo p : puntiSelezionati)
                    gestoreMagazzini.removePuntoPrelievo(p.getID());
                successWindow("Success!", "Punti prelievo eliminati con successo.");
                sel.clear();
                visualizzaPuntiPrelievo();
            } else
                alertWindow("Impossibile proseguire", "Selezionare uno o piu' punti di prelievo.");
        } catch (SQLException e) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    @FXML
    public void visualizzaPuntiPrelievo() {
        try {
            setPuntoPrelievoCellValueFactory();
            puntiPrelievoTable.getItems().clear();
            puntiSelezionati.clear();
            puntiPrelievoTable.getItems().addAll(gestoreMagazzini.getDettagliItems());
            if (puntiPrelievoTable.getItems().isEmpty()) {
                tab.getSelectionModel().select(registra);
                alertWindow("Alert!", "Non ci sono punti di prelievo.");
            }
        } catch (SQLException e) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }
}
