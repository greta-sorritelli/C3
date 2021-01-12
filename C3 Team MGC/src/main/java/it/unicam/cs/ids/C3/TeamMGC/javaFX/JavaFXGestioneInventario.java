package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.negozio.Merce;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Objects;

public class JavaFXGestioneInventario {

    private final Negozio negozio;

    @FXML
    TextField prezzo;

    @FXML
    TextField descrizione;

    @FXML
    TextField quantita;

    @FXML
    TextField quantita1;

    @FXML
    ChoiceBox<Merce> merceChoiceBox = new ChoiceBox<>();

    ArrayList<ArrayList<String>> merceCreata = new ArrayList<>();

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
     * Tabella della merce dell' inventario.
     */
    @FXML
    TableView<ArrayList<String>> merceTable2;

    @FXML
    TableColumn<ArrayList<String>, String> IDMerce2;

    @FXML
    TableColumn<ArrayList<String>, String> IDNegozioMerce2;

    @FXML
    TableColumn<ArrayList<String>, String> PrezzoMerce2;

    @FXML
    TableColumn<ArrayList<String>, String> DescrizioneMerce2;

    @FXML
    TableColumn<ArrayList<String>, String> QuantitaMerce2;

    public JavaFXGestioneInventario(Negozio negozio) {
        this.negozio = negozio;
    }

    /**
     * todo
     */
    public void inserisciNuovaMerce() {
        try {
            merceCreata.add(negozio.inserisciNuovaMerce(Double.parseDouble(prezzo.getText()), descrizione.getText(), Integer.parseInt(quantita.getText())));
            prezzo.clear();
            descrizione.clear();
            quantita.clear();
            visualizzaMerciInserite();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Aggiunta prodotto eseguita con successo!");
            alert.setContentText("La merce e' stata inserita correttamente nell' inventario.");
            alert.showAndWait();
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Inserire i dati richiesti.");
            alert.showAndWait();
        }
    }

    /**
     * todo
     */
    public void removeMerce() {
        try {
            ArrayList<ArrayList<String>> merciSelezionate = new ArrayList<>(merceTable2.getSelectionModel().getSelectedItems());
            if(!merciSelezionate.isEmpty()) {
                for (ArrayList<String> merce : merciSelezionate) {
                    int merceID = Integer.parseInt(merce.get(0));
                    negozio.removeMerce(merceID);
                    merceCreata.removeIf(merce1 -> merce1.get(0).equals(String.valueOf(merceID)));
                }
                visualizzaMerci();
                visualizzaMerciInserite();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Rimozione prodotto eseguita con successo!");
                alert.setContentText("La merce selezionata e' stata rimossa dall' inventario.");
                alert.showAndWait();
            }else
                throw new IllegalArgumentException("Merci non selezionate.");
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Selezionare la merce.");
            alert.showAndWait();
        }
    }

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
     * Collega i campi della Merce dell' inventario alle colonne della tabella.
     */
    private void setMerceInventarioCellValueFactory() {
        merceTable2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        IDMerce2.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(0)));
        IDNegozioMerce2.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(1)));
        PrezzoMerce2.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(2)));
        DescrizioneMerce2.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(3)));
        QuantitaMerce2.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(4)));
    }

    /**
     * todo
     */
    public void setQuantita() {
        try {
            negozio.setQuantita(merceChoiceBox.getValue().getID(), Integer.parseInt(quantita1.getText()));
            merceChoiceBox.getValue().update();
            merceCreata.removeIf(merce -> merce.get(0).equals(String.valueOf(merceChoiceBox.getValue().getID())));
            merceCreata.add(negozio.getMerce(merceChoiceBox.getValue().getID()).getDettagli());
            visualizzaMerciInserite();
            quantita1.clear();
            merceChoiceBox.getItems().clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Modifica prodotto eseguita con successo!");
            alert.setContentText("La quantita' della merce e' stata modificata.");
            alert.showAndWait();
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Inserire i dati richiesti.");
            alert.showAndWait();
        }
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

    /**
     * Inserisce i dati delle {@link Merce Merci} dell' inventario nella tabella.
     */
    @FXML
    public void visualizzaMerci() {
        try {
            setMerceInventarioCellValueFactory();
            merceTable2.getItems().clear();
            for (ArrayList<String> m : negozio.getDettagliMerce()) {
                merceTable2.getItems().add(m);
            }
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error!");
            alert.showAndWait();
        }
    }

    /**
     * Inserisce i dati delle {@link Merce Merci} create nella tabella.
     */
    @FXML
    public void visualizzaMerciInserite() {
        setMerceCellValueFactory();
        merceTable.getItems().clear();
        for (ArrayList<String> m : merceCreata) {
            merceTable.getItems().add(m);
        }
    }

}
