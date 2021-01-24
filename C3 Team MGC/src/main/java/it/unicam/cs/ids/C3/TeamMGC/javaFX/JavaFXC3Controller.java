package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.cliente.JavaFXLoginCliente;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.corriere.JavaFXLoginCorriere;
import it.unicam.cs.ids.C3.TeamMGC.view.*;

import java.sql.SQLException;

/**
 * Classe per la visualizzazione della home e la scelta del profilo
 */
public class JavaFXC3Controller implements JavaFXController {

    public void registrazionePiattaforma(){
        openWindow("/SignIn.fxml", "SignIn", new JavaFXSignIn());
    }

    public void login(){
        openWindow("/Login.fxml", "Login", new JavaFXLogin());
    }

    public void visualizzaHomeAddetto() throws SQLException {
        openWindow("/HomeAddettoMagazzino.fxml", "Home Addetto Magazzino", new IAddettoMagazzino());
    }

    public void visualizzaHomeCommesso() throws SQLException {
        openWindow("/HomeCommesso.fxml", "Home Commesso", new ICommesso());
    }

    public void visualizzaHomeMagazziniere() throws SQLException {
        openWindow("/HomeMagazziniere.fxml", "Login Magazziniere", new IMagazziniere());
    }

    public void visualizzaHomeCorriere()  {
        openWindow("/LoginCorriere.fxml", "Login Corriere", new JavaFXLoginCorriere());
    }

    //todo fxml home cliente
    public void visualizzaHomeCliente() {
        openWindow("/LoginCliente.fxml", "Login Cliente", new JavaFXLoginCliente());
    }

    //todo fxml
    public void visualizzaHomeCommerciante() {
        openWindow("/HomeCommerciante.fxml", "Home Commerciante", new ICommerciante());
    }

}
