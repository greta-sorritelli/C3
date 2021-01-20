package it.unicam.cs.ids.C3.TeamMGC.javaFX.commesso;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class JavaFXComunicareCodiceRitiro implements JavaFXController {

    private final GestoreClienti gestoreClienti = GestoreClienti.getInstance();
    private final GestoreOrdini gestoreOrdini = GestoreOrdini.getInstance();

    @FXML
    TextField IDCliente;

    @FXML
    TextField IDOrdine;

    @FXML
    TextField CodiceRitiroAttuale;


    public void verificaEsistenzaCodice() {
        try {
            if (IDCliente.getText().isEmpty() || IDOrdine.getText().isEmpty())
                throw new NullPointerException("Dati non presenti.");
            String vecchioCodice = gestoreClienti.getCodiceRitiroCliente(Integer.parseInt(IDCliente.getText()));
            String codice = gestoreClienti.verificaEsistenzaCodice(Integer.parseInt(IDCliente.getText()), Integer.parseInt(IDOrdine.getText()));
            if (vecchioCodice.equals(codice))
                informationWindow("Il cliente ha gia' un codice", "Codice cliente : " + codice);
            else
                informationWindow("Nuovo codice creato", "Codice cliente : " + codice);
            CodiceRitiroAttuale.setText(codice);
        } catch (NullPointerException exception) {
            errorWindow("Errore!", "Inserire tutti i dati richiesti.");
        } catch (IllegalArgumentException exception) {
            if (exception.getMessage().equals("ID non valido.")) {
                errorWindow("Errore!", "ID cliente non valido.");
                IDCliente.clear();
            }
            if (exception.getMessage().equals("ID ordine non valido.")) {
                errorWindow("Errore!", "ID ordine non valido.");
                IDOrdine.clear();
            }

        } catch (SQLException exception) {
            errorWindow("Errore!", "Error DB.");
        }

    }
}
