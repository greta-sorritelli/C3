package it.unicam.cs.ids.C3.TeamMGC.javaFX.commesso;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;

/**
 * Controller della pagina per comunicare il Codice di Ritiro al Cliente.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class JavaFXComunicareCodiceRitiro implements JavaFXController {

    private final GestoreClienti gestoreClienti = GestoreClienti.getInstance();

    @FXML
    TextField IDCliente;

    @FXML
    TextField IDOrdine;

    @FXML
    TextField CodiceRitiroAttuale;

    public String getIDCliente() {
        if (IDCliente.getText().matches(".*[a-zA-Z]+.*"))
            throw new IllegalArgumentException("ID cliente non valido.");
        return IDCliente.getText();
    }

    public String getIDOrdine() {
        if (IDOrdine.getText().matches(".*[a-zA-Z]+.*"))
            throw new IllegalArgumentException("ID ordine non valido.");
        return IDOrdine.getText();
    }

    public void verificaEsistenzaCodice() {
        try {
            if (getIDCliente().isEmpty() || IDOrdine.getText().isEmpty())
                throw new NullPointerException("Dati non presenti.");
            String vecchioCodice = gestoreClienti.getCodiceRitiroCliente(Integer.parseInt(getIDCliente()));
            String codice = gestoreClienti.verificaEsistenzaCodice(Integer.parseInt(getIDCliente()), Integer.parseInt(getIDOrdine()));
            if (vecchioCodice.equals(codice))
                informationWindow("Il cliente ha gia' un codice", "Codice cliente : " + codice, 2);
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Nuovo codice creato");
                alert.setContentText("Codice cliente : " + codice);
                alert.showAndWait();
            }
            CodiceRitiroAttuale.setText(codice);
        } catch (NullPointerException exception) {
            errorWindow("Errore!", "Inserire tutti i dati richiesti.", 2);
        } catch (IllegalArgumentException exception) {
            if (exception.getMessage().equals("ID cliente non valido.")) {
                errorWindow("Errore!", "ID cliente non valido.", 2);
                IDCliente.clear();
            }
            if (exception.getMessage().equals("ID ordine non valido.")) {
                errorWindow("Errore!", "ID ordine non valido.", 2);
                IDOrdine.clear();
            }
        } catch (SQLException exception) {
            errorWindow("Errore!", "Error DB.", 2);
        }

    }
}
