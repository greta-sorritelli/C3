package it.unicam.cs.ids.C3.TeamMGC.javaFX;

/**
 * Classe per la visualizzazione della home e la scelta del profilo
 */
public class JavaFXC3Controller implements JavaFXController {

    public void registrazionePiattaforma() {
        openWindow("/SignIn.fxml", "SignIn", new JavaFXSignIn());
    }

    public void login() {
        openWindow("/Login.fxml", "Login", new JavaFXLogin());
    }
}
