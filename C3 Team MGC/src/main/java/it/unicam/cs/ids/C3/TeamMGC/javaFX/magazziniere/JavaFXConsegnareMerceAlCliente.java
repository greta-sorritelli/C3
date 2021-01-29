package it.unicam.cs.ids.C3.TeamMGC.javaFX.magazziniere;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.ArrayList;

public class JavaFXConsegnareMerceAlCliente implements JavaFXController {

    private final PuntoPrelievo simplePuntoPrelievo;
    private final GestoreOrdini gestoreOrdini = GestoreOrdini.getInstance();
    private final GestoreClienti gestoreClienti = GestoreClienti.getInstance();

    @FXML
    TextField IDCliente;
    @FXML
    TextField codiceRitiro;

    /**
     * Tabella della merceOrdine
     */
    @FXML
    TableView<ArrayList<String>> merceOrdineTable;
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
    @FXML
    TableColumn<ArrayList<String>, String> StatoMerce;

    public JavaFXConsegnareMerceAlCliente(PuntoPrelievo simplePuntoPrelievo) {
        this.simplePuntoPrelievo = simplePuntoPrelievo;
    }

    public String getCodiceRitiro() {
        if (codiceRitiro.getText().length() != 12 || codiceRitiro.getText().matches(".*[a-zA-Z]+.*"))
            throw new IllegalArgumentException("Codice non valido.");
        return codiceRitiro.getText();
    }

    public String getIDCliente() {
        if (IDCliente.getText().matches(".*[a-zA-Z]+.*"))
            throw new IllegalArgumentException("ID non valido.");
        return IDCliente.getText();
    }

    /**
     * Collega i campi della MerceOrdine alle colonne della tabella.
     */
    private void setMerceOrdineCellValueFactory() {
        IDMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(0)));
        IDOrdineMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(1)));
        PrezzoMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(2)));
        DescrizioneMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(3)));
        QuantitaMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(4)));
        StatoMerce.setCellValueFactory(merceOrdine -> new SimpleObjectProperty<>(merceOrdine.getValue().get(5)));
    }

    public void setStatoMerce() {
        try {
            if (!merceOrdineTable.getItems().isEmpty()) {
                for (ArrayList<String> merce : merceOrdineTable.getItems())
                    gestoreOrdini.setStatoMerce(Integer.parseInt(merce.get(0)), StatoOrdine.RITIRATO);
                merceOrdineTable.getItems().clear();
                IDCliente.clear();
                codiceRitiro.clear();
                successWindow("Consegna merce eseguita con successo!", "La merce e' stata consegnata al cliente.");
            } else
                throw new IllegalArgumentException("Merce non presente nella tabella.");
        } catch (Exception exception) {
            errorWindow("Errore!", "Inserire i dati richiesti.");
        }
    }

    @FXML
    public void verificaCodice() {
        try {
            if (getIDCliente().isEmpty() || getCodiceRitiro().isEmpty())
                throw new NullPointerException("Dati non presenti.");

            if (gestoreClienti.verificaCodice(Integer.parseInt(getIDCliente()), getCodiceRitiro())) {
                ArrayList<ArrayList<String>> merci = gestoreOrdini.getInDepositMerci(simplePuntoPrelievo.getOrdini(Integer.parseInt(getIDCliente())));
                visualizzaMerci(merci);
                successWindow("Verifica codice eseguita con successo!", "Il codice inserito appartiene al cliente.");
                if (merci.isEmpty()) {
                    informationWindow("Riprovare piu' tardi!", "Non ci sono merci da consegnare al cliente.");
                    IDCliente.clear();
                    codiceRitiro.clear();
                }
            } else
                throw new IllegalStateException("Codice non valido.");
        } catch (NullPointerException e) {
            errorWindow("Errore!", "Inserisci tutti i dati richiesti!");
        } catch (IllegalArgumentException exception) {
            if (exception.getMessage().equals("ID non valido.")) {
                errorWindow("Errore!", "ID cliente non valido.");
                IDCliente.clear();
            }
            if (exception.getMessage().equals("Codice non valido.")) {
                errorWindow("Codice ritiro non valido!", "Inserire un codice di 12 numeri.");
                codiceRitiro.clear();
            }
        } catch (IllegalStateException exception) {
            errorWindow("Errore!", "Il codice non appartiene al cliente!");
            IDCliente.clear();
            codiceRitiro.clear();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    public void visualizzaMerci(ArrayList<ArrayList<String>> merci) {
        setMerceOrdineCellValueFactory();
        merceOrdineTable.getItems().clear();
        for (ArrayList<String> m : merci)
            merceOrdineTable.getItems().add(m);

    }


}
