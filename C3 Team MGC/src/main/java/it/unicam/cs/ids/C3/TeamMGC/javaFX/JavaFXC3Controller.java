package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.amministratore.JavaFXLoginAdmin;
import javafx.fxml.FXML;

/**
 * Classe per la visualizzazione della home e la scelta del profilo.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class JavaFXC3Controller implements JavaFXController {

    /**
     * Apre la finestra il signIn.
     */
    @FXML
    public void registrazionePiattaforma() {
        openWindow("/SignIn.fxml", "SignIn", new JavaFXSignIn());
    }

    /**
     * Apre la finestra per login.
     */
    @FXML
    public void login() {
        openWindow("/Login.fxml", "Login", new JavaFXLogin());
    }

    /**
     * Apre la finestra per il login dell' amministratore.
     */
    @FXML
    public void loginAdmin() {
        openWindow("/amministratore/LoginAdmin.fxml", "Login Amministratore", new JavaFXLoginAdmin());
    }
}
