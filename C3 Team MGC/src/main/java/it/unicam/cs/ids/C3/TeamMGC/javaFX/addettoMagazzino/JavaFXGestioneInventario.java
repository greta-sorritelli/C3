package it.unicam.cs.ids.C3.TeamMGC.javaFX.addettoMagazzino;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Merce;
import it.unicam.cs.ids.C3.TeamMGC.negozio.SimpleMerce;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controller della pagina per la gestione dell' Inventario.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class JavaFXGestioneInventario implements JavaFXController {

    private final Negozio negozio;

    @FXML
    TabPane tab = new TabPane();
    @FXML
    Tab aggiunta = new Tab();

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

    public String getPrezzo() {
        if (prezzo.getText().matches(".*[a-zA-Z]+.*"))
            throw new IllegalArgumentException("Formato prezzo non valido.");
        return prezzo.getText();
    }

    public String getQuantita(TextField t) {
        if (t.getText().matches(".*[a-zA-Z]+.*"))
            throw new IllegalArgumentException("Formato quantita non valido.");
        return t.getText();
    }


    public void inserisciNuovaMerce() {
        try {
            if (getPrezzo().isEmpty() || descrizione.getText().isEmpty() || getQuantita(quantita).isEmpty())
                throw new NullPointerException("Dati non presenti.");
            merceCreata.add(negozio.inserisciNuovaMerce(Double.parseDouble(getPrezzo()), descrizione.getText(), Integer.parseInt(getQuantita(quantita))));
            prezzo.clear();
            descrizione.clear();
            quantita.clear();
            visualizzaMerciInserite();
            successWindow("Aggiunta prodotto eseguita con successo!", "La merce e' stata inserita correttamente nell' inventario.", 2);
        } catch (NullPointerException exception) {
            errorWindow("Impossibile inserire la merce!", "Inserire i dati richiesti.", 2);
        } catch (IllegalArgumentException exception) {
            if (exception.getMessage().equals("Formato prezzo non valido.")) {
                errorWindow("Formato prezzo non valido!", "Inserire di nuovo il prezzo.", 2);
                prezzo.clear();
            }
            if (exception.getMessage().equals("Formato quantita non valido.")) {
                errorWindow("Formato quantita' non valido!", "Inserire di nuovo la quantita'.", 2);
                quantita.clear();
            }
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.", 2);
        }
    }

    public void removeMerce() {
        try {
            ArrayList<ArrayList<String>> merciSelezionate = new ArrayList<>(merceTable2.getSelectionModel().getSelectedItems());
            if (!merciSelezionate.isEmpty()) {
                for (ArrayList<String> merce : merciSelezionate) {
                    int merceID = Integer.parseInt(merce.get(0));
                    negozio.removeMerce(merceID);
                    merceCreata.removeIf(merce1 -> merce1.get(0).equals(String.valueOf(merceID)));
                }
                successWindow("Rimozione prodotto eseguita con successo!", "La merce selezionata e' stata rimossa dall' inventario.", 2);
                if (!negozio.getDettagliItems().isEmpty())
                    visualizzaMerci();
                else
                    tab.getSelectionModel().select(aggiunta);
                visualizzaMerciInserite();
            } else
                throw new IllegalArgumentException("Merci non selezionate.");
        } catch (IllegalArgumentException exception) {
            errorWindow("Errore!", "Selezionare la merce.", 2);
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.", 2);
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

    public void setQuantita() {
        try {
            if (merceChoiceBox.getValue() == null || getQuantita(quantita1).isEmpty())
                throw new NullPointerException("Dati non presenti.");
            negozio.setQuantitaMerce(merceChoiceBox.getValue().getID(), Integer.parseInt(quantita1.getText()));
            merceChoiceBox.getValue().update();
            merceCreata.removeIf(merce -> merce.get(0).equals(String.valueOf(merceChoiceBox.getValue().getID())));
            merceCreata.add(negozio.getItem(merceChoiceBox.getValue().getID()).getDettagli());
            visualizzaMerciInserite();
            quantita1.clear();
            merceChoiceBox.getItems().clear();
            successWindow("Modifica prodotto eseguita con successo!", "La quantita' della merce e' stata modificata.", 2);
        } catch (NullPointerException exception) {
            errorWindow("Errore!", "Inserire i dati richiesti.", 2);
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.", 2);
        } catch (IllegalArgumentException exception) {
            errorWindow("Formato quantita' non valido!", "Inserire di nuovo la quantita.", 2);
            quantita1.clear();
        }
    }

    @FXML
    public void showMerce() {
        try {
            merceChoiceBox.getItems().clear();
            merceChoiceBox.setItems(FXCollections.observableArrayList(negozio.getItems()));
            if (merceChoiceBox.getItems().isEmpty()) {
                alertWindow("Merci non presenti.", "Aggiorna piu' tardi.", 2);
                tab.getSelectionModel().select(aggiunta);
            }
        } catch (Exception exception) {
            errorWindow("Errore!", "Error.", 2);
        }
    }

    @FXML
    public void updateMerceChoiceBox() {
        showMerce();
        merceChoiceBox.getItems().clear();
        quantita1.clear();
    }

    @FXML
    public void clearText() {
        prezzo.clear();
        descrizione.clear();
        quantita.clear();
    }

    /**
     * Inserisce i dati delle {@link SimpleMerce Merci} dell' inventario nella tabella.
     */
    @FXML
    public void visualizzaMerci() {
        try {
            setMerceInventarioCellValueFactory();
            merceTable2.getItems().clear();
            for (ArrayList<String> m : negozio.getDettagliItems())
                merceTable2.getItems().add(m);
            if (merceTable2.getItems().isEmpty()) {
                alertWindow("Prodotti non presenti.", "Aggiorna piu' tardi.", 2);
                tab.getSelectionModel().select(aggiunta);
            }
        } catch (Exception exception) {
            errorWindow("Errore!", "Error.", 2);
        }
    }

    /**
     * Inserisce i dati delle {@link SimpleMerce Merci} create nella tabella.
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
