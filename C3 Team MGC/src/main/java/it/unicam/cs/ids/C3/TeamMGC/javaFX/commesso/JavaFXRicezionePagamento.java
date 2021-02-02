package it.unicam.cs.ids.C3.TeamMGC.javaFX.commesso;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Merce;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.MerceOrdine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.SimpleMerceOrdine;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Controller della pagina per la ricezione del Pagamento e terminare l' Ordine.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class JavaFXRicezionePagamento implements JavaFXController {

    private final Negozio negozio;
    private final GestoreOrdini gestoreOrdini = GestoreOrdini.getInstance();
    private final GestoreClienti gestoreClienti = GestoreClienti.getInstance();
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
    TableColumn<ArrayList<String>, String> IDOrdineMerce;
    @FXML
    TableColumn<ArrayList<String>, String> PrezzoMerce;
    @FXML
    TableColumn<ArrayList<String>, String> DescrizioneMerce;
    @FXML
    TableColumn<ArrayList<String>, String> QuantitaMerce;

    public JavaFXRicezionePagamento(Negozio negozio) {
        this.negozio = negozio;
    }

    public String getIDCliente() {
        if (IDCliente.getText().matches(".*[a-zA-Z]+.*"))
            throw new IllegalArgumentException("ID non valido.");
        return IDCliente.getText();
    }

    public String getQuantita() {
        if (quantita.getText().matches(".*[a-zA-Z]+.*"))
            throw new IllegalArgumentException("Formato quantita' non valido.");
        return quantita.getText();
    }

    @FXML
    public void registraMerce() {
        try {
            if (getIDCliente().isEmpty() || merceChoiceBox.getValue() == null || getQuantita().isEmpty())
                throw new NullPointerException("Dati non presenti.");
            if (ordineTextField.getText().isEmpty()) {
                registraOrdine();
            }
            gestoreOrdini.registraMerce(merceChoiceBox.getValue().getID(), Integer.parseInt(quantita.getText()), Integer.parseInt(ordineTextField.getText()), negozio);
            visualizzaMerci();
            quantita.clear();
            merceChoiceBox.getItems().clear();
            IDCliente.setEditable(false);
            successWindow("Merce aggiunta con successo!", "La merce e' stata aggiunta all'ordine creato.");
            merceChoiceBox.getItems().clear();
            quantita.clear();
        } catch (IllegalArgumentException exception) {
            if (exception.getMessage().equals("Quantita non valida.")) {
                errorWindow("Quantita' non corretta!", "La quantita' inserita e' maggiore di quella presente in negozio.");
                quantita.clear();
            }
            if (exception.getMessage().equals("ID non valido.")) {
                errorWindow("Errore!", "ID del cliente non valido.");
                IDCliente.clear();
            }
            if (exception.getMessage().equals("Formato quantita' non valido.")) {
                errorWindow("Formato quantita' non valido!", "Inserire di nuovo la quantita'.");
                quantita.clear();
            }
        } catch (NullPointerException exception) {
            alertWindow("Alert!", "Inserire tutti i dati richiesti.");
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    @FXML
    public void registraOrdine() {
        try {
            int ID = Integer.parseInt(getIDCliente());
            ArrayList<String> dettagliOrdine = gestoreOrdini.registraOrdine(ID, gestoreClienti.getNomeCliente(ID),
                    gestoreClienti.getCognomeCliente(ID), negozio);
            ordineTextField.setText(dettagliOrdine.get(0));
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    public void selezionaPuntoPrelievo() {
        openWindow("/commesso/SelezionaPuntoPrelievo.fxml", "SelezionaPuntoPrelievo", new JavaFXSelezionaPuntoPrelievo(Integer.parseInt(ordineTextField.getText()), negozio));
    }

    /**
     * Collega i campi della Merce inserita alle colonne della tabella.
     */
    private void setMerceCellValueFactory() {
        IDMerce.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(0)));
        IDOrdineMerce.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(1)));
        PrezzoMerce.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(2)));
        DescrizioneMerce.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(3)));
        QuantitaMerce.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(4)));
    }

    @FXML
    public void showMerce() {
        try {
            merceChoiceBox.getItems().clear();
            merceChoiceBox.setItems(FXCollections.observableArrayList(negozio.getItems()));
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    @FXML
    public void terminaOrdine() {
        try {
            if (!merceTable.getItems().isEmpty()) {
                gestoreOrdini.terminaOrdine(Integer.parseInt(ordineTextField.getText()));
                successWindow("Ordine terminato con successo!", "Lo stato dell' ordine e' stato impostato a pagato.");
                selezionaPuntoPrelievo();
                closeWindow((Stage) IDCliente.getScene().getWindow());
            } else
                throw new IllegalArgumentException("Dati non presenti.");
        } catch (IllegalArgumentException exception) {
            errorWindow("L' ordine non puo' essere terminato!", "Inserire i dati richiesti.");
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    @FXML
    public void updateMerceChoiceBox() {
        if (Objects.isNull(merceChoiceBox.getValue())) {
            showMerce();
        }
    }

    /**
     * Inserisce i dati delle {@link SimpleMerceOrdine merci}  nella tabella.
     */
    @FXML
    public void visualizzaMerci() {
        try {
            setMerceCellValueFactory();
            merceTable.getItems().clear();
            for (MerceOrdine m : gestoreOrdini.getMerciOrdine(Integer.parseInt(ordineTextField.getText())))
                merceTable.getItems().add(m.getDettagli());
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }
}
