package it.unicam.cs.ids.C3.TeamMGC.javaFX.amministratore;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.negozio.CategoriaNegozio;
import it.unicam.cs.ids.C3.TeamMGC.negozio.GestoreNegozi;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Controller della pagina per la gestione dei Negozi.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class JavaFXGestioneNegozi implements JavaFXController {

    private final GestoreNegozi gestoreNegozi = GestoreNegozi.getInstance();
    private final ArrayList<Negozio> negoziSelezionati = new ArrayList<>();

    @FXML
    TabPane tab = new TabPane();
    @FXML
    Tab registra = new Tab();
    @FXML
    TextField nome;
    @FXML
    TextField indirizzo;
    @FXML
    TextField orarioA;
    @FXML
    TextField orarioC;
    @FXML
    TextField telefono;
    /**
     * Tabella dei negozi
     */
    @FXML
    TableView<ArrayList<String>> negoziTable;
    @FXML
    TableColumn<ArrayList<String>, String> IDNegozio;
    @FXML
    TableColumn<ArrayList<String>, String> nomeNegozio;
    @FXML
    TableColumn<ArrayList<String>, String> categoriaNegozio;
    @FXML
    TableColumn<ArrayList<String>, String> orarioANegozio;
    @FXML
    TableColumn<ArrayList<String>, String> orarioCNegozio;
    @FXML
    TableColumn<ArrayList<String>, String> indirizzoNegozio;
    @FXML
    TableColumn<ArrayList<String>, String> telefonoNegozio;

    @FXML
    ChoiceBox<CategoriaNegozio> categorieChoiceBox = new ChoiceBox<>();

    @FXML
    private void eliminaNegozi() {
        try {
            if (!negoziTable.getSelectionModel().isEmpty()) {
                ArrayList<ArrayList<String>> sel = new ArrayList<>(negoziTable.getSelectionModel().getSelectedItems());
                for (ArrayList<String> negozio : sel)
                    if (negozio != null) {
                        int id = Integer.parseInt(negozio.get(0));
                        this.negoziSelezionati.add(gestoreNegozi.getItem(id));
                    }
                for (Negozio negozio : negoziSelezionati)
                    gestoreNegozi.removeNegozio(negozio.getID());
                successWindow("Success!", "Negozi eliminati con successo.");
                sel.clear();
                if (!gestoreNegozi.getDettagliItems().isEmpty())
                    visualizzaNegozi();
                else
                    tab.getSelectionModel().select(registra);
            } else
                alertWindow("Impossibile proseguire", "Selezionare uno o piu' negozi.");
        } catch (SQLException e) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    public String getOrario(TextField t) {
        if (t.getText().length() == 5) {
            if (t.getText().contains(":")) {
                String[] tmp = t.getText().split(":");
                if (tmp.length == 2) {
                    int orario;
                    orario = tmp[0].charAt(0);
                    if (orario > 50)
                        throw new NumberFormatException("Formato non valido");
                    orario = tmp[0].charAt(1);
                    if (orario > 57)
                        throw new NumberFormatException("Formato non valido");
                    orario = tmp[1].charAt(0);
                    if (orario > 53)
                        throw new NumberFormatException("Formato non valido");
                    orario = tmp[1].charAt(1);
                    if (orario > 57)
                        throw new NumberFormatException("Formato non valido");
                    return t.getText();
                } else
                    throw new NumberFormatException("Formato non valido");
            } else
                throw new NumberFormatException("Formato non valido");
        } else
            throw new NumberFormatException("Formato non valido");
    }

    public String getTelefono() {
        if (telefono.getText().matches(".*[a-zA-Z]+.*"))
            throw new IllegalArgumentException("Formato telefono non valido.");
        return telefono.getText();
    }

    @FXML
    public void registraNegozio() {
        try {
            if (nome.getText().isEmpty() || categorieChoiceBox.getValue() == null || getTelefono().isEmpty() || indirizzo.getText().isEmpty() || orarioA.getText().isEmpty() || orarioC.getText().isEmpty())
                throw new IllegalArgumentException("Dati non presenti");
            gestoreNegozi.inserisciNegozio(nome.getText(), categorieChoiceBox.getValue(), indirizzo.getText(), getTelefono(), getOrario(orarioA), getOrario(orarioC));
            successWindow("Success!", "Negozio registrato con successo.");
            nome.clear();
            categorieChoiceBox.getItems().clear();
            indirizzo.clear();
            telefono.clear();
            orarioA.clear();
            orarioC.clear();
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (NumberFormatException exception) {
            alertWindow("Formato orario non valido!", "Inserire un orario nel formato 00:00.");
        } catch (IllegalArgumentException exception) {
            if (exception.getMessage().equals("Formato telefono non valido.")) {
                alertWindow("Formato telefono non valido!", "Inserire solo numeri.");
                telefono.clear();
            }
            if (exception.getMessage().equals("Dati non presenti"))
                alertWindow("Alert!", "Inserire tutti i dati richiesti.");
        }
    }

    /**
     * Collega i campi del Negozio alle colonne della tabella.
     */
    private void setNegozioCellValueFactory() {
        negoziTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        IDNegozio.setCellValueFactory(negozio -> new SimpleObjectProperty<>(negozio.getValue().get(0)));
        categoriaNegozio.setCellValueFactory(negozio -> new SimpleObjectProperty<>(negozio.getValue().get(2)));
        nomeNegozio.setCellValueFactory(negozio -> new SimpleObjectProperty<>(negozio.getValue().get(1)));
        orarioANegozio.setCellValueFactory(negozio -> new SimpleObjectProperty<>(negozio.getValue().get(3)));
        orarioCNegozio.setCellValueFactory(negozio -> new SimpleObjectProperty<>(negozio.getValue().get(4)));
        indirizzoNegozio.setCellValueFactory(negozio -> new SimpleObjectProperty<>(negozio.getValue().get(5)));
        telefonoNegozio.setCellValueFactory(negozio -> new SimpleObjectProperty<>(negozio.getValue().get(6)));
    }

    @FXML
    public void showCategorie() {
        categorieChoiceBox.getItems().clear();
        categorieChoiceBox.setItems(FXCollections.observableArrayList(CategoriaNegozio.values()));
    }

    @FXML
    public void updateCategorieChoiceBox() {
        showCategorie();
        nome.clear();
        categorieChoiceBox.getItems().clear();
        telefono.clear();
        indirizzo.clear();
        orarioA.clear();
        orarioC.clear();
    }

    @FXML
    public void visualizzaNegozi() {
        try {
            setNegozioCellValueFactory();
            negoziTable.getItems().clear();
            negoziSelezionati.clear();
            negoziTable.getItems().addAll(gestoreNegozi.getDettagliItems());
            if (negoziTable.getItems().isEmpty()) {
                alertWindow("Alert!", "Non ci sono negozi.");
                tab.getSelectionModel().select(registra);
            }
        } catch (SQLException e) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }
}