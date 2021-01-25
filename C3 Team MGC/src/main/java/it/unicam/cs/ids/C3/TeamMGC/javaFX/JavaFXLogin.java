package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.negozio.GestoreNegozi;
import it.unicam.cs.ids.C3.TeamMGC.personale.GestorePersonale;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;
import it.unicam.cs.ids.C3.TeamMGC.view.*;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;

public class JavaFXLogin implements JavaFXController {

    private final GestoreClienti gestoreClienti = GestoreClienti.getInstance();
    private final GestoreCorrieri gestoreCorrieri = GestoreCorrieri.getInstance();
    private final GestoreNegozi gestoreNegozi = GestoreNegozi.getInstance();
    private final GestoreMagazzini gestoreMagazzini = GestoreMagazzini.getInstance();

    @FXML
    Label IDSedeLavoro;
    @FXML
    Label Id;
    @FXML
    TextField ID;
    @FXML
    TextField IDSede;
    @FXML
    PasswordField password;
    @FXML
    ChoiceBox<String> utentiChoiceBox = new ChoiceBox<>();
    private GestorePersonale gestorePersonale;

    public String getPassword(PasswordField t) {
        if (t.getText().isEmpty() || t.getText().length() < 8 || t.getText().length() > 12)
            throw new IllegalArgumentException("Password non valida.");
        return t.getText();
    }

    private void loginAddettoMagazzino() {
        try {
            if (ID.getText().isEmpty() || getPassword(password).isEmpty() || IDSede.getText().isEmpty())
                throw new NullPointerException("Dati non presenti.");
            int id = Integer.parseInt(ID.getText());
            int idNegozio = Integer.parseInt(IDSede.getText());
            gestorePersonale = new GestorePersonale(idNegozio);
            if (gestorePersonale.checkInfo("ADDETTO_MAGAZZINO", id, password.getText(), idNegozio)) {
                successWindow("Login effettuato.", "Il login e' stato effettuato con successo.");
                openWindow("/addettoMagazzino/HomeAddettoMagazzino.fxml", "Home Addetto magazzino del negozio", new IAddettoMagazzino(id, idNegozio));
                closeWindow((Stage) ID.getScene().getWindow());
            } else
                errorWindow("Login non effettuato.", "ID o password errati.");
        } catch (IllegalArgumentException e) {
            errorWindow("Dati errati.", "ID o password errati.");
        } catch (NullPointerException exception) {
            alertWindow("Dati non presenti!", "Inserire tutti i dati richiesti.");
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalStateException e) {
            errorWindow("Valore non corretto", "Inserire un valore valido.");
        }
    }

    private void loginCliente() {
        try {
            if (ID.getText().isEmpty() || getPassword(password).isEmpty())
                throw new NullPointerException("Dati non presenti.");
            int id = Integer.parseInt(ID.getText());
            if (gestoreClienti.checkInfo("CLIENTE", id, password.getText())) {
                successWindow("Login effettuato.", "Il login e' stato effettuato con successo.");
                openWindow("/cliente/HomeCliente.fxml", "Home Cliente", new ICliente(id));
                closeWindow((Stage) ID.getScene().getWindow());
            } else
                errorWindow("Login non effettuato.", "ID o password errati.");
        } catch (IllegalArgumentException e) {
            errorWindow("Dati errati.", "ID o password errati.");
        } catch (NullPointerException exception) {
            alertWindow("Dati non presenti!", "Inserire tutti i dati richiesti.");
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalStateException e) {
            errorWindow("Valore non corretto", "Inserire un valore valido.");
        }
    }

    private void loginCommerciante() {
        try {
            if (ID.getText().isEmpty() || getPassword(password).isEmpty() || IDSede.getText().isEmpty())
                throw new NullPointerException("Dati non presenti.");
            int id = Integer.parseInt(ID.getText());
            int idNegozio = Integer.parseInt(IDSede.getText());
            gestorePersonale = new GestorePersonale(idNegozio);
            if (gestorePersonale.checkInfo("COMMERCIANTE", id, password.getText(), idNegozio)) {
                successWindow("Login effettuato.", "Il login e' stato effettuato con successo.");
                openWindow("/commerciante/HomeCommerciante.fxml", "Home Commerciante", new ICommerciante(id, idNegozio));
                closeWindow((Stage) ID.getScene().getWindow());
            } else
                errorWindow("Login non effettuato.", "ID o password errati.");
        } catch (IllegalArgumentException e) {
            errorWindow("Dati errati.", "ID o password errati.");
        } catch (NullPointerException exception) {
            alertWindow("Dati non presenti!", "Inserire tutti i dati richiesti.");
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalStateException e) {
            errorWindow("Valore non corretto", "Inserire un valore valido.");
        }
    }

