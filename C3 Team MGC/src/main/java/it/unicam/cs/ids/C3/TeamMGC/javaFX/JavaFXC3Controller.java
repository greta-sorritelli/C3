package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.view.*;

import java.sql.SQLException;

/**
 * Classe per la visualizzazione della home e la scelta del profilo
 */
public class JavaFXC3Controller implements JavaFXController {

    public void visualizzaHomeAddetto() throws SQLException {
        openWindow("/HomeAddettoMagazzino.fxml", "Home Addetto Magazzino", new IAddettoMagazzino());
    }

    public void visualizzaHomeCommesso() throws SQLException {
        openWindow("/HomeCommesso.fxml", "Home Commesso", new ICommesso());
    }

    public void visualizzaHomeMagazziniere() throws SQLException {
        openWindow("/HomeMagazziniere.fxml", "Home Magazziniere", new IMagazziniere());
    }

    public void visualizzaHomeCorriere() throws SQLException {
        openWindow("/LoginCorriere.fxml", "Login Corriere", new JavaFXLogin());
//        openWindow("/HomeCorriere.fxml", "Home Corriere", new ICorriere());
    }

    //todo fxml
    public void visualizzaHomeCliente() {
        openWindow("/HomeCliente.fxml", "Home Cliente", new ICliente());
    }

    //todo fxml
    public void visualizzaHomeCommerciante() {
        openWindow("/HomeCommerciante.fxml", "Home Commerciante", new ICommerciante());
    }

}
