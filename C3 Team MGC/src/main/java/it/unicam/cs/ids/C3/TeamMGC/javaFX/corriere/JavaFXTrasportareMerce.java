
package it.unicam.cs.ids.C3.TeamMGC.javaFX.corriere;

import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.ArrayList;

public class JavaFXTrasportareMerce implements JavaFXController {

    private final GestoreOrdini gestoreOrdini = GestoreOrdini.getInstance();
    private final GestoreCorrieri gestoreCorrieri = GestoreCorrieri.getInstance();
    private int IDCorriere;
    private final ArrayList<ArrayList<String>> merceDaTrasportare = new ArrayList<>();

    public JavaFXTrasportareMerce(int id) {
        this.IDCorriere = id;
    }

    @FXML
    TableView<ArrayList<String>> merceTable;

    @FXML
    TableColumn<ArrayList<String>, String> merceID;

    @FXML
    TableColumn<ArrayList<String>, String> merceIDOrdine;

    @FXML
    TableColumn<ArrayList<String>, String> merceDescrizione;

    @FXML
    TableColumn<ArrayList<String>, String> mercePrezzo;

    @FXML
    TableColumn<ArrayList<String>, String> merceQuantita;

    private void setCorriereCellValueFactory() {
        merceTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        merceID.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(0)));
        merceIDOrdine.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(1)));
        merceDescrizione.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(2)));
        mercePrezzo.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(3)));
        merceQuantita.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(4)));
    }

    @FXML
    public void visualizzaMerce() {
        try {
            setCorriereCellValueFactory();
            merceTable.getItems().clear();
            merceTable.getItems().addAll(getDettagliMerce(StatoOrdine.AFFIDATO_AL_CORRIERE));
        } catch (NullPointerException e) {
            alertWindow("Merci non disponibili.", "Nessuna merce affidata.");
        }
    }

    @FXML
    public void setStatoInTransito() {
        if (!merceTable.getSelectionModel().getSelectedItems().isEmpty())
            for (ArrayList<String> merce : merceTable.getSelectionModel().getSelectedItems())
                setStatoMerce(Integer.parseInt(merce.get(0)));
        else
            alertWindow("Nessuna merce selezionata.", "Selezionare almeno una merce.");
    }

    private ArrayList<ArrayList<String>> getDettagliMerce(StatoOrdine statoOrdine) {
        // todo stato affidato al corriere
        try {
            return gestoreCorrieri.getItem(IDCorriere).getDettagliMerceAffidata();
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }
        return null;
    }

    private void setStatoMerce(int IDMerce) {
        try {
            gestoreOrdini.setStatoMerce(IDMerce, StatoOrdine.IN_TRANSITO);
        } catch (SQLException e) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

}
