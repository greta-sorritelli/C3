package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.negozio.GestoreNegozi;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.personale.GestorePersonale;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import it.unicam.cs.ids.C3.TeamMGC.view.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class JavaFXSignIn implements JavaFXController {

    private final GestoreClienti gestoreClienti = GestoreClienti.getInstance();
    private final GestoreCorrieri gestoreCorrieri = GestoreCorrieri.getInstance();
    private final GestoreNegozi gestoreNegozi = GestoreNegozi.getInstance();
    private final GestoreMagazzini gestoreMagazzini = GestoreMagazzini.getInstance();
    /**
     * TabPane della finestra
     */
    @FXML
    TabPane tab = new TabPane();
    @FXML
    Tab signIn = new Tab();
    @FXML
    Tab cliente = new Tab();
    @FXML
    Tab corriere = new Tab();
    @FXML
    Tab commesso = new Tab();
    @FXML
    Tab addetto = new Tab();
    @FXML
    Tab commerciante = new Tab();
    @FXML
    Tab magazziniere = new Tab();
    /**
     * TextFields cliente
     */
    @FXML
    TextField nomeCliente;
    @FXML
    TextField cognomeCliente;
    @FXML
    PasswordField passwordCliente;
    /**
     * TextFields corriere
     */
    @FXML
    TextField nomeCorriere;
    @FXML
    TextField cognomeCorriere;
    @FXML
    PasswordField passwordCorriere;
    /**
     * TextFields commesso
     */
    @FXML
    ChoiceBox<Negozio> IDNegozioCommesso = new ChoiceBox<>();
    @FXML
    TextField nomeCommesso;
    @FXML
    TextField cognomeCommesso;
    @FXML
    PasswordField passwordCommesso;
    /**
     * TextFields addetto
     */
    @FXML
    ChoiceBox<Negozio> IDNegozioAddetto = new ChoiceBox<>();
    @FXML
    TextField nomeAddetto;
    @FXML
    TextField cognomeAddetto;
    @FXML
    PasswordField passwordAddetto;
    /**
     * TextFields commerciante
     */
    @FXML
    ChoiceBox<Negozio> IDNegozioCommerciante = new ChoiceBox<>();
    @FXML
    TextField nomeCommerciante;
    @FXML
    TextField cognomeCommerciante;
    @FXML
    PasswordField passwordCommerciante;
    /**
     * TextFields magazziniere
     */
    @FXML
    ChoiceBox<PuntoPrelievo> IDPPMagazziniere = new ChoiceBox<>();
    @FXML
    TextField nomeMagazziniere;
    @FXML
    TextField cognomeMagazziniere;
    @FXML
    PasswordField passwordMagazziniere;
    @FXML
    ChoiceBox<String> utentiChoiceBox = new ChoiceBox<>();
    private GestorePersonale gestorePersonale;

    @FXML
    public void backToSignIn() {
        cliente.setDisable(true);
        corriere.setDisable(true);
        commesso.setDisable(true);
        addetto.setDisable(true);
        commerciante.setDisable(true);
        magazziniere.setDisable(true);
        signIn.setDisable(false);
        tab.getSelectionModel().select(signIn);
    }

    @FXML
    public void deleteTextFieldAddetto() {
        IDNegozioAddetto.getItems().clear();
        nomeAddetto.clear();
        cognomeAddetto.clear();
        passwordAddetto.clear();
        showNegozi();
    }

    @FXML
    public void deleteTextFieldCliente() {
        nomeCliente.clear();
        cognomeCliente.clear();
        passwordCliente.clear();
    }

    @FXML
    public void deleteTextFieldCommerciante() {
        IDNegozioCommerciante.getItems().clear();
        nomeCommerciante.clear();
        cognomeCommerciante.clear();
        passwordCommerciante.clear();
        showNegozi();
    }

    @FXML
    public void deleteTextFieldCommesso() {
        IDNegozioCommesso.getItems().clear();
        nomeCommesso.clear();
        cognomeCommesso.clear();
        passwordCommesso.clear();
        showNegozi();
    }

    @FXML
    public void deleteTextFieldCorriere() {
        nomeCorriere.clear();
        cognomeCorriere.clear();
        passwordCorriere.clear();
    }

    @FXML
    public void deleteTextFieldMagazziniere() {
        IDPPMagazziniere.getItems().clear();
        nomeMagazziniere.clear();
        cognomeMagazziniere.clear();
        passwordMagazziniere.clear();
        showMagazzini();
    }

    public String getPassword(TextField t) {
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
                    tab.getSelectionModel().select(cliente);
                    cliente.setDisable(false);
                    signIn.setDisable(true);
                    break;
                case "CORRIERE":
                    tab.getSelectionModel().select(corriere);
                    corriere.setDisable(false);
                    signIn.setDisable(true);
                    break;
                case "COMMESSO":
                    tab.getSelectionModel().select(commesso);
                    commesso.setDisable(false);
                    signIn.setDisable(true);
                    break;
                case "ADDETTO":
                    tab.getSelectionModel().select(addetto);
                    addetto.setDisable(false);
                    signIn.setDisable(true);
                    break;
                case "COMMERCIANTE":
                    tab.getSelectionModel().select(commerciante);
                    commerciante.setDisable(false);
                    signIn.setDisable(true);
                    break;
                case "MAGAZZINIERE":
                    tab.getSelectionModel().select(magazziniere);
                    magazziniere.setDisable(false);
                    signIn.setDisable(true);
                    break;
            }
        } catch (NullPointerException exception) {
            alertWindow("Dati non presenti!", "Inserire la tipologia di utente.");
        }
    }

    @FXML
    public void showMagazzini() {
        try {
            IDPPMagazziniere.getItems().clear();
            IDPPMagazziniere.setItems(FXCollections.observableArrayList(gestoreMagazzini.getItems()));
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    @FXML
    public void showNegozi() {
        try {
            IDNegozioCommesso.getItems().clear();
            IDNegozioAddetto.getItems().clear();
            IDNegozioCommerciante.getItems().clear();
            ArrayList<Negozio> tmp = gestoreNegozi.getItems();
            IDNegozioCommesso.setItems(FXCollections.observableArrayList(tmp));
            IDNegozioAddetto.setItems(FXCollections.observableArrayList(tmp));
            IDNegozioCommerciante.setItems(FXCollections.observableArrayList(tmp));
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

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
    public void signInAddetto() {
        try {
            if (IDNegozioAddetto.getValue() == null || nomeAddetto.getText().isEmpty() || cognomeAddetto.getText().isEmpty() || passwordAddetto.getText().isEmpty())
                throw new NullPointerException("Dati non presenti.");
            gestorePersonale = new GestorePersonale(IDNegozioAddetto.getValue().getID());
            ArrayList<String> dettagli = gestorePersonale.inserisciAddetto(nomeAddetto.getText(), cognomeAddetto.getText(), getPassword(passwordAddetto));
            successWindow("Sign in successful!", "Addetto registrato con successo!\n" +
                    "ID: " + dettagli.get(0) + ", Nome: " + dettagli.get(3) + ", Cognome: " + dettagli.get(4) + ", Password: " + passwordAddetto.getText() + ".");
            openWindow("/addettoMagazzino/HomeAddettoMagazzino.fxml", "Home Addetto magazzino del negozio", new IAddettoMagazzino(Integer.parseInt(dettagli.get(0)), Integer.parseInt(dettagli.get(2))));
            closeWindow((Stage) nomeAddetto.getScene().getWindow());
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalArgumentException exception) {
            alertWindow("Password non valida!", "Inserire una password compresa tra 8 e 12 caratteri.");
            passwordAddetto.clear();
        } catch (NullPointerException exception) {
            alertWindow("Dati non presenti!", "Inserire tutti i dati richiesti.");
        }
    }

    @FXML
    public void signInCliente() {
        try {
            if (nomeCliente.getText().isEmpty() || cognomeCliente.getText().isEmpty() || passwordCliente.getText().isEmpty())
                throw new NullPointerException("Dati non presenti.");
            ArrayList<String> dettagli = gestoreClienti.inserisciDati(nomeCliente.getText(), cognomeCliente.getText(), getPassword(passwordCliente));
            successWindow("Sign in successful!", "Cliente registrato con successo!\n" +
                    "ID: " + dettagli.get(0) + ", Nome: " + dettagli.get(1) + ", Cognome: " + dettagli.get(2) + ", Password: " + passwordCliente.getText() + ".");
            openWindow("/cliente/HomeCliente.fxml", "Home Cliente", new ICliente(Integer.parseInt(dettagli.get(0))));
            closeWindow((Stage) nomeCliente.getScene().getWindow());
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalArgumentException exception) {
            alertWindow("Password non valida!", "Inserire una password compresa tra 8 e 12 caratteri.");
            passwordCliente.clear();
        } catch (NullPointerException exception) {
            alertWindow("Dati non presenti!", "Inserire tutti i dati richiesti.");
        }
    }

    @FXML
    public void signInCommerciante() {
        try {
            if (IDNegozioCommerciante.getValue() == null || nomeCommerciante.getText().isEmpty() || cognomeCommerciante.getText().isEmpty() || passwordCommerciante.getText().isEmpty())
                throw new NullPointerException("Dati non presenti.");
            gestorePersonale = new GestorePersonale(IDNegozioCommerciante.getValue().getID());
            ArrayList<String> dettagli = gestorePersonale.inserisciCommerciante(nomeCommerciante.getText(), cognomeCommerciante.getText(), getPassword(passwordCommerciante));
            successWindow("Sign in successful!", "Commerciante registrato con successo!\n" +
                    "ID: " + dettagli.get(0) + ", Nome: " + dettagli.get(3) + ", Cognome: " + dettagli.get(4) + ", Password: " + passwordCommerciante.getText() + ".");
            openWindow("/commerciante/HomeCommerciante.fxml", "Home Commerciante", new ICommerciante(Integer.parseInt(dettagli.get(0)), Integer.parseInt(dettagli.get(2))));
            closeWindow((Stage) nomeCommerciante.getScene().getWindow());
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalArgumentException exception) {
            if (exception.getMessage().equals("Password non valida.")) {
                alertWindow("Password non valida!", "Inserire una password compresa tra 8 e 12 caratteri.");
                passwordCommerciante.clear();
            }
            if (exception.getMessage().equals("Commerciante già presente.")) {
                alertWindow("In questo negozio e' gia' presente un commerciante!", "Impossibile registrarsi.");
                backToSignIn();
            }
        } catch (NullPointerException exception) {
            alertWindow("Dati non presenti!", "Inserire tutti i dati richiesti.");
        }
    }

    @FXML
    public void signInCommesso() {
        try {
            if (IDNegozioCommesso.getValue() == null || nomeCommesso.getText().isEmpty() || cognomeCommesso.getText().isEmpty() || passwordCommesso.getText().isEmpty())
                throw new NullPointerException("Dati non presenti.");
            gestorePersonale = new GestorePersonale(IDNegozioCommesso.getValue().getID());
            ArrayList<String> dettagli = gestorePersonale.inserisciCommesso(nomeCommesso.getText(), cognomeCommesso.getText(), getPassword(passwordCommesso));
            successWindow("Sign in successful!", "Commesso registrato con successo.!\n" +
                    "ID: " + dettagli.get(0) + ", Nome: " + dettagli.get(3) + ", Cognome: " + dettagli.get(4) + ", Password: " + passwordCommesso.getText() + ".");
            openWindow("/commesso/HomeCommesso.fxml", "Home Commesso", new ICommesso(Integer.parseInt(dettagli.get(0)), Integer.parseInt(dettagli.get(2))));
            closeWindow((Stage) nomeCommesso.getScene().getWindow());
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalArgumentException exception) {
            alertWindow("Password non valida!", "Inserire una password compresa tra 8 e 12 caratteri.");
            passwordCommesso.clear();
        } catch (NullPointerException exception) {
            alertWindow("Dati non presenti!", "Inserire tutti i dati richiesti.");
        }
    }

    @FXML
    public void signInCorriere() {
        try {
            if (nomeCorriere.getText().isEmpty() || cognomeCorriere.getText().isEmpty() || passwordCorriere.getText().isEmpty())
                throw new NullPointerException("Dati non presenti.");
            ArrayList<String> dettagli = gestoreCorrieri.inserisciDati(nomeCorriere.getText(), cognomeCorriere.getText(), getPassword(passwordCorriere));
            successWindow("Sign in successful!", "Corriere registrato con successo!\n" +
                    "ID: " + dettagli.get(0) + ", Nome: " + dettagli.get(1) + ", Cognome: " + dettagli.get(2) + ", Password: " + passwordCorriere.getText() + ".");
            openWindow("/corriere/HomeCorriere.fxml", "Home Corriere", new ICorriere(Integer.parseInt(dettagli.get(0))));
            closeWindow((Stage) nomeCorriere.getScene().getWindow());
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalArgumentException exception) {
            alertWindow("Password non valida!", "Inserire una password compresa tra 8 e 12 caratteri.");
            passwordCorriere.clear();
        } catch (NullPointerException exception) {
            alertWindow("Dati non presenti!", "Inserire tutti i dati richiesti.");
        }
    }

    @FXML
    public void signInMagazziniere() {
        try {
            if (IDPPMagazziniere.getValue() == null || nomeMagazziniere.getText().isEmpty() || cognomeMagazziniere.getText().isEmpty() || passwordMagazziniere.getText().isEmpty())
                throw new NullPointerException("Dati non presenti.");
            ArrayList<String> dettagli = gestoreMagazzini.inserisciMagazziniere(IDPPMagazziniere.getValue().getID(), nomeMagazziniere.getText(), cognomeMagazziniere.getText(), getPassword(passwordMagazziniere));
            successWindow("Sign in successful!", "Magazziniere registrato con successo!\n" +
                    "ID: " + dettagli.get(0) + ", Nome: " + dettagli.get(1) + ", Cognome: " + dettagli.get(2) + ", Password: " + passwordMagazziniere.getText() + ".");
            openWindow("/magazziniere/HomeMagazziniere.fxml", "Home Magazziniere", new IMagazziniere(Integer.parseInt(dettagli.get(0))));
            closeWindow((Stage) nomeMagazziniere.getScene().getWindow());
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalArgumentException exception) {
            alertWindow("Password non valida!", "Inserire una password compresa tra 8 e 12 caratteri.");
            passwordMagazziniere.clear();
        } catch (NullPointerException exception) {
            alertWindow("Dati non presenti!", "Inserire tutti i dati richiesti.");
        }
    }


}
