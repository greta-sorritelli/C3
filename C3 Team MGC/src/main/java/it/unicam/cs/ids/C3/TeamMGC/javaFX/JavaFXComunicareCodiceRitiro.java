package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class JavaFXComunicareCodiceRitiro implements JavaFXController {

    private final GestoreClienti gestoreClienti;

    public JavaFXComunicareCodiceRitiro(GestoreClienti gestoreClienti) {
        this.gestoreClienti = gestoreClienti;
    }

    @FXML
    TextField IDCliente;

    @FXML
    TextField IDOrdine;

    @FXML
    TextField CodiceRitiroAttuale;


    public void verificaEsistenzaCodice() throws SQLException {
        String vecchioCodice = gestoreClienti.getItem(Integer.parseInt(IDCliente.getText())).getCodiceRitiro();
        String codice = gestoreClienti.verificaEsistenzaCodice(Integer.parseInt(IDCliente.getText()), Integer.parseInt(IDOrdine.getText()));
        if (vecchioCodice.equals(codice))
            successWindow("Il cliente ha gia' un codice", "Codice cliente : "  + codice);
        else
            successWindow("Nuovo codice creato", "Codice cliente : "  + codice);
        CodiceRitiroAttuale.setText(codice);
    }


}
