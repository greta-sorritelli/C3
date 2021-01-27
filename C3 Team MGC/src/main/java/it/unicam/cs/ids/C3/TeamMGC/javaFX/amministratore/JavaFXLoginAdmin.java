package it.unicam.cs.ids.C3.TeamMGC.javaFX.amministratore;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.manager.GestoreLogin;
import it.unicam.cs.ids.C3.TeamMGC.view.IAmministratore;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class JavaFXLoginAdmin implements JavaFXController {

    @FXML
    TextField nomeUtente;
    @FXML
    PasswordField password;

    public String getPassword() {
        if (password.getText().isEmpty() || password.getText().length() < 8 || password.getText().length() > 12)
            throw new IllegalArgumentException("Password non valida.");
        return password.getText();
    }

    @FXML
    public void loginAdmin() {
        try {
            if (nomeUtente.getText().isEmpty() || getPassword().isEmpty())
                throw new NullPointerException("Dati non presenti.");
            if (GestoreLogin.checkInfo(nomeUtente.getText(),getPassword())) {
                successWindow("Login effettuato.", "Il login e' stato effettuato con successo.");
                openWindow("/amministratore/HomeAmministratore.fxml", "Home Amministratore", new IAmministratore());
                closeWindow((Stage) nomeUtente.getScene().getWindow());
            } else
                errorWindow("Login non effettuato.", "Nome utente o password errati.");
        } catch (IllegalArgumentException e) {
            errorWindow("Error!", "Password errata.");
            password.clear();
        } catch (NullPointerException exception) {
            alertWindow("Dati non presenti!", "Inserire tutti i dati richiesti.");
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB." + exception.getMessage());
        }
    }
}
