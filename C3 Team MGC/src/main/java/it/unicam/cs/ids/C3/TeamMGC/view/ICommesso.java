package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.commesso.JavaFXComunicareCodiceRitiro;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.commesso.JavaFXRicezionePagamento;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.negozio.SimpleNegozio;

import java.sql.SQLException;

public class ICommesso implements JavaFXController {
    //todo
    private final Negozio negozio = new SimpleNegozio(1);
    private int ID;

    public ICommesso(int id) throws SQLException {
        this.ID = id;
    }

    public void comunicaCodiceRitiro() {
        openWindow("/ComunicareCodiceRitiro.fxml", "ComunicareCodiceRitiro", new JavaFXComunicareCodiceRitiro());
    }

    public void ricezionePagamento() {
        openWindow("/RicezionePagamento.fxml", "RicezionePagamento", new JavaFXRicezionePagamento(negozio));
    }

}