    private void loginCommesso() {
        try {
            if (ID.getText().isEmpty() || getPassword(password).isEmpty() || IDSede.getText().isEmpty())
                throw new NullPointerException("Dati non presenti.");
            int id = Integer.parseInt(ID.getText());
            int idNegozio = Integer.parseInt(IDSede.getText());
            gestorePersonale = new GestorePersonale(idNegozio);
            if (gestorePersonale.checkInfo("COMMESSO", id, password.getText(), idNegozio)) {
                successWindow("Login effettuato.", "Il login e' stato effettuato con successo.");
                openWindow("/commesso/HomeCommesso.fxml", "Home Commesso", new ICommesso(id, idNegozio));
                closeWindow((Stage) ID.getScene().getWindow());
            } else
                errorWindow("Login non effettuato.", "ID o password errati.");
        } catch (IllegalArgumentException e) {
            errorWindow("Dati errati.", "ID o password errati.");
        } catch (NullPointerException exception) {
            alertWindow("Dati non presenti!", "Inserire tutti i dati richiesti.");
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalStateException e) {
            errorWindow("Valore non corretto", "Inserire un valore valido.");
        }
    }

    private void loginCorriere() {
        try {
            if (ID.getText().isEmpty() || getPassword(password).isEmpty())
                throw new NullPointerException("Dati non presenti.");
            int id = Integer.parseInt(ID.getText());
            if (gestoreCorrieri.checkInfo("CORRIERE", id, password.getText())) {
                successWindow("Login effettuato.", "Il login e' stato effettuato con successo.");
                openWindow("/corriere/HomeCorriere.fxml", "Home Corriere", new ICorriere(id));
                closeWindow((Stage) ID.getScene().getWindow());
            } else
                errorWindow("Login non effettuato.", "ID o password errati.");
        } catch (IllegalArgumentException e) {
            errorWindow("Dati errati.", "ID o password errati.");
        } catch (NullPointerException exception) {
            alertWindow("Dati non presenti!", "Inserire tutti i dati richiesti.");
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalStateException e) {
            errorWindow("Valore non corretto", "Inserire un valore valido.");
        }
    }

    private void loginMagazziniere() {
        try {
            if (getPassword(password).isEmpty() || IDSede.getText().isEmpty())
                throw new NullPointerException("Dati non presenti.");
            int idMagazzino = Integer.parseInt(IDSede.getText());
            if (gestoreMagazzini.checkInfo("MAGAZZINIERE", idMagazzino, password.getText())) {
                successWindow("Login effettuato.", "Il login e' stato effettuato con successo.");
                openWindow("/magazziniere/HomeMagazziniere.fxml", "Home Magazziniere", new IMagazziniere(idMagazzino));
                closeWindow((Stage) ID.getScene().getWindow());
            } else
                errorWindow("Login non effettuato.", "ID o password errati.");
        } catch (IllegalArgumentException e) {
            errorWindow("Dati errati.", "ID o password errati.");
        } catch (NullPointerException exception) {
            alertWindow("Dati non presenti!", "Inserire tutti i dati richiesti.");
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalStateException e) {
            errorWindow("Valore non corretto", "Inserire un valore valido.");
        }
    }

    @FXML
    public void loginUtente() {
        if (!Objects.isNull(utentiChoiceBox.getValue())) {
            switch (utentiChoiceBox.getValue()) {
                case "CLIENTE":
                    loginCliente();
                    break;
                case "CORRIERE":
                    loginCorriere();
                    break;
                case "MAGAZZINIERE":
                    loginMagazziniere();
                    break;
                case "ADDETTO MAGAZZINO":
                    loginAddettoMagazzino();
                    break;
                case "COMMERCIANTE":
                    loginCommerciante();
                    break;
                case "COMMESSO":
                    loginCommesso();
                    break;
            }
        }
    }

    @FXML
    public void setUtenteLogin() {
        if (utentiChoiceBox.getValue() != null)
            switch (utentiChoiceBox.getValue()) {
                case "CLIENTE":
                case "CORRIERE":
                    IDSedeLavoro.setVisible(false);
                    IDSede.setVisible(false);
                    ID.setVisible(true);
                    Id.setVisible(true);
                    break;
                case "COMMESSO":
                case "ADDETTO MAGAZZINO":
                case "COMMERCIANTE":
                    IDSedeLavoro.setVisible(true);
                    IDSede.setVisible(true);
                    ID.setVisible(true);
                    Id.setVisible(true);
                    break;
                case "MAGAZZINIERE":
                    IDSedeLavoro.setVisible(true);
                    IDSede.setVisible(true);
                    ID.setVisible(false);
                    Id.setVisible(false);
                    break;
            }
    }

    @FXML
    public void showUtenti() {
        utentiChoiceBox.getItems().clear();
        utentiChoiceBox.getItems().add("CLIENTE");
        utentiChoiceBox.getItems().add("CORRIERE");
        utentiChoiceBox.getItems().add("COMMESSO");
        utentiChoiceBox.getItems().add("ADDETTO MAGAZZINO");
        utentiChoiceBox.getItems().add("COMMERCIANTE");
        utentiChoiceBox.getItems().add("MAGAZZINIERE");
    }

    @FXML
    public void updateUtentiChoiceBox() {
        if (Objects.isNull(utentiChoiceBox.getValue())) {
            showUtenti();
        }
    }
}
