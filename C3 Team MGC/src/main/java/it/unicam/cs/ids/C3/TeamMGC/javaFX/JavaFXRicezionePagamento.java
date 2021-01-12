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

public class JavaFXRicezionePagamento implements JavaFXController{

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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error!");
            alert.showAndWait();
        }
    }

    @FXML
    public void updateMerceChoiceBox() {
        if (Objects.isNull(merceChoiceBox.getValue())) {
            showMerce();
        }
    }

    //todo errore se quantita è maggiore e alert
    public void registraOrdine() throws SQLException {
        if (ordineTextField.getText().isEmpty()) {
            int ID = Integer.parseInt(IDCliente.getText());
            ArrayList<String> dettagliOrdine = gestoreOrdini.registraOrdine(ID, gestoreClienti.getItem(ID).getNome(),
                    gestoreClienti.getItem(ID).getCognome());
            ordineTextField.setText(dettagliOrdine.get(0));
        }
        gestoreOrdini.registraMerce(merceChoiceBox.getValue().getID(), Integer.parseInt(quantita.getText()), Integer.parseInt(ordineTextField.getText()));
        setMerceCellValueFactory();
        merceTable.getItems().clear();
        for (MerceOrdine m : gestoreOrdini.getOrdine(Integer.parseInt(ordineTextField.getText())).getMerci())
            merceTable.getItems().add(m.getDettagli());
        quantita.clear();
        merceChoiceBox.getItems().clear();
        IDCliente.setEditable(false);
    }

    //todo alert
    public void terminaOrdine() {
        try {
            gestoreOrdini.terminaOrdine(Integer.parseInt(ordineTextField.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Ordine terminato con successo!");
            alert.setContentText("Lo stato dell' ordine e' stato impostato a pagato.");
            alert.showAndWait();
            Stage stage = (Stage) IDCliente.getScene().getWindow();
            closeWindow(stage);
        } catch (Exception exception){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Inserire i dati richiesti.");
        alert.showAndWait();
    }
    }
}
