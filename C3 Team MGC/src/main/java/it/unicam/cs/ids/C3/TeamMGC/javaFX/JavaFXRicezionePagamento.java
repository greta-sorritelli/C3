package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Merce;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.MerceOrdine;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class JavaFXRicezionePagamento implements JavaFXController {

    private final Negozio negozio;
    private final GestoreOrdini gestoreOrdini;
    private final GestoreClienti gestoreClienti;

    public JavaFXRicezionePagamento(Negozio negozio, GestoreOrdini gestoreOrdini, GestoreClienti gestoreClienti) {
        this.negozio = negozio;
        this.gestoreOrdini = gestoreOrdini;
        this.gestoreClienti = gestoreClienti;
    }

    @FXML
    TextField IDCliente;

    @FXML
    TextField ordineTextField;

    @FXML
    ChoiceBox<Merce> merceChoiceBox = new ChoiceBox<>();

    @FXML
    TextField quantita;

    /**
     * Tabella della merce inserita.
     */
    @FXML
    TableView<ArrayList<String>> merceTable;

    @FXML
    TableColumn<ArrayList<String>, String> IDMerce;

    @FXML
    TableColumn<ArrayList<String>, String> IDNegozioMerce;

    @FXML
    TableColumn<ArrayList<String>, String> PrezzoMerce;

    @FXML
    TableColumn<ArrayList<String>, String> DescrizioneMerce;

    @FXML
    TableColumn<ArrayList<String>, String> QuantitaMerce;

    /**
     * Collega i campi della Merce inserita alle colonne della tabella.
     */
    private void setMerceCellValueFactory() {
        IDMerce.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(0)));
        IDNegozioMerce.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(1)));
        PrezzoMerce.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(2)));
        DescrizioneMerce.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(3)));
        QuantitaMerce.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(4)));
    }

    /**
     * todo
     */
    @FXML
    public void showMerce() {
        try {
            merceChoiceBox.getItems().clear();
            merceChoiceBox.setItems(FXCollections.observableArrayList(negozio.getMerceDisponibile()));
        } catch (Exception exception) {
            errorWindow("Errore!", "Error.");
        }
    }

    @FXML
    public void updateMerceChoiceBox() {
        if (Objects.isNull(merceChoiceBox.getValue())) {
            showMerce();
        }
    }

    @FXML
    public void registraOrdine() {
        try {
            int ID = Integer.parseInt(IDCliente.getText());
            ArrayList<String> dettagliOrdine = gestoreOrdini.registraOrdine(ID, gestoreClienti.getItem(ID).getNome(),
                    gestoreClienti.getItem(ID).getCognome());
            ordineTextField.setText(dettagliOrdine.get(0));
        } catch (Exception exception) {
            errorWindow("Errore!", "Error");
        }
    }

    @FXML
    public void registraMerce() {
        try {
            if (ordineTextField.getText().isEmpty()) {
                registraOrdine();
            }
            gestoreOrdini.registraMerce(merceChoiceBox.getValue().getID(), Integer.parseInt(quantita.getText()), Integer.parseInt(ordineTextField.getText()));
            visualizzaMerci();
            quantita.clear();
            merceChoiceBox.getItems().clear();
            IDCliente.setEditable(false);
            successWindow("Merce aggiunta con successo!", "La merce e' stata aggiunta all'ordine creato.");
            merceChoiceBox.getItems().clear();
            quantita.clear();
        } catch (Exception exception) {
            errorWindow("Quantita non corretta!", "La quantita inserita e' maggiore di quella presente in negozio.");
            quantita.clear();

        }
    }

    @FXML
    public void terminaOrdine() {
        try {
            gestoreOrdini.terminaOrdine(Integer.parseInt(ordineTextField.getText()));
            successWindow("Ordine terminato con successo!", "Lo stato dell' ordine e' stato impostato a pagato.");
            Stage stage = (Stage) IDCliente.getScene().getWindow();
            closeWindow(stage);
        } catch (Exception exception) {
            errorWindow("L' ordine non puo' essere terminato!", "Inserire i dati richiesti.");
        }
    }

    /**
     * Inserisce i dati delle {@link MerceOrdine merci}  nella tabella.
     */
    @FXML
    public void visualizzaMerci() {
        try {
            setMerceCellValueFactory();
            merceTable.getItems().clear();
            for (MerceOrdine m : gestoreOrdini.getOrdine(Integer.parseInt(ordineTextField.getText())).getMerci())
                merceTable.getItems().add(m.getDettagli());
    } catch (Exception exception) {
            errorWindow("Errore!", "Error.");
        }
    }
}
