package it.unicam.cs.ids.C3.TeamMGC.javaFX.magazziniere;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.MerceOrdine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.ArrayList;

public class JavaFXConsegnareMerceAlCliente implements JavaFXController {

    private final PuntoPrelievo puntoPrelievo;
    private final GestoreOrdini gestoreOrdini;
    private final GestoreClienti gestoreClienti;

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

    public JavaFXConsegnareMerceAlCliente(PuntoPrelievo puntoPrelievo, GestoreOrdini gestoreOrdini, GestoreClienti gestoreClienti) {
        this.puntoPrelievo = puntoPrelievo;
        this.gestoreOrdini = gestoreOrdini;
        this.gestoreClienti = gestoreClienti;
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
                successWindow("Consegna merce eseguita con successo!","La merce e' stata consegnata al cliente.");
            } else
                throw new IllegalArgumentException("Merce non presente nella tabella.");
        } catch (Exception exception) {
            errorWindow("Errore!", "Inserire i dati richiesti.");
        }
    }

    @FXML
    public void verificaCodice() {
        try {
            if (IDCliente.getText().isEmpty() ||  codiceRitiro.getText().isEmpty())
                throw new NullPointerException("Dati non presenti.");

            if (gestoreClienti.verificaCodice(Integer.parseInt(IDCliente.getText()), codiceRitiro.getText())) {
                ArrayList<ArrayList<String>> merci = gestoreOrdini.getInDepositMerci(puntoPrelievo.getOrdini(Integer.parseInt(IDCliente.getText())));
                visualizzaMerci(merci);
                successWindow("Verifica codice eseguita con successo!","Il codice inserito appartiene al cliente.");
                if(merci.isEmpty()) {
                    informationWindow("Verifica codice eseguita con successo!","Il codice inserito appartiene al cliente.");
                    IDCliente.clear();
                    codiceRitiro.clear();
                }
            } else
                throw new IllegalStateException("Codice non valido.");
        } catch (NullPointerException e) {
            errorWindow("Errore!","Inserisci tutti i dati richiesti!");
        } catch (IllegalArgumentException exception) {
            errorWindow("Errore!","ID cliente non valido.");
            IDCliente.clear();
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
