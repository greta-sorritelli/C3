package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.commesso.JavaFXComunicareCodiceRitiro;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.commesso.JavaFXRicezionePagamento;
import it.unicam.cs.ids.C3.TeamMGC.negozio.GestoreNegozi;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import java.sql.SQLException;

public class ICommesso implements JavaFXController {

    private final GestoreNegozi gestoreNegozi = GestoreNegozi.getInstance();
    private final Negozio negozio;
    private final int ID;

    public ICommesso(int ID, int IDNegozio) throws SQLException {
        this.ID = ID;
        this.negozio = gestoreNegozi.getItem(IDNegozio);
    }

    /**
     * Apre la finestra per comunicare il codice di ritiro al cliente.
     */
    public void comunicaCodiceRitiro() {
        openWindow("/commesso/ComunicareCodiceRitiro.fxml", "ComunicareCodiceRitiro", new JavaFXComunicareCodiceRitiro());
    }

    /**
     * Apre la finestra per ricevere il pagamento del cliente.
     */
    public void ricezionePagamento() {
        openWindow("/commesso/RicezionePagamento.fxml", "RicezionePagamento", new JavaFXRicezionePagamento(negozio));
    }

}