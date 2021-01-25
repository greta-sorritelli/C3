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
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class JavaFXLogin implements JavaFXController {

    private final GestoreClienti gestoreClienti = GestoreClienti.getInstance();
    private final GestoreCorrieri gestoreCorrieri = GestoreCorrieri.getInstance();
    private final GestoreNegozi gestoreNegozi = GestoreNegozi.getInstance();
    private final GestoreMagazzini gestoreMagazzini = GestoreMagazzini.getInstance();
    private GestorePersonale gestorePersonale;

    @FXML
    Label IDSedeLavoro;
    @FXML
    TextField ID;
    @FXML
    TextField IDSede;
    @FXML
    PasswordField password;

    @FXML
    ChoiceBox<String> utentiChoiceBox = new ChoiceBox<>();

    @FXML
    public void showUtenti() {
        utentiChoiceBox.getItems().clear();
        utentiChoiceBox.getItems().add("CLIENTE");
        utentiChoiceBox.getItems().add("CORRIERE");
        utentiChoiceBox.getItems().add("COMMESSO");
        utentiChoiceBox.getItems().add("ADDETTO");
        utentiChoiceBox.getItems().add("COMMERCIANTE");
        utentiChoiceBox.getItems().add("MAGAZZINIERE");
    }

    @FXML
    public void updateUtentiChoiceBox() {
        if (Objects.isNull(utentiChoiceBox.getValue())) {
            showUtenti();
        }
    }


    public String getPassword(PasswordField t) {
        if (t.getText().isEmpty() || t.getText().length() < 8 || t.getText().length() > 12)
            throw new IllegalArgumentException("Password non valida.");
        return t.getText();
    }


    @FXML
    public void goToUtente() {
        try {
            if (utentiChoiceBox.getValue().isEmpty())
                throw new NullPointerException("Tipologia non presente");

            switch (utentiChoiceBox.getValue()) {
                case "CLIENTE":
//                    tab.getSelectionModel().select(cliente);
//                    cliente.setDisable(false);
//                    signIn.setDisable(true);
                    break;
                case "CORRIERE":
//                    tab.getSelectionModel().select(corriere);
//                    corriere.setDisable(false);
//                    signIn.setDisable(true);
                    break;
                case "COMMESSO":
//                    tab.getSelectionModel().select(commesso);
//                    commesso.setDisable(false);
//                    signIn.setDisable(true);
                    break;
                case "ADDETTO":
//                    tab.getSelectionModel().select(addetto);
//                    addetto.setDisable(false);
//                    signIn.setDisable(true);
                    break;
                case "COMMERCIANTE":
//                    tab.getSelectionModel().select(commerciante);
//                    commerciante.setDisable(false);
//                    signIn.setDisable(true);
                    break;
                case "MAGAZZINIERE":
//                    tab.getSelectionModel().select(magazziniere);
//                    magazziniere.setDisable(false);
//                    signIn.setDisable(true);
                    break;
            }
        } catch (NullPointerException exception) {
            alertWindow("Dati non presenti!", "Inserire la tipologia di utente.");
        }
    }


   private void loginCliente() {
        try {
            if (ID.getText().isEmpty() || getPassword(password).isEmpty())
                throw new NullPointerException("Dati non presenti.");
            int id = Integer.parseInt(ID.getText());
            if(gestoreClienti.checkInfoUtente("CLIENTE", id, password.getText())) {
                successWindow("Login effettuato.", "Il login è stato effettuato con successo.");
                openWindow("/HomeCliente.fxml", "Home Cliente", new ICliente(id));
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
            if(gestoreCorrieri.checkInfoUtente("CORRIERE", id, password.getText())) {
                successWindow("Login effettuato.", "Il login è stato effettuato con successo.");
                openWindow("/HomeCorriere.fxml", "Home Corriere", new ICorriere(id));
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
            if (ID.getText().isEmpty() || getPassword(password).isEmpty() || IDSede.getText().isEmpty())
                throw new NullPointerException("Dati non presenti.");
            int id = Integer.parseInt(ID.getText());
            int idMagazzino = Integer.parseInt(IDSede.getText());
            if(gestoreMagazzini.checkInfoPersonale("MAGAZZINIERE", id, password.getText(), idMagazzino)) {
                successWindow("Login effettuato.", "Il login è stato effettuato con successo.");
                openWindow("/HomeMagazziniere.fxml", "Home Magazziniere", new IMagazziniere(id));
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
            if(gestorePersonale.checkInfoPersonale("COMMERCIANTE", id, password.getText(), idNegozio)) {
                successWindow("Login effettuato.", "Il login è stato effettuato con successo.");
                openWindow("/HomeCommerciante.fxml", "Home Commerciante", new ICommerciante(id));
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
            if(gestorePersonale.checkInfoPersonale("COMMESSO", id, password.getText(), idNegozio)) {
                successWindow("Login effettuato.", "Il login è stato effettuato con successo.");
                openWindow("/HomeCommesso.fxml", "Home Commesso", new ICommesso(id));
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

    private void loginAddettoMagazzino() {
        try {
            if (ID.getText().isEmpty() || getPassword(password).isEmpty() || IDSede.getText().isEmpty())
                throw new NullPointerException("Dati non presenti.");
            int id = Integer.parseInt(ID.getText());
            int idNegozio = Integer.parseInt(IDSede.getText());
            if(gestorePersonale.checkInfoPersonale("ADDETTO_MAGAZZINO", id, password.getText(), idNegozio)) {
                successWindow("Login effettuato.", "Il login è stato effettuato con successo.");
                openWindow("/HomeAddettoMagazzino.fxml", "Home Addetto magazzino del negozio", new IAddettoMagazzino(id));
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
    public void login() {
        ID.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                loginUtente();
            }
        });
    }

    private void loginUtente() {
    }


}
