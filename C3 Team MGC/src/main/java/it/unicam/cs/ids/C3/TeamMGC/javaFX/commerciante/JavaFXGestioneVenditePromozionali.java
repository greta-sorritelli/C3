package it.unicam.cs.ids.C3.TeamMGC.javaFX.commerciante;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Merce;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class JavaFXGestioneVenditePromozionali implements JavaFXController {

    private final Negozio negozio;

    @FXML
    TextField prezzoNuovo;
    @FXML
    TextArea messaggio;
    @FXML
    TextField prezzoModificato;
    @FXML
    TextArea messaggioModificato = new TextArea();
    /**
     * Tabella delle promozioni.
     */
    @FXML
    TableView<ArrayList<String>> promozioniTable;
    @FXML
    TableColumn<ArrayList<String>, String> IDMercePromozione;
    @FXML
    TableColumn<ArrayList<String>, String> messaggioPromozione;
    @FXML
    TableColumn<ArrayList<String>, String> prezzoAttuale;
    @FXML
    TableColumn<ArrayList<String>, String> prezzoPrecedente;
    @FXML
    ChoiceBox<Merce> merceChoiceBox = new ChoiceBox<>();
    @FXML
    ChoiceBox<ArrayList<String>> promozioneChoiceBox = new ChoiceBox<>();

    public JavaFXGestioneVenditePromozionali(Negozio negozio) {
        this.negozio = negozio;
    }

    @FXML
    public void caricaMessaggio() {
        if (promozioneChoiceBox.getValue() != null)
            messaggioModificato.setText(promozioneChoiceBox.getValue().get(1));
    }

    @FXML
    public void eliminaPromozione() {
        try {
            if (!promozioniTable.getSelectionModel().isEmpty()) {
                ArrayList<ArrayList<String>> sel = new ArrayList<>(promozioniTable.getSelectionModel().getSelectedItems());
                for (ArrayList<String> promozione : sel)
                    if (promozione != null)
                        negozio.eliminaPromozione(Integer.parseInt(promozione.get(0)));
                successWindow("Success!", "Promozioni eliminate con successo.");
                sel.clear();
                visualizzaPromozioni();
            } else
                alertWindow("Impossibile proseguire", "Selezionare la promozione da eliminare.");
        } catch (SQLException e) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    @FXML
    public void lanciaPromozione() throws SQLException {
        try {
            if (merceChoiceBox.getValue() == null || prezzoNuovo.getText().isEmpty() || messaggio.getText().isEmpty())
                throw new NullPointerException("Dati non presenti.");
            negozio.lanciaPromozione(merceChoiceBox.getValue().getID(), Double.parseDouble(prezzoNuovo.getText()), messaggio.getText());
            successWindow("Success!", "Promozione lanciata con successo.");
            merceChoiceBox.getItems().clear();
            prezzoNuovo.clear();
            messaggio.clear();
        } catch (NullPointerException exception) {
            alertWindow("Alert!", "Inserire tutti i dati richiesti.");
        } catch (SQLException exception) {
            alertWindow("Alert!", "Questa merce è già in promozione.");
            merceChoiceBox.getItems().clear();
            prezzoNuovo.clear();
            messaggio.clear();
        }

    }

    @FXML
    public void modificaPromozione() {
        try {
            if (promozioneChoiceBox.getValue() == null || prezzoModificato.getText().isEmpty())
                throw new NullPointerException("Dati non presenti.");
            negozio.setNuoviDatiPromozione(Integer.parseInt(promozioneChoiceBox.getValue().get(0)), Double.parseDouble(prezzoModificato.getText()), messaggioModificato.getText());
            successWindow("Success!", "Promozione modificata con successo.");
            promozioneChoiceBox.getItems().clear();
            prezzoModificato.clear();
            messaggioModificato.clear();
        } catch (NullPointerException exception) {
            alertWindow("Alert!", "Inserire tutti i dati richiesti.");
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    /**
     * Collega i campi delle promozioni alle colonne della tabella.
     */
    private void setPromozioneCellValueFactory() {
        promozioniTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        IDMercePromozione.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().get(0)));
        messaggioPromozione.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().get(1)));
        prezzoAttuale.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().get(2)));
        prezzoPrecedente.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().get(3)));
    }

    @FXML
    public void showMerce() {
        try {
            merceChoiceBox.getItems().clear();
            merceChoiceBox.setItems(FXCollections.observableArrayList(negozio.getInventario()));
            if (negozio.getInventario().isEmpty())
                throw new IllegalArgumentException("Merci non presenti.");
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalArgumentException exception) {
            alertWindow("Alert!", "Merci non presenti.");
            closeWindow((Stage) prezzoNuovo.getScene().getWindow());
        }
    }

    @FXML
    public void showPromozioni() {
        try {
            promozioneChoiceBox.getItems().clear();
            promozioneChoiceBox.setItems(FXCollections.observableArrayList(negozio.getDettagliPromozioni()));
            if (promozioneChoiceBox.getItems().isEmpty())
                throw new IllegalArgumentException("Promozioni non presenti.");
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalArgumentException exception) {
            alertWindow("Alert!", "Promozioni non presenti.");
        }
    }

    @FXML
    public void updateMerceChoiceBox() {
        if (Objects.isNull(merceChoiceBox.getValue())) {
            showMerce();
        }
    }

    @FXML
    public void updatePromozioniChoiceBox() {
        if (Objects.isNull(promozioneChoiceBox.getValue())) {
            showPromozioni();
        }
    }

    @FXML
    public void visualizzaPromozioni() {
        try {
            setPromozioneCellValueFactory();
            promozioniTable.getItems().clear();
            promozioniTable.getItems().addAll(negozio.getDettagliPromozioni());
            if (promozioniTable.getItems().isEmpty())
                throw new IllegalArgumentException("Promozioni non presenti.");
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalArgumentException e) {
            alertWindow("Promozioni non presenti.", "Aggiorna piu' tardi.");
        }
    }
}